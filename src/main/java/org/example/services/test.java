//package com.almis.ore.service;
//
//import com.almis.awe.builder.client.ScreenActionBuilder;
//import com.almis.awe.builder.client.SelectActionBuilder;
//import com.almis.awe.config.ServiceConfig;
//import com.almis.awe.exception.AWException;
//import com.almis.awe.model.dto.CellData;
//import com.almis.awe.model.dto.DataList;
//import com.almis.awe.model.dto.ServiceData;
//import com.almis.awe.model.entities.actions.ClientAction;
//import com.almis.awe.model.entities.menu.Menu;
//import com.almis.awe.model.entities.menu.Option;
//import com.almis.awe.model.entities.screen.Include;
//import com.almis.awe.model.entities.screen.Screen;
//import com.almis.awe.model.entities.screen.Tag;
//import com.almis.awe.model.entities.screen.component.Resizable;
//import com.almis.awe.model.entities.screen.component.Window;
//import com.almis.awe.model.entities.screen.component.action.Dependency;
//import com.almis.awe.model.entities.screen.component.action.DependencyElement;
//import com.almis.awe.model.entities.screen.component.container.TabContainer;
//import com.almis.awe.model.entities.screen.component.grid.Column;
//import com.almis.awe.model.entities.screen.component.grid.Grid;
//import com.almis.awe.model.entities.screen.component.grid.GroupHeader;
//import com.almis.awe.model.entities.screen.component.panelable.Tab;
//import com.almis.awe.service.QueryService;
//import com.almis.ore.constants.OreConstants;
//import com.almis.ore.entity.*;
//import com.almis.ore.repository.*;
//import com.almis.ore.service.test.awe.GridService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import java.util.*;
//@Service
//@Slf4j
//public class test extends ServiceConfig {
//
//    private String optionMenu;
//    @Autowired
//    GridService gridService;
//
//    @Autowired
//    ReportEntityRepository entityRepository;
//    @Autowired
//    TabsRepository tabsRepository;
//    @Autowired
//    GroupHeaderRepository ghRepository;
//    @Autowired
//    ColumnEntityRepository columnEntityRepository;
//    @Autowired
//    DependencyEntityRepository dependencyEntityRepository;
//    @Autowired
//    GridsRepository gridsRepository;
//    @Autowired
//    QueryService queryService ;
//    @Value("${application.files.menu.private:private}")
//    private String privateMenu;
//    public void storeScreen(Screen screen, Menu menu) {
//        getElements().setScreen(screen);
//        getElements().setMenu(menu.getId(), menu);
//    }
//
//    /* public ServiceData getData(Integer idRep, Integer idSta, String stateType, String stateCode) throws AWException {
//       ServiceData serviceData;
//       serviceData = gridService.getGridStateV2(idRep,idSta,null);
//       return  serviceData;
//     }*/
//    public  ServiceData getDataV2(Integer idRep, Integer idSta) throws AWException {
//        ServiceData serviceData;
//        if(idRep == null || idSta == null){
//            throw new AWException("");
//        }
//        else {
//            serviceData = gridService.getGridStateV2(idRep,idSta,null);
//        }
//        return  serviceData;
//    }
//
//    public ServiceData goToScreen2(Integer stateId, String typeEst, String cfgEst, String desEst) throws AWException {
//        ServiceData serviceData = new ServiceData();
//
//        String screeIdDyn = "State-" + typeEst + "-" + cfgEst ;
//        String menuId = this.privateMenu;
//        Menu menu = new Menu();
//        menu.setId(menuId);
//        menu = getElements().getMenu("private");
//
//        Option option = new Option();
//        option.setLabel("StateKey");
//        option.setName(screeIdDyn);
//        option.setScreen(screeIdDyn);
//        option.setInvisible(true);
//        menu.addElement(option);
//
//        Window window = new Window();
//        window.setId("window1");
//        window.setLabel(desEst);
//        window.setStyle("expand");
//        window.setExpand("horizontal");
//        window.setMaximize(true);
//        Resizable resizable = new Resizable();
//        resizable.setId("resizable1");
//        resizable.setDirections("right");
//        resizable.setStyle("col-xs-7 no-padding");
//
//        Include include = new Include();
//        include.setId("include1");
//        include.setTargetScreen("OreStaMnt");
//        include.setTargetSource("OreStaMntGrdErr");
//        include.setElementList(getElements().getScreen("OreStaMnt").getElementList().get(2).getElementList());
//        Screen s = getElements().getScreen("loadNewScreen");
//
//        Tab tab = generateDynamicTab(stateId);
//        s.setId(screeIdDyn);
//        resizable.addElement(tab);
//        window.addElement(resizable);
//        window.addElement(include);
//
//        if(s.getElementList().get(1).getElementList().size() > 1){
//            s.getElementList().get(1).getElementList().remove(1);
//            s.getElementList().get(1).getElementList().add(window);
//        }
//        else {
//            s.getElementList().get(1).getElementList().add(window);
//        }
//
//        if(s.getElementList().get(1).getElementList().get(1).getElementList().get(0).getElementList().size() > 0){
//            s.getElementList().get(1).getElementList().get(1).getElementList().get(0).getElementList().remove(0);
//            s.getElementList().get(1).getElementList().get(1).getElementList().get(0).getElementList().add(tab);
//        }
//        else {
//            s.getElementList().get(1).getElementList().get(1).getElementList().get(0).getElementList().add(tab);
//        }
//        storeScreen(s,menu);
//        redirectTo(serviceData, screeIdDyn);
//
//        return serviceData;
//    }
//
//
//    public ServiceData goBack() throws AWException {
//        ServiceData serviceData = new ServiceData();
//        String screenTarget = "loadGridSav";
//        Screen s = getElements().getScreen("loadNewScreen");
//        s.getElementList().get(1).getElementList().get(1).getElementList().clear();
//        redirectTo(serviceData, screenTarget);
//        return serviceData;
//    }
//    private void redirectTo(ServiceData serviceData, String screenTarget) {
//        ClientAction clientAction = new ClientAction(OreConstants.TARGET_ACTION_SCREEN);
//        clientAction.setTarget(screenTarget);
//        clientAction.setParameterMap(new HashMap<>());
//        serviceData.addClientAction(clientAction);
//    }
//    public Tab generateDynamicTabV1(Integer stateId){
//        List<Tabs> listTabs  = tabsRepository.findByStateId(stateId);
//        Tab tab = new Tab();
//        tab.setId("Tab1");
//        tab.setInitialLoad("enum");
//        tab.setTargetAction(listTabs.get(0).getTarget());
//        tab.setStyle("expand bordered");
//
//        for (int i = 0; i < listTabs.size() ; i++) {
//
//            Tabs tabs = listTabs.get(i);
//            Integer s = tabs.getId();
//            List<Grids> listgrid = gridsRepository.findByTabId(s);
//            Integer k = i+1 ;
//            TabContainer tabContainer = new TabContainer();
//            tabContainer.setId(listTabs.get(i).getTabValue());
//            tabContainer.setExpand("vertical");
//            tabContainer.setStyle("expand");
//            Window window = new Window();
//            window.setStyle("expand");
//            window.setExpand("horizontal");
//            window.setMaximize(true);
//            window.setId("window"+k);
//
//            Resizable resizable = new Resizable();
//            resizable.setLabel("test");
//            resizable.setDirections("right");
//            resizable.setStyle("col-xs-7 no-padding");
//
//            Include include = new Include();
//            include.setTargetSource("OreStaMntGrdErr");
//            include.setTargetScreen("OreStaMnt");
//
//            for (int j = 0; j < listgrid.size() ; j++) {
//                Grids gridEntity = listgrid.get(j);
//
//                List<GroupHeaderEntity> headerList = ghRepository.findByGridId(gridEntity.getId());
//
//                for (int l = 0; l < headerList.size() ; l++) {
//                    List<ColumnEntity> columnEntities = columnEntityRepository.findByHeaderId(headerList.get(l).getId());
//                    for (int m = 0; m < columnEntities.size()  ; m++) {
//
//                    }
//                }
//
//
//                Grid grid = getGrid(gridEntity);
//                tabContainer.addElement(grid);
//
//
//
//            }
//            tab.addElement(tabContainer);
//            resizable.addElement(tab);
//            window.addElement(resizable);
//            window.addElement(include);
//
//
//        }
//        return  tab;
//    }
//    public Tab generateDynamicTab(Integer stateId){
//        List<Tabs> listTabs  = tabsRepository.findByStateId(stateId);
//
//        Tab tab = new Tab();
//        tab.setId("Tab1");
//        tab.setInitialLoad("enum");
//        tab.setTargetAction(listTabs.get(0).getTarget());
//        tab.setStyle("expand bordered");
//        tab.setMaximize(true);
//
//        for (int i = 0; i < listTabs.size(); i++) {
//
//            Tabs tabs = listTabs.get(i);
//            Integer s = tabs.getId();
//            List<Grids> listgrid = gridsRepository.findByTabId(s);
//            Integer k = i + 1;
//            TabContainer tabContainer = new TabContainer();
//            tabContainer.setId(listTabs.get(i).getTabValue());
//            tabContainer.setExpand("vertical");
//            tabContainer.setStyle("expand");
//
//            for (int j = 0; j < listgrid.size(); j++) {
//                Grids gridEntity = listgrid.get(j);
//
//                Grid grid1 = getGridV2(gridEntity);
//                List<GroupHeaderEntity> headerList = ghRepository.findByGridId(gridEntity.getId());
//                Integer numCol = 0;
//                grid1.addElement(getColumnLblV2(gridEntity));
//                for (int l = 0; l < headerList.size(); l++) {
//                    GroupHeader groupHeader = getGroupHeader(headerList.get(l));
//                    List<ColumnEntity> columnEntities = columnEntityRepository.findByHeaderId(headerList.get(l).getId());
//                    for (int m = 0; m < columnEntities.size(); m++) {
//
//                        Column column = getColumnKeyVal(columnEntities.get(m), numCol);
//                        List<DependencyEntity> dependencyEntities = dependencyEntityRepository.findByColumnId(columnEntities.get(m).getId());
//                        if (dependencyEntities.size() > 0) {
//                            Dependency dependency = getDependencyShwKey(dependencyEntities.get(i).getColumnType(),gridEntity.getName(), numCol);
//                            column.addElement(dependency);
//                        }
//                        groupHeader.addElement(column);
//                        if ("Value".equals(columnEntities.get(m).getColumnType())) {
//                            numCol++;
//                        }
//                    }
//                    grid1.addElement(groupHeader);
//
//                }
//                for (int v = 0; v < numCol; v++) {
//                    String c;
//                    if (v == 0) c = "";
//                    else c = String.valueOf(v);
//                    grid1.addElement(getColumnHidden("IdeOreRep" + c + "_" + grid1.getId()));
//                    grid1.addElement(getColumnHidden("IdeOreCfgEst" + c + "_" + grid1.getId()));
//                    grid1.addElement(getColumnHidden("IdeOreCfgEstKey" + c + "_" + grid1.getId()));
//                }
//
//                /*Grid grid = getGrid(gridEntity);*/
//                tabContainer.addElement(grid1);
//
//            }
//            tab.addElement(tabContainer);
//        }
//
//        return  tab;
//    }
//
//    public GroupHeader getGroupHeader(GroupHeaderEntity groupHeaderEntity){
//        GroupHeader groupHeader = new GroupHeader();
//        groupHeader.setLabel(groupHeaderEntity.getLbl());
//        groupHeader.setId(groupHeaderEntity.getName());
//        return  groupHeader;
//    }
//    public ServiceData getData() throws AWException {
//        ServiceData serviceData;
//        serviceData = gridService.getGridState(1,8,null,"EERR","A22");
//        return  serviceData;
//    }
//    /* public ServiceData getDataV2(Integer idRep, Integer idSta, String stateType, String stateCode) throws AWException {
//       ServiceData serviceData;
//       serviceData = gridService.getGridState(idRep,idSta,null,stateType,stateCode);
//       return  serviceData;
//     }*/
//    public Dependency getDependencyCfgKey(String  name, Integer i){
//        String  c = "";
//        if (i==0) c ="";
//        else c = String.valueOf(i);
//
//        Dependency dependency = new Dependency();
//        dependency.setTargetType("hide");
//        dependency.setInitial(true);
//        DependencyElement dependencyElement = new DependencyElement();
//        dependencyElement.setId(name);
//        dependencyElement.setColumn("CfgKey_"+ c + name);
//        dependencyElement.setAttribute("currentRowValue");
//        dependencyElement.setCondition("is empty");
//
//        return  dependency ;
//    }
//    public Dependency getDependencyShwKey( String columnType ,String name, Integer i){
//        String  c = "";
//        if (i==0) c ="";
//        else c = String.valueOf(i);
//
//        Dependency dependency = new Dependency();
//        dependency.setId("dep" + UUID.randomUUID());
//        dependency.setTargetType("hide");
//        dependency.setInitial(true);
//
//        DependencyElement dependencyElement = new DependencyElement();
//        dependencyElement.setId(name);
//        dependencyElement.setColumn(columnType + c + "_" + name);
//        dependencyElement.setAttribute("currentRowValue");
//        if("ShwKey".equals(columnType)){
//            dependencyElement.setCondition("eq");
//            dependencyElement.setValue("0");
//        }else
//            dependencyElement.setCondition("is empty");
//
//        dependency.addElement(dependencyElement);
//        return  dependency ;
//    }
//
//    public Grid getGrid(Grids grids){
//        return getGrid(grids.getName(), grids.getNumCol());
//    }
//    public Grid getGridV2(Grids grids){
//        Grid grid = new Grid();
//        grid.setId(grids.getName());
//        grid.setSendAll(true);
//        grid.setMultioperation(true);
//        grid.setEditable(true);
//        grid.setStyle("expand");
//        grid.setDisablePagination(true);
//        return grid ;
//    }
//    public Grid getGrid(String id, Integer numCol){
//        Grid grid = new Grid();
//        grid.setId(id);
//        grid.setSendAll(true);
//        grid.setMultioperation(true);
//        grid.setEditable(true);
//        grid.setStyle("expand");
//        grid.setDisablePagination(true);
//        getGrid2(grid,numCol);
//        return grid ;
//    }
//    public Grid getGrid2(Grid grid ,Integer numCol){
//        String name = grid.getId();
//
//        grid.addElement( getColumnLbl(name));
//
//        String  c = "";
//        for (int i = 0; i < numCol; i++) {
//            if (i==0) c ="";
//            else c = String.valueOf(i);
//
//            List<String> list = new ArrayList<>();
//            list.add("COLUMN_ORE_TOTAL");
//            list.add("COLUMN_ORE_BALANCE");
//            if(!list.isEmpty()){
//      /*  GroupHeader groupHeader  = getHeader(name,i,list);
//        groupHeader.addElement(getColumnKey(c+"_"+name));
//        groupHeader.addElement(getColumnValue(c+"_"+name));
//        grid.addElement(groupHeader)*/;
//                grid.addElement(getColumnKey(c+"_"+name));
//                grid.addElement(getColumnValue(c+"_"+name));
//            }else {
//                grid.addElement(getColumnKey(   c+"_"+name));
//                grid.addElement(getColumnValue(c+"_"+name));
//            }
//
//        }
//
//        for (int i = 0; i < numCol; i++) {
//            if (i==0) c ="";
//            else c = String.valueOf(i);
//            grid.addElement(getColumnHidden( "IdeOreRep"+c+"_"+name));
//            grid.addElement(getColumnHidden( "IdeOreCfgEst"+c+"_"+name));
//            grid.addElement(getColumnHidden( "IdeOreCfgEstKey"+c+"_"+name));
//        }
//        return grid ;
//    }
//    public Grid getGridWithHeader(Grid grid ,Integer numCol){
//        String name = grid.getId();
//        Integer id ;
//        grid.addElement( getColumnLbl(name));
//
//        String  c = "";
//        for (int i = 0; i < numCol; i++) {
//            if (i==0) c ="";
//            else c = String.valueOf(i);
//
//            List<String> list = new ArrayList<>();
//            list.add("COLUMN_ORE_TOTAL");
//            list.add("COLUMN_ORE_BALANCE");
//            if(!list.isEmpty()){
//      /*  GroupHeader groupHeader  = getHeader(name,i,list);
//        groupHeader.addElement(getColumnKey(c+"_"+name));
//        groupHeader.addElement(getColumnValue(c+"_"+name));
//        grid.addElement(groupHeader)*/;
//                grid.addElement(getColumnKey(c+"_"+name));
//                grid.addElement(getColumnValue(c+"_"+name));
//            }else {
//                grid.addElement(getColumnKey(   c+"_"+name));
//                grid.addElement(getColumnValue(c+"_"+name));
//            }
//
//        }
//
//        for (int i = 0; i < numCol; i++) {
//            if (i==0) c ="";
//            else c = String.valueOf(i);
//            grid.addElement(getColumnHidden( "IdeOreRep"+c+"_"+name));
//            grid.addElement(getColumnHidden( "IdeOreCfgEst"+c+"_"+name));
//            grid.addElement(getColumnHidden( "IdeOreCfgEstKey"+c+"_"+name));
//        }
//        return grid ;
//    }
//
//    public GroupHeader getHeader(String name, Integer i, List<String> lbls){
//        GroupHeader groupHeader = new GroupHeader();
//        groupHeader.setId(name+"Grp"+i);
//        groupHeader.setLabel(lbls.get(i));
//        return groupHeader ;
//    }
//    public Column getColumnLbl(String name){
//        Column column = new Column();
//        column.setName("Lbl_" + name);
//        column.setAlign("left");
//        column.setCharLength(55);
//        column.setType("string");
//        column.setSortable(false);
//        column.setStyle("grey2");
//        column.setLabel("COLUMN_ORE_AVAILABLE");
//        return column ;
//    }
//    public Column getColumnLblV2(Grids grids){
//        Column column = new Column();
//        column.setName("Lbl_" + grids.getName());
//        column.setAlign("left");
//        column.setCharLength(55);
//        column.setType("string");
//        column.setSortable(false);
//        column.setStyle("grey2");
//        column.setLabel(grids.getLabelColumn());
//        return column ;
//    }
//
//    public Column getColumnKeyVal(ColumnEntity  columnEntity, Integer numCol){
//        String c ;
//        if (numCol==0) c ="";
//        else c = String.valueOf(numCol);
//        Column column = new Column();
//        column.setName(columnEntity.getColumnType()+ c + "_" + columnEntity.getGroupId().getGrids().getName());
//        column.setAlign("right");
//        column.setCharLength(15);
//        if(columnEntity.getColumnType().equals("CfgKey") ){
//            column.setType("integer");
//            column.setStyle("grey1");
//            column.setLabel(columnEntity.getLbl());
//        }
//        else if(columnEntity.getColumnType().equals("Value")){
//            column.setLabel(columnEntity.getLbl());
//            if(OreConstants.STATIC_COL_VALUE_COMPONENT_NUMERIC.equals(columnEntity.getComponentType())) {
//                column.setType("float");
//                column.setComponentType(OreConstants.STATIC_COL_VALUE_COMPONENT_NUMERIC);
//                column.setNumberFormat("{mDec: 2}");
//            }
//            else if(OreConstants.ORE_LITERAL_SELECT.equals(columnEntity.getComponentType())){
//                column.setComponentType(OreConstants.ORE_LITERAL_SELECT);
//                column.setInitialLoad("enum");
//                column.setTargetAction("EnuValYesNo");
//                column.setOptional(false);
//            }
//            else if("string".equals(columnEntity.getComponentType())){
//                column.setType("string");
//                column.setComponentType("text");
//                column.setNumberFormat("{mDec: 2}");
//            }
//
//        }/*else if(columnEntity.getColumnType().equals("Value") && columnEntity.getComponentType().equals("string")){
//    }*/
//        column.setSortable(false);
//
//
//        return column ;
//    }
//    public Column getColumnKey(String name){
//        Column column = new Column();
//        column.setLabel("COLUMN_ORE_KEY");
//        column.setName("CfgKey"+name);
//        column.setAlign("right");
//        column.setCharLength(15);
//        column.setType("integer");
//        column.setSortable(false);
//        column.setStyle("grey1");
//        return column ;
//    }
//    public Column getColumnValue(String name){
//        Column column = new Column();
//        column.setLabel("COLUMN_ORE_AMOUNT");
//        column.setName("Value"+name);
//        column.setAlign("right");
//        column.setCharLength(15);
//        column.setType("float");
//        column.setSortable(false);
//        column.setComponentType("numeric");
//        column.setNumberFormat("{mDec: 2}");
//        return column ;
//    }
//    public Column getColumnHidden(String name){
//        Column column = new Column();
//        column.setName(name);
//        column.setHidden(true);
//        return column ;
//    }
//
//    public ServiceData loadStateScreen(Integer stateId) throws AWException {
//
//        Tab tab = generateDynamicTab(stateId);
//        Screen s = getElements().getScreen("loadNewScreen");
//
//        if(s.getElementList().get(1).getElementList().size() > 1){
//            /*s.getElementList().get(1).getElementList().set(1, tab);*/
//            s.getElementList().get(1).getElementList().remove(1);
//            s.getElementList().get(1).getElementList().add(tab);
//        }
//        else {
//            s.getElementList().get(1).getElementList().add(tab);
//        }
//        ServiceData serviceData = new ServiceData();
//        String screenTarget = "loadNewScreen";
//
//        ClientAction clientAction = new ClientAction(OreConstants.TARGET_ACTION_SCREEN);
//        clientAction.setTarget(screenTarget);
//        clientAction.setParameterMap(new HashMap<>());
//        serviceData.addClientAction(clientAction);
//
//        return serviceData;
//    }
//    public ServiceData loadStateScreen3() throws AWException {
//
///*    Tag tagButtons = new Tag();
//    tagButtons.setSource("buttons");
//    tagButtons.setId("tagB");
//
//    Include include = new Include();
//    include.setTargetScreen("OreStaMnt");
//    include.setTargetSource("OreStaMntButBar");
//    include.setId("include1");*/
//        /*   tagButtons.addElement(include);*/
//    /*Tag tag1 = new Tag();
//    tag1.setSource("buttons");
//    Button button = new Button();
//    button.setType("button");
//    button.setIcon("print");
//    button.setLabel("PRINT");
//    button.setId("btn1");
//    tag1.addElement(button);*/
//        // tag1.generateTemplate();
//        // window
//  /*  Tag tag2 = new Tag();
//    tag2.setStyle("expand");
//    tag2.setExpand("vertical");
//    tag2.setType("div");*/
//        Window window = new Window();
//        window.setStyle("expand");
//        window.setExpand("horizontal");
//        window.setMaximize(true);
//        window.setId("window1");
//        Window window2 = new Window();
//        window2.setStyle("expand");
//        window2.setExpand("horizontal");
//        window2.setMaximize(true);
//        window2.setId("window2");
//
//        Grid grid = new Grid();
//        grid.setId("grid1");
//        grid.setStyle("expand");
//        grid.setInitialLoad("query");
//        grid.setTargetAction("OreGetDtaStaGrpMntTest");
//        grid.setServerAction("data");
//        grid.setMultiselect(true);
//        grid.setMultioperation(true);
//        grid.setMax(50);
//        grid.setSendAll(false);
//        grid.setMultioperation(false);
//        grid.setEditable(false);
//
//        Grid grid2 = new Grid();
//        grid2.setId("grid2");
//        grid2.setStyle("expand");
//        grid2.setInitialLoad("query");
//        grid2.setTargetAction("OreGetDtaStaGrpMntTest");
//        grid2.setServerAction("data");
//        grid2.setMultiselect(true);
//        grid2.setMultioperation(true);
//        grid2.setMax(50);
//        grid2.setSendAll(false);
//        grid2.setMultioperation(false);
//        grid2.setEditable(false);
//
//        Column column = new Column();
//        column.setName("ID");
//        column.setLabel("id");
//        column.setCharLength(10);
//        column.setComponentType("numeric");
//        Column column2 = new Column();
//        column2.setName("Name");
//        column2.setLabel("name");
//        column2.setCharLength(40);
//        column2.setComponentType("text");
//
//        Tab tab = new Tab();
//        tab.setId("tab1");
//        tab.setInitialLoad("enum");
//        tab.setTargetAction("EnuTest");
//        tab.setStyle("expand bordered");
//
//        TabContainer tabContainer = new TabContainer();
//        tabContainer.setId("test1");
//        tabContainer.setExpand("vertical");
//        tabContainer.setStyle("expand");
//        TabContainer tabContainer2 = new TabContainer();
//        tabContainer2.setId("test2");
//        tabContainer2.setExpand("vertical");
//        tabContainer2.setStyle("expand");
//        grid.addElement(column);
//        grid.addElement(column2);
//        window.addElement(grid);
//        tabContainer.addElement(window);
//
//        tab.addElement(tabContainer);
//        tab.addElement(tabContainer2);
//        Grid grid3 = getGrid("GrdStaEERRA21Tab1", 2);
//  /*  Grid grid4 = getGrid("GrdStaEERRA1Tab11", 1);
//    Grid grid5 = getGrid("GrdStaEERRA1Tab12", 1);*/
//        window2.addElement(grid3);
///*    window2.addElement(grid4);
//    window2.addElement(grid5);*/
//        tabContainer2.addElement(window2);
//
//
///*    List<GridState> getEERRStateA1Data(Integer idRep, Integer idSta, Integer idEtt, String typeState, String stateCode);
//    getGridState(Integer idRep, Integer idSta, Integer idEtt,String stateType, String stateCode);*/
//        Screen s = getElements().getScreen("loadNewScreen");
//        /*s.getElementList().get(1).addElement(tab);*/
//
//   /* if(s.getElementList().get(0).getElementList().size() > 1){
//      s.getElementList().get(0).getElementList().remove(1);
//      s.getElementList().get(0).getElementList().add(tag2);
//    }
//    else {
//      s.getElementList().get(0).getElementList().add(tag2);
//    }*/
//        if(s.getElementList().get(1).getElementList().size() > 1){
//            s.getElementList().get(1).getElementList().remove(1);
//            s.getElementList().get(1).getElementList().add(tab);
//        }
//        else {
//            s.getElementList().get(1).getElementList().add(tab);
//        }
//        ServiceData serviceData = new ServiceData();
//        //serviceData = gridService.getGridState(1,6,null,"EERR","A1");
//
//        /*s.getElementList().add(tag1);*/
//        String screenTarget = "loadNewScreen";
//
//        ClientAction clientAction = new ClientAction(OreConstants.TARGET_ACTION_SCREEN);
//        clientAction.setTarget(screenTarget);
//        clientAction.setParameterMap(new HashMap<>());
//        serviceData.addClientAction(clientAction);
//
//        return serviceData;
//    }
//
//    public ServiceData loadStateScreen2(){
//        ServiceData serviceData = new ServiceData();
//
//        Tag tag = new Tag();
//        tag.setSource("center");
//        tag.setId("tag1");
//        Window window = new Window();
//        window.setId("window1");
//        window.setLabel("TITLE_ORE_LOAD_STATE_A21");
//        window.setStyle("expand");
//        window.setExpand("horizontal");
//        window.setMaximize(true);
//        tag.addElement(window);
//        Tab tab = new Tab();
//        tab.setId("OreTabStaEERRA21");
//        tab.setInitialLoad("enum");
//        tab.setTargetAction("EnuTabStaEERRA21");
//        tab.setStyle("expand");
//        tab.setMaximize(true);
//        window.addElement(tab);
//        TabContainer tabContainer = new TabContainer();
//        tabContainer.setId("TabStaEERRA21Tab1");
//        tabContainer.setStyle("expand bordered");
//        tabContainer.setExpand("vertical");
//        tab.addElement(tabContainer);
//
//
//        String screenTarget = "loadNewScreen2";
//        ClientAction clientAction = new ClientAction(OreConstants.TARGET_ACTION_SCREEN);
//        clientAction.setTarget(screenTarget);
//        clientAction.setParameterMap(new HashMap<>());
//        serviceData.addClientAction(clientAction);
//        return serviceData;
//    }
//
//    public ServiceData load() throws AWException {
//        ServiceData serviceData = new ServiceData();
//
///*
//   List<Element> elementList = new ArrayList<>();
//
//    Tag tag1 = new Tag();
//    tag1.setSource("buttons");
//    Button button = new Button();
//    button.setType("button");
//    button.setIcon("print");
//    button.setLabel("PRINT");
//    button.setId("btn1");
//    tag1.addElement(button);
//   // tag1.generateTemplate();
//
//    // window
//    Tag tag2 = new Tag();
//    tag2.setStyle("expand").setExpand("vertical");
//    Window window = new Window();
//    window.setStyle("expand").setExpand("vertical");
//    Grid grid = new Grid();
//    grid.setId("grid1");
//    grid.setStyle("expand");
//    grid.setInitialLoad("query");
//    grid.setTargetAction("");
//    grid.setServerAction("data");
//    grid.setMultiselect(true);
//    grid.setMultioperation(true);
//    Column column = new Column();
//    column.setName("ID");
//    column.setLabel("id");
//    column.setCharLength(10);
//    column.setComponentType("numeric");
//    Column column2 = new Column();
//    column2.setName("Name");
//    column2.setLabel("name");
//    column2.setCharLength(40);
//    column2.setComponentType("text");
//    grid.addElement(column);
//    grid.addElement(column2);
//    window.addElement(grid);
//    tag2.addElement(window);
//    tag1.addElement(tag2);
//
//    //getElements().getApplicationContext().
//    elementList.add(tag1);
//    Screen s = getElements().getScreen("loadScreen");
//    s.getElementList().add(tag1);
//    s.setInitialized(true);
//    getElements().setScreen(s);
//*/
///*
//    getElements().setMenu()
//*//*
//
//    Screen loadScreen = getElements().getScreen("loadScreen");
//    loadScreen.getElementList();
//     //serviceData = screenBuilder.buildClientAction(getElements());
//
//
//*/
//        return (new ServiceData()).addClientAction(new ClientAction());
//    }
//
//    public ServiceData loadGrid() throws AWException {
//        ServiceData serviceData = new ServiceData();
//
//        String optionMenu = getTypeEtt();
//
//        List<ReportEntity> entities = entityRepository.findByTypEtt(optionMenu);
//        DataList dataList = new DataList();
//
//        for (int i = 0; i < entities.size(); i++) {
//            Map<String, CellData> row = new HashMap<>();
//            row.put("label", new CellData(entities.get(i).getEtt()));
//            row.put("value", new CellData(entities.get(i).getId()));
//            dataList.addRow(row);
//        }
//        serviceData.addClientAction(new SelectActionBuilder("CrtStaEtt", dataList).build());
//   /* getRequest().getParameters();
//    List<Element> elementList = new ArrayList<>();
//    Element tag = new Tag();
//    tag.setStyle("static panel-body");
//    tag.setType("div");
//    Criteria criteria = new Criteria();
//    criteria.setId("CrtLoad");
//    criteria.setComponentType("select");
//    criteria.setLabel("Load Data");
//    tag.addElement(criteria);
//
//    elementList.add(tag);
//
//    Window window = new Window();
//    window.setStyle("static criteria");
//    window.setLabel("LoadGrid");
//    window.setElementList(elementList);
//    ClientAction clientAction = screenAction("loadScreen");
//    Window build = new WindowBuilder().build();
//    ClientAction clientAction2 = new ClientAction();
//    clientAction2.setTarget("");
//    List<String> values = new ArrayList();
//
//    serviceData.addClientAction(new SelectActionBuilder("CrtLoad",values).build());
//    //serviceData.addClientAction(new SelectActionBuilder("CrtLoad",values).build());
//    Screen loadScreen = getElements().getScreen("loadScreen");
//    loadScreen.getElementList();
//    *//*Screen s = getElements().getScreen("loadScreen");
//    s.getElementList().add(window);*//*
//    Screen s = getElements().getScreen("loadScreen");
//    s.getElementList().add(tag);
//    ScreenActionBuilder loadGridSav = new ScreenActionBuilder("loadGridSav", true);
//    getElements().setScreen(s);
//    ScreenBuilder builder = new ScreenBuilder();
//    Screen build1 = builder.build(s);
//    serviceData.addClientAction(new ScreenActionBuilder("loadGridSav",true).build());
//*//*
//    this.baseConfigProperties.getPaths().getScreen()
//*//*
//        System.out.println("load");
//   *//* ServiceData serviceData2 = builder
//        .setMenuType("private")
//        .buildClientAction(getElements());*//*
//    serviceData.addClientAction(new SelectActionBuilder("my-select", values).build());
//    */
///*
//    List<Element> elementList = new ArrayList<>();
//
//    Tag tag1 = new Tag();
//    tag1.setSource("buttons");
//*/
//
// /*   Criteria criteria = new Criteria();
//    criteria.setId("CrtLoad");
//    criteria.setComponentType("select");
//    criteria.setLabel("Load Data");
//    tag1.addElement(criteria);*/
//
//  /*  Button button = new Button();
//    button.setType("button");
//    button.setIcon("print");
//    button.setLabel("PRINT");
//    button.setId("btn1");
//    tag1.addElement(button);
//    elementList.add(tag1);
//    Screen s = getElements().getScreen("loadScreen");
//    s.getElementList().add(tag1);
//    s.toBuilder().build();
//    getElements().setScreen(s);
//    Screen loadScreen = getElements().getScreen("loadScreen");
//    loadScreen.getElementList();
//    serviceData.addClientAction(new ScreenActionBuilder("loadGridSav").build());
//*/
//
///*    ScreenActionBuilder loadGridSav = new ScreenActionBuilder("loadGridSav", true);
//    getRequest().getHttpRequest();
//    Menu menu = getElements().getMenu("private");
//    ActionBuilder.buildScreenAction(menu,"loadGridSav");*/
//
///*
//    Grid grid = new Grid();
//    grid.setId("grid1");
//    grid.setTargetAction("getData");
//    Dependency dependency = new Dependency();
//    dependency.setInitial(true);
//    dependency.setSourceType("");
//*/
//
//
//
//        return serviceData ;
//    }
//
//
//    public ClientAction screenAction(String screen) throws AWException {
//        String SCREEN = "screen",JSON_SCREEN = "screen", CONTEXT = "context",SESSION_TOKEN = "token", MENU_PRIVATE = "private";
//        //    UserController userController = new UserController();
////
//        ClientAction screenAction = new ClientAction(SCREEN);
////    screenAction.addParameter(SESSION_TOKEN, PropertySingleton.getInstance().getProperty(PropertyType.TOKEN));
////    // Return session LANGUAGE
////    screenAction.addParameter(SESSION_LANGUAGE, userController.getLanguage());
////    // Return session THEME
////    screenAction.addParameter(SESSION_THEME, userController.getUserTheme());
////    // Return HOME SCREEN
//        // Get menu
//        Menu menu = getElements().getMenu(MENU_PRIVATE);
//        menu.getElementList();
//        screenAction.addParameter(JSON_SCREEN, "/" + menu.getScreenContext() + "/" + screen);
//        return screenAction;
//    }
//
//    public ServiceData loadEntity() throws AWException {
//        ServiceData serviceData = new ServiceData();
//
//        String optionMenu = getTypeEtt();
//
//        List<ReportEntity> entities = entityRepository.findByTypEtt(optionMenu);
//        ServiceData serviceData2 = queryService.launchQuery("OreGetEttSav");
//
//        DataList dataList =  queryService.launchQuery("OreGetEttSav").getDataList();
//        DataList dataList2 = new DataList();
//        for (int i = 0; i < dataList.getRows().size(); i++) {
//            Map<String, CellData> row = new HashMap<>();
//            row.put("id", new CellData(i+1));
//            row.put("label", new CellData(entities.get(i).getEtt()));
//            row.put("value", new CellData(entities.get(i).getId()));
//            dataList2.addRow(row);
//        }
//
//        // serviceData.addClientAction(new ClientAction("select").setTarget("CrtRepVer").addParameter("values", Arrays.asList(dataList.getRows().get(0).get("value").getStringValue())));
//
//        return serviceData.setDataList(dataList);
//    }
//    public String  getTypeEtt(){
//        String optionMenu2 = getRequest().getParameterAsString("option");
//        if (optionMenu2 != null){
//            optionMenu = optionMenu2;
//        }
//        switch(optionMenu){
//            case "loadGridSav":
//                return "SAV" ;
//            case "loadGridIIC":
//                return "SGIIC" ;
//            default:
//                log.error("error : Not Found");
//                throw new IllegalStateException("Not Found");
//        }
//    }
//
//    public ServiceData buildClientAction(Screen s) throws AWException {
//        ClientAction clientAction = (new ScreenActionBuilder(s.getId())).build();
//        return (new ServiceData()).addClientAction(clientAction);
//    }
//}
