package org.example.repositories;

import org.example.models.Valuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface ValuationRepo extends JpaRepository<Valuation,Integer> {

    @Query(nativeQuery = true,value="select * from Valuation where asset_id = :asset_id and pricedate  = :pricedate")
    Optional<Valuation> findByAsset_id(@Param("asset_id") Integer asset_id,@Param("pricedate") Date pricedate);

}
