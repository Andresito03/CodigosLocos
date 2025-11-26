package com.example.incidencias.service;

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
        Dao.save(incidencia);
    }

    @Override
    public void updateIncidencia(incidencia incidencia) {
        Dao.save(incidencia);
    }
}
