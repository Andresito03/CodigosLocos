package com.example.incidencias.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Estado")
@Data
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;

}
