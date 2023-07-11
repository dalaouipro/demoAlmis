package org.example.services;

import com.almis.awe.config.ServiceConfig;
import com.almis.awe.exception.AWException;
import com.almis.awe.model.dto.DataList;
import com.almis.awe.model.dto.ServiceData;
import com.almis.awe.model.util.data.DataListUtil;
import com.almis.awe.service.QueryService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class testService extends ServiceConfig {

    @Autowired
    QueryService queryService;


    public ServiceData print() throws FileNotFoundException, JRException, AWException, ParseException {
        String destpath = "C:\\Users\\HP\\Documents\\JAVA";
       // String srcpath = "C:\\Users\\HP\\IdeaProjects\\Demo\\src\\main\\resources\\jrxml\\Blank_A4.jrxml";
        String srcpath = "C:\\Users\\HP\\IdeaProjects\\Demo\\src\\main\\resources\\jrxml\\Blank_A4_2.jrxml";
        ServiceData serviceData = new ServiceData();
        generateReport(destpath,srcpath);
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

    private void generateReport(String destinationPath,String sourcePath) throws FileNotFoundException, JRException, AWException, ParseException {

        File file = new File(sourcePath);
        List<Customer> customers = getCustomers();
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);
        Map<String, Object> parameters = new HashMap<>();
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, destinationPath + "\\customer.pdf");
        } catch (JRException e){
            e.printStackTrace();
        }
    }


}
