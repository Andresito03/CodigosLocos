package com.example.incidencias.dao;

import com.example.incidencias.domain.Traduccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface TraduccionDao extends MongoRepository<Traduccion, String> {
    Optional<Traduccion> findByKey(String key);
}
