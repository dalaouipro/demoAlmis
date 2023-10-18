package org.example.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDto {

    private String Name;
    private Boolean Alive;
    private LocalDate Birthdate;
}
