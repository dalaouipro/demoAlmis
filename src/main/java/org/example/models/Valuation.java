package org.example.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity(name="VALUATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Valuation {
    @Id
    @Column
    Integer valuation_id;
    @Column
    Integer quantity;
    @Column
    Integer price;
    @Column
    Integer multiple;
    @Column
    Date pricedate;
    @Column
    Integer asset_id;

}
