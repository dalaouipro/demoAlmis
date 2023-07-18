package org.example.services;

import org.example.models.Valuation;
import org.example.repositories.ValuationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ValuationService {
    @Autowired
    ValuationRepo valuationRepo;

        public List<Valuation> getValuation(){
        List<Valuation> valuationList = new ArrayList<>();
        valuationRepo.findAll().forEach(valuation -> {
            valuationList.add(valuation);
        } );
       return valuationList;
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
