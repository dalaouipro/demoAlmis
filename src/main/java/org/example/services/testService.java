package org.example.services;

import com.almis.awe.builder.enumerates.Action;
import com.almis.awe.builder.enumerates.Condition;
import com.almis.awe.builder.enumerates.InitialLoad;
import com.almis.awe.builder.enumerates.TargetType;
import com.almis.awe.builder.screen.ScreenBuilder;
import com.almis.awe.builder.screen.TagBuilder;
import com.almis.awe.builder.screen.button.ButtonActionBuilder;
import com.almis.awe.builder.screen.button.ButtonBuilder;
import com.almis.awe.builder.screen.criteria.TextCriteriaBuilder;
import com.almis.awe.builder.screen.dependency.DependencyBuilder;
import com.almis.awe.builder.screen.dependency.DependencyElementBuilder;
import com.almis.awe.builder.screen.grid.CalendarColumnBuilder;
import com.almis.awe.builder.screen.grid.GridBuilder;
import com.almis.awe.builder.screen.grid.NumericColumnBuilder;
import com.almis.awe.builder.screen.grid.TextColumnBuilder;
import com.almis.awe.builder.screen.tab.TabBuilder;
import com.almis.awe.builder.screen.tab.TabContainerBuilder;
import com.almis.awe.config.ServiceConfig;
import com.almis.awe.exception.AWException;
import com.almis.awe.model.dto.CellData;
import com.almis.awe.model.dto.DataList;
import com.almis.awe.model.dto.ServiceData;
import com.almis.awe.model.entities.actions.ClientAction;
import com.almis.awe.model.util.data.DataListUtil;
import com.almis.awe.service.MaintainService;
import com.almis.awe.service.QueryService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.models.Estatico;
import org.example.dto.PersonDto;
import org.example.models.Customer;
import org.example.models.Person;
import org.example.models.Valuation;
import org.example.repositories.PersonRepo;
import org.example.repositories.ValuationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.modelmapper.*;

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
    @Autowired
    PersonRepo personRepo;


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
               if(!Objects.equals(element.getPrice(), val.get().getPrice())) {
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

    public void SvcGrdIns(PersonDto personDto) throws AWException {
        Person MyPers = new ModelMapper().map(personDto, Person.class);
        personRepo.save(MyPers);
        System.out.println(MyPers);
        for (Map<String, CellData> element : queryService.launchQuery("QuerTestPers").getDataList().getRows()) {
            MyPers.setId(Integer.parseInt(element.get("GrdSvcColId").getStringValue()));
            MyPers.setName(element.get("GrdSvcColName").getStringValue());
            MyPers.setAlive(Boolean.parseBoolean(element.get("GrdSvcColAlive").getStringValue()));
            MyPers.setBirthdate(LocalDate.parse(element.get("GrdSvcColBirthDate").getStringValue(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println(MyPers);
        }
    }

    public ServiceData SvcGrdFill() throws AWException {
        //getRequest().getParameterList()getParameter("GrdSvc").;
        ServiceData serviceData = new ServiceData();
        ClientAction fillGrdAction = new ClientAction("fill");
        fillGrdAction.setTarget("GrdSvc");
        DataList dataList = queryService.launchQuery("QuerTestPers").getDataList();
        fillGrdAction.addParameter("datalist", dataList);
        serviceData.addClientAction(fillGrdAction);
        return serviceData;
    }

    public void doInsert(List<Estatico> estatico) throws AWException {
        DataList dlPersons = queryService.launchQuery("QuerGridEstE").getDataList();
        List<Person> listPerson = new ArrayList<>();
        for (Map<String, CellData> element : dlPersons.getRows()) {
            listPerson.add(new Person(element));
        }
        getRequest().getParameterList();

    }

    /*
    public ServiceData createScreen() throws AWException {
//        getElements().getScreen("testScreen");
        ScreenBuilder builder = new ScreenBuilder();
        TagBuilder tagBuilder = new TagBuilder();
        TextCriteriaBuilder textCriteriaBuilder = new TextCriteriaBuilder();
        textCriteriaBuilder.setId("crtJavaTest").setValue("bla bla").setReadonly(true).setStyle("col-lg-2 col-lg-offset-5");
        tagBuilder.setSource("center").addCriteria(textCriteriaBuilder);
//        builder.setId(UUID.randomUUID().toString());
        builder.setId("testScreen2");
        builder.setTemplate("window");
        builder.addTag(tagBuilder);
//        getElements().setScreen(builder.build());
//        getElements().getScreen("testScreen");
//        Screen screen = builder.build();
//        getElements().setScreen(screen);
        ServiceData serviceData = builder.buildClientAction(getElements());
//        serviceData.addVariable("screenId","testScreen2");
//        serviceData.addClientAction(new ScreenActionBuilder("testScreen2").build());
        return serviceData;
    }
    */

    public ServiceData createInstrumentsDetails() throws AWException {
        ScreenBuilder builder = new ScreenBuilder()
                .setId("instrumentsDetails")
                .setTemplate("window");

        TagBuilder tagButtons = new TagBuilder()
                .setSource("buttons");

        ButtonBuilder btnMtnSec = new ButtonBuilder()
                .setId("btnMtnSec")
                .setLabel("Maintain Security");
        DependencyBuilder depBtnMtnSec = new DependencyBuilder()
                .setTargetType(TargetType.SHOW)
                .setInitial(true);
        DependencyElementBuilder depEleSec = new DependencyElementBuilder()
                .setId("tabIstr")
                .setCondition(Condition.EQUALS)
                .setValue("Securities");
        depBtnMtnSec.addDependencyElement(depEleSec);
        btnMtnSec.addDependency(depBtnMtnSec);

        tagButtons.addButton(btnMtnSec);

        TagBuilder tagCenter = new TagBuilder()
                .setSource("center");

        TabBuilder tabIstr = new TabBuilder()
                .setId("tabIstr")
                .setInitialLoad(InitialLoad.ENUMERATED)
                .setTargetAction("enumIstr");
        TabContainerBuilder tabCtnSec2 = new TabContainerBuilder().setId("Securities");
        TabContainerBuilder tabCtnPrc2 = new TabContainerBuilder().setId("Pricing");
        TabContainerBuilder tabCtnCli = new TabContainerBuilder().setId("Clients");
        TabContainerBuilder tabCtnPfl = new TabContainerBuilder().setId("Portfolio");
        TabContainerBuilder tabCtnPos = new TabContainerBuilder().setId("Position");



        GridBuilder grdSec = new GridBuilder()
                .setId("grdSec")
                .setInitialLoad(InitialLoad.QUERY)
                .setTargetAction("querIstrDtlSec")
                .setStyle("expand")
                .setSendOperations(true);

        NumericColumnBuilder colIstrSecId = new NumericColumnBuilder()
                .setId("colIstrSecId")
                .setName("colIstrSecId")
                .setLabel("Id")
                .setHidden(true);
        TextColumnBuilder colIstrSecNam = new TextColumnBuilder()
                .setId("colIstrSecNam")
                .setName("colIstrSecNam")
                .setLabel("Name");
        TextColumnBuilder colIstrSecTyp = new TextColumnBuilder()
                .setId("colIstrSecTyp")
                .setName("colIstrSecTyp")
                .setLabel("Type");
        TextColumnBuilder colIstrSecEmt = new TextColumnBuilder()
                .setId("colIstrSecEmt")
                .setName("colIstrSecEmt")
                .setLabel("Emitter");
        TextColumnBuilder colIstrSecRtg = new TextColumnBuilder()
                .setId("colIstrSecRtg")
                .setName("colIstrSecRtg")
                .setLabel("Rating");
        NumericColumnBuilder colIstrSecRsk = new NumericColumnBuilder()
                .setId("colIstrSecRsk")
                .setName("colIstrSecRsk")
                .setLabel("Risk");
        grdSec.addColumn(colIstrSecId, colIstrSecNam, colIstrSecTyp, colIstrSecEmt, colIstrSecRtg, colIstrSecRsk);

        ButtonActionBuilder grdSecAddRow = new ButtonActionBuilder()
                .setId("grdSecAddRow")
                .setType(Action.ADD_ROW)
                .setTarget("grdSec")
                .setSilent(true);
        ButtonBuilder btnGrdSecInsObj = new ButtonBuilder()
                .setId("btnGrdSecIns2")
                .setLabel("BUTTON_NEW")
                .setIcon("plus-circle")
                .addButtonAction(grdSecAddRow);
        ButtonActionBuilder grdSecDelRow = new ButtonActionBuilder()
                .setId("grdSecDelRow")
                .setType(Action.DELETE_ROW)
                .setTarget("grdSec")
                .setSilent(true);
        ButtonBuilder btnGrdSecDelObj = new ButtonBuilder()
                .setId("btnGrdSecDel2")
                .setLabel("BUTTON_DELETE")
                .setIcon("trash")
                .addButtonAction(grdSecDelRow);
        grdSec.addButton(btnGrdSecInsObj, btnGrdSecDelObj);

        tabCtnSec2.addGrid(grdSec);

        GridBuilder grdPrc = new GridBuilder()
                .setId("grdPrc")
                .setInitialLoad(InitialLoad.QUERY)
                .setTargetAction("querIstrDtlPrc")
                .setStyle("expand")
                .setSendOperations(true);

        NumericColumnBuilder colIstrPrcId = new NumericColumnBuilder()
                .setId("colIstrPrcId")
                .setName("colIstrPrcId")
                .setLabel("Id")
                .setHidden(true);
        TextColumnBuilder colIstrPrcSec = new TextColumnBuilder()
                .setId("colIstrPrcSec")
                .setName("colIstrPrcSec")
                .setLabel("Security");
        CalendarColumnBuilder colIstrPrcDate = new CalendarColumnBuilder()
                .setId("colIstrPrcDate")
                .setName("colIstrPrcDate")
                .setLabel("Date");
        NumericColumnBuilder colIstrPrcPrc = new NumericColumnBuilder()
                .setId("colIstrPrcPrc")
                .setName("colIstrPrcPrc")
                .setLabel("Pricing");
        grdPrc.addColumn(colIstrPrcId, colIstrPrcSec, colIstrPrcDate, colIstrPrcPrc);

//        ButtonActionBuilder grdPrcAddRow = new ButtonActionBuilder()
//                .setId("grdPrcAddRow")
//                .setType(Action.ADD_ROW)
//                .setTarget("grdPrc")
//                .setSilent(true);
//        ButtonBuilder btnGrdPrcInsObj2 = new ButtonBuilder()
//                .setId("btnGrdPrcIns2")
//                .setLabel("BUTTON_NEW")
//                .setIcon("plus-circle");
//                .addButtonAction(grdPrcAddRow);
//        ButtonActionBuilder grdPrcDelRow = new ButtonActionBuilder()
//                .setId("grdPrcDelRow")
//                .setType(Action.DELETE_ROW)
//                .setTarget("grdPrc")
//                .setSilent(true);
//        ButtonBuilder btnGrdPrcDelObj = new ButtonBuilder()
//                .setId("btnGrdPrcDel")
//                .setLabel("BUTTON_DELETE")
//                .setIcon("trash");
//                .addButtonAction(grdPrcDelRow);

//        grdPrc.addButton(btnGrdPrcInsObj2
//                , btnGrdPrcDelObj
//        );

        tabCtnPrc2.addGrid(grdPrc);

        tabIstr.addTabContainerList(tabCtnSec2, tabCtnPrc2, tabCtnCli, tabCtnPfl, tabCtnPos);
        tagCenter.addTab(tabIstr);
        builder.addTag(tagButtons, tagCenter);

        return new ServiceData().setData(builder.build());
//        return builder.buildClientAction(getElements());
    }



}

