package com.example.incidencias.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "trabajadores")
@Data
public class Trabajador{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String apellido;    
    private String email;
    private float preciohora;

}
