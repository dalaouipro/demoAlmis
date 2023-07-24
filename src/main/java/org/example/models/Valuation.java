package org.example.models;

import lombok.*;
import javax.persistence.*;
import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Entity(name="VALUATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Valuation extends Auditable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
