package com.example.incidencias.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados")
@Data
public class Trabajador{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;

}
