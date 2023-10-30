package org.example.models;

import com.almis.awe.model.dto.CellData;
import com.almis.awe.model.dto.DataList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@EqualsAndHashCode
@Entity(name="PERSONTEST")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table-generator")
    @TableGenerator(
            name = "table-generator",
            table = "awekey",
            pkColumnName = "keynam",
            valueColumnName = "keyval",
            pkColumnValue = "persontestid",
            allocationSize = 1
    )
    private Integer Id;
    private String Name;
    private Boolean Alive;
    private LocalDate Birthdate;

    public Person(String Name, Boolean Alive, LocalDate Birthdate) {this.Name=Name; this.Alive=Alive; this.Birthdate=Birthdate;}

    public Person(Map<String, CellData> element) {
         Id = Integer.parseInt(element.get("GrdSvcColId").getStringValue());
         Name = element.get("GrdSvcColName").getStringValue();
         Alive = Boolean.parseBoolean(element.get("GrdSvcColAlive").getStringValue());
         Birthdate = LocalDate.parse(element.get("GrdSvcColBirthDate").getStringValue(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
