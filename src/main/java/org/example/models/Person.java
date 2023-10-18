package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
}
