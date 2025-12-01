package com.example.incidencias.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.incidencias.dao.EstadoDao;
import com.example.incidencias.domain.Estado;

@Service
public class EstadoImpl implements EstadoService {

    @Autowired
    private EstadoDao Dao;

    @Override
    public List<Estado> getAllEstado() {
        return Dao.findAll();
    }

    @Override
    public Estado getEstadoById(long id) {
        return Dao.findById(id).orElse(null);
    }

    @Override
    public void deleteEstadoById(long id) {
        Dao.deleteById(id);
    }

    @Override
    public void saveEstado(Estado Estado) {
        Dao.save(Estado);
    }


}
