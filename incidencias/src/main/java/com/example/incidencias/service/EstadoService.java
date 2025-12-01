package com.example.incidencias.service;

import java.util.List;
import com.example.incidencias.domain.Estado;


public interface  EstadoService {
    List<Estado> getAllEstado();
    Estado getEstadoById(long id);
    void deleteEstadoById(long id);
    void saveEstado(Estado estado);
}

