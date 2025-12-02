package com.example.incidencias.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.Id;
import lombok.Data;

@Document(collection = "traducciones")
@Data
public class Traduccion {

    @Id
    private String id;

    private String key; // ej: "login", "username"
    private String es;  // español
    private String ca;  // catalán
    private String en;  // inglés
}

