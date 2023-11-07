package org.example.services;

import com.almis.awe.config.ServiceConfig;
import com.almis.awe.model.dto.CellData;
import com.almis.awe.model.dto.DataList;
import com.almis.awe.model.dto.ServiceData;
import com.almis.awe.model.entities.actions.ClientAction;
import org.example.models.Valuation;
import org.example.repositories.ValuationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValuationService extends ServiceConfig {
    @Autowired
    ValuationRepo valuationRepo;

        public ServiceData getValuation(){
            ServiceData serviceData = new ServiceData();
           // List<Valuation> valuationList = new ArrayList<>();
            Map<String, CellData> valuationMap = new HashMap<>();
            DataList valuationDataList = new DataList();
            valuationRepo.findAll().forEach(valuation -> {
               // valuationList.add(valuation);
                valuationMap.put("Colvaluation_id", new CellData(valuation.getValuation_id()));
                valuationMap.put("Colasset_id", new CellData(valuation.getAsset_id()));
                valuationMap.put("Colprice", new CellData(valuation.getPrice()));
                valuationMap.put("Colquantity", new CellData(valuation.getQuantity()));
                valuationMap.put("Colmultiple", new CellData(valuation.getMultiple()));
                // Add the map to the valuationDataList
                valuationDataList.addRow(valuationMap);
            });
                ClientAction clientAction = new ClientAction("fill");
                clientAction.setTarget("GrdValuation");
                clientAction.addParameter("datalist",valuationDataList);
                serviceData.addClientAction(clientAction);

           return serviceData;
    }
    public Valuation getValuationById(Integer id){
        return valuationRepo.findById(id).get();
    }
    public void deleteValuation (Integer id){
        valuationRepo.deleteById(id);
    }
    public Valuation save(Valuation valuation){
        return valuationRepo.save(valuation);
    }
}
