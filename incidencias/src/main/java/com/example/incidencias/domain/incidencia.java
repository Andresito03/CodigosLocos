package com.example.incidencias.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "Incidencias")
@Data
public class incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private String descripcion;

    @Column(name = "fechaapertura")
    private LocalDate fechaApertura;

    @Column(name = "fechacierre")
    private LocalDate fechaCierre;

    @ManyToOne
    @JoinColumn(name = "estado")
    private Estado estado;

    @Column(name = "tiemporesolucion")
    private String tiempoResolucion;

    private String prioridad;

    // Solo guardamos el nombre del archivo
    private String imagen;
}

