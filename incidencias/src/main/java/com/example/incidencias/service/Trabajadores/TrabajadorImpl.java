package com.example.incidencias.service.Trabajadores;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.incidencias.dao.EstadoDao;
import com.example.incidencias.dao.IncidenciasDao;
import com.example.incidencias.dao.TrabajadorDao;
import com.example.incidencias.domain.Estado;
import com.example.incidencias.domain.Trabajador;
import com.example.incidencias.domain.incidencia;
import com.example.incidencias.service.Incidencias.incidenciasService;
import com.example.incidencias.service.Trabajadores.trabajadorService;

@Service
public class TrabajadorImpl implements trabajadorService {

    @Autowired
    private TrabajadorDao Dao;

    @Override
    public List<Trabajador> getAllTrabajador() {
        return Dao.findAll();
    }

    @Override
    public Trabajador getTrabajadorById(long id) {
        return Dao.findById(id).orElse(null);
    }

    @Override
    public void deleteTrabajadorById(long id) {
        Dao.deleteById(id);
    }

    @Override
    public void saveTrabajador(Trabajador i) {
        Dao.save(i);
    }

    @Override
    public void updateTrabajador(Trabajador i) {
        Dao.save(i);
    }

}
