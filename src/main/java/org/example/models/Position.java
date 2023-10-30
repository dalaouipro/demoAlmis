package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@EqualsAndHashCode
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    @Id
    Integer id;
    String security;
    Integer quantity;
}
