package org.example;

import com.almis.awe.config.ServiceConfig;
import com.almis.awe.exception.AWException;
import com.almis.awe.model.dto.DataList;
import com.almis.awe.model.dto.ServiceData;
import com.almis.awe.model.util.data.DataListUtil;
import com.almis.awe.service.QueryService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
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
        DataList l = queryService.launchQuery("queryPrint").getDataList();
        List<Customer> customers = new ArrayList<>();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        for (int i = 0; i < l.getRows().size(); i++) {
            Integer id = Integer.valueOf(DataListUtil.getData(l, i, "id"));
            String companyName = DataListUtil.getData(l, i, "COMPANYNAME");
            String fullName = DataListUtil.getData(l, i, "FULLNAME");
            Boolean status = Boolean.valueOf(DataListUtil.getData(l, i, "STATUS"));
            String registerDate = DataListUtil.getData(l,i,"REGISTRATION_DATE");

            Date RegistrationDate = null;
            if(registerDate != null && !registerDate.isEmpty()){
                RegistrationDate = dateFormat.parse(DataListUtil.getData(l,i,"REGISTRATION_DATE"));
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
