package com.example.incidencias.service.Traduccion;

import com.example.incidencias.dao.TrabajadorDao;
import com.example.incidencias.dao.TraduccionDao;
import com.example.incidencias.domain.Traduccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TraduccionServiceImpl implements TraduccionService {

    @Autowired
    private TraduccionDao repository;

    @Override
    public List<Traduccion> getAllTraducciones() {
        return repository.findAll();
    }

    @Override
    public Optional<Traduccion> getTraduccionByKey(String key) {
        return repository.findByKey(key);
    }

    @Override
    public Traduccion saveTraduccion(Traduccion traduccion) {
        return repository.save(traduccion);
    }

    @Override
    public void deleteTraduccion(String id) {
        repository.deleteById(id);
    }

    @Override
    public Map<String, String> getTraduccionesPorIdioma(int idioma) {
        List<Traduccion> todas = repository.findAll();

        return todas.stream()
                .collect(Collectors.toMap(
                        Traduccion::getKey, // la clave de la traducción
                        t -> switch (idioma) {
                    case 1 ->
                        t.getEs();
                    case 2 ->
                        t.getCa();
                    case 3 ->
                        t.getEn();
                    default ->
                        t.getEs(); // español por defecto
                }
                ));
    }
}
