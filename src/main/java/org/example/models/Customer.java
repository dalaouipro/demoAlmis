package org.example.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer{
    private Integer id;
    private String companyname;
    private String fullname;
    private Boolean status;
    private Date registrationDate;

}
