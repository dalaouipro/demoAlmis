package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estatico {


    private String id;
    private String entidad;
    private String description;
    private String tipo;
    private String numReq;
    private String nif;
  //  private String act;
}
