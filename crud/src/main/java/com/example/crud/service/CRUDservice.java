package com.example.crud.service;

import java.util.List;
import com.example.crud.domain.Persona;

public interface CRUDservice {
    List<Persona> getAllPersonas();
    Persona getPersonaById(long id);
    void deletePersonaById(long id);
    void savePersona(Persona persona);
    void updatePersona(Persona persona);
}
