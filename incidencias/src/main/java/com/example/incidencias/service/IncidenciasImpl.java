package com.example.incidencias.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.incidencias.dao.EstadoDao;
import com.example.incidencias.dao.IncidenciasDao;
import com.example.incidencias.domain.Estado;
import com.example.incidencias.domain.incidencia;

@Service
public class IncidenciasImpl implements incidenciasService {

    @Autowired
    private IncidenciasDao Dao;

    @Autowired
    private EstadoDao estadoRepo;

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

        // Fecha de apertura actual
        incidencia.setFechaApertura(LocalDate.now());

        // Obtener el estado con ID 1 ("ABIERTA")
        Estado estadoAbierta = estadoRepo.findById(1L).orElse(null);

        incidencia.setEstado(estadoAbierta);

        // Guardar incidencia
        Dao.save(incidencia);
    }

    @Override
    public void updateIncidencia(incidencia incidencia) {
        // Si el estado es "CERRADA", se pone la fecha de cierre actual
        if ("CERRADA".equals(incidencia.getEstado().getNombre())) {
            incidencia.setFechaCierre(LocalDate.now());
        }

        // Calcular tiempo de resolución si ya se cerró
        if (incidencia.getFechaCierre() != null) {
            long dias = ChronoUnit.DAYS.between(incidencia.getFechaApertura(), incidencia.getFechaCierre());
            incidencia.setTiempoResolucion(dias + " días");
        }

        // Guardar cambios
        Dao.save(incidencia);
    }

}
