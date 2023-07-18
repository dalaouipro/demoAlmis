package org.example.services;

import com.almis.awe.config.ServiceConfig;
import com.almis.awe.exception.AWException;
import com.almis.awe.model.dto.DataList;
import com.almis.awe.model.dto.ServiceData;
import com.almis.awe.model.util.data.DataListUtil;
import com.almis.awe.service.MaintainService;
import com.almis.awe.service.QueryService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.models.Customer;
import org.example.models.Valuation;
import org.example.repositories.ValuationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class testService extends ServiceConfig {

    @Autowired
    QueryService queryService;
    @Autowired
    MaintainService maintainService;
    @Autowired
    ValuationService valuationService;
    @Autowired
    ValuationRepo valuationRepo;


    public ServiceData print() throws FileNotFoundException, JRException, AWException, ParseException {
        String destpath = "C:\\Users\\HP\\Documents\\JAVA";
        String srcpath = "C:\\Users\\HP\\Desktop\\demoAlmis\\src\\main\\resources\\jrxml\\Blank_A4_1.jrxml";
        ServiceData serviceData = new ServiceData();
        generateReport(destpath,srcpath);
        return serviceData;

    }
    public ServiceData valuate() throws AWException, ParseException {
        ServiceData serviceData = new ServiceData();
        System.out.println(getSession().getUser());
        List<Valuation> valuations = getQuantityPrice();
        List<Valuation> valuationList = new ArrayList<>();
        System.out.println("list valuation ==> "+valuations);
        for (Valuation element:valuations) {
           Optional<Valuation> val = valuationRepo.findByAsset_id(element.getAsset_id(),element.getPricedate());
           if(!val.isPresent())  valuationList.add(element);
           else{
               if(element.getPrice() != val.get().getPrice()) {
                   valuationList.add(element);
               }
           }
        }
        valuationRepo.saveAll(valuationList);
        return serviceData;
    }

    private List<Customer> getCustomers() throws AWException, ParseException {
        DataList customerList = queryService.launchQuery("queryPrint").getDataList();
        List<Customer> customers = new ArrayList<>();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        for (int i = 0; i < customerList.getRows().size(); i++) {
            Integer id = Integer.valueOf(DataListUtil.getData(customerList, i, "id"));
            String companyName = DataListUtil.getData(customerList, i, "COMPANYNAME");
            String fullName = DataListUtil.getData(customerList, i, "FULLNAME");
            Boolean status = Boolean.valueOf(DataListUtil.getData(customerList, i, "STATUS"));
            String registerDate = DataListUtil.getData(customerList,i,"REGISTRATION_DATE");

            Date RegistrationDate = null;
            if(registerDate != null && !registerDate.isEmpty()){
                RegistrationDate = dateFormat.parse(DataListUtil.getData(customerList,i,"REGISTRATION_DATE"));
            }
            customers.add(new Customer(id,
                    companyName,
                    fullName,
                    status,
                    RegistrationDate
            ));
        }
        return customers;
    }

    private void generateReport(String destinationPath,String sourcePath) throws  JRException, AWException, ParseException {

        File file = new File(sourcePath);
        List<Customer> customers = getCustomers();
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("customers",dataSource);
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, destinationPath + "\\customer.pdf");
        } catch (JRException e){
            e.printStackTrace();
        }
    }
    public List<Valuation> getQuantityPrice() throws AWException, ParseException {
        DataList ValuationList = queryService.launchQuery("getQuantityPrice").getDataList();
        Integer mult = 0;
        List<Valuation> valuations = new ArrayList<>();
        for (int i = 0;i<ValuationList.getRows().size();i++){
            Integer id = Integer.valueOf(DataListUtil.getData(ValuationList,i,"PORTFOLIO_ID"));
            Integer quantity = Integer.valueOf(DataListUtil.getData(ValuationList,i,"QUANTITY"));
            Integer price = Integer.valueOf(DataListUtil.getData(ValuationList,i,"PRICE"));
            String Date = DataListUtil.getData(ValuationList,i,"DATE");
            Integer asset_id = Integer.valueOf(DataListUtil.getData(ValuationList,i,"ASSET_ID"));
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Date priceDate = null;
            if(Date != null && !Date.isEmpty()){
                priceDate = dateFormat.parse(DataListUtil.getData(ValuationList,i,"DATE"));
            }
            mult = quantity*price;
            valuations.add(new Valuation(id,quantity,price,mult,priceDate,asset_id));
        }
        return valuations;
    }

}
