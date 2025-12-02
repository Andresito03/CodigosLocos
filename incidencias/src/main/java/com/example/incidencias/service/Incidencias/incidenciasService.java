package com.example.incidencias.service.Incidencias;

import java.util.List;
import com.example.incidencias.domain.incidencia;


public interface  incidenciasService {
    List<incidencia> getAllIncidencias();
    incidencia getIncidenciasById(long id);
    void deleteIncidenciasById(long id);
    void saveIncidencia(incidencia incidencia);
    void updateIncidencia(incidencia incidencia);
}

