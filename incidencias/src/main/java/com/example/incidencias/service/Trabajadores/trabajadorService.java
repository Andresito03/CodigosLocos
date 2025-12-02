package com.example.incidencias.service.Trabajadores;

import java.util.List;

import com.example.incidencias.domain.Trabajador;
import com.example.incidencias.domain.incidencia;


public interface  trabajadorService {
    List<Trabajador> getAllTrabajador();
    Trabajador getTrabajadorById(long id);
    void deleteTrabajadorById(long id);
    void saveTrabajador(Trabajador Trabajador);
    void updateTrabajador(Trabajador Trabajador);
}

