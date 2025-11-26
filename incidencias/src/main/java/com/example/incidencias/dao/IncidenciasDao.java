package com.example.incidencias.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.incidencias.domain.incidencia;

@Repository
public interface IncidenciasDao extends JpaRepository<incidencia, Long> {
    
}
