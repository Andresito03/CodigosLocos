package com.example.incidencias.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.incidencias.domain.Estado;
import com.example.incidencias.domain.incidencia;

@Repository
public interface EstadoDao extends JpaRepository<Estado, Long> {

}
