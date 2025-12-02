package com.example.incidencias.service.Traduccion;

import com.example.incidencias.domain.Traduccion;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TraduccionService {
    List<Traduccion> getAllTraducciones();
    Optional<Traduccion> getTraduccionByKey(String key);
    Traduccion saveTraduccion(Traduccion traduccion);
    void deleteTraduccion(String id);
    String getTraduccion(String key, String idioma);
    Map<String, String> getTraduccionesPorIdioma(int idioma);

}
