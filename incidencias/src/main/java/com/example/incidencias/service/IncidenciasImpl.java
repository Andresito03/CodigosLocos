package com.example.incidencias.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.incidencias.dao.IncidenciasDao;
import com.example.incidencias.domain.incidencia;

@Service
public class IncidenciasImpl implements incidenciasService {

    @Autowired
    private IncidenciasDao Dao;

    @Override
    public List<incidencia> getAllIncidencias() {
        return Dao.findAll();
    }

    @Override
    public incidencia getIncidenciasById(long id) {
        return Dao.findById(id).orElse(null);
    }

    @Override
    public void deleteIncidenciasById(long id) {
        Dao.deleteById(id);
    }

    @Override
    public void saveIncidencia(incidencia incidencia) {
        incidencia.setFechaApertura(LocalDate.now());
        incidencia.setEstado("ABIERTA");
        Dao.save(incidencia);
    }

    @Override
    public void updateIncidencia(incidencia incidencia) {
        if (incidencia.getEstado().equals("CERRADA")) {
            incidencia.setFechaCierre(LocalDate.now());
        }
        long dias = ChronoUnit.DAYS.between(incidencia.getFechaApertura(), incidencia.getFechaCierre());
        incidencia.setTiempoResolucion(dias + " días");
        Dao.save(incidencia);
    }
}
