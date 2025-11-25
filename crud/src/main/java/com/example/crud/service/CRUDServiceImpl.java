package com.example.crud.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.crud.domain.Persona;
import com.example.crud.repository.PersonaDAO;

@Service
public class CRUDServiceImpl implements CRUDservice {

    private final PersonaDAO personaDAO;

    public CRUDServiceImpl(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    @Override
    public List<Persona> getAllPersonas() {
        return personaDAO.findAll();
    }

    @Override
    public Persona getPersonaById(long id) {
        return personaDAO.findById(id).orElse(null);
    }

    @Override
    public void deletePersonaById(long id) {
        personaDAO.deleteById(id);
    }

    @Override
    public void savePersona(Persona persona) {
        personaDAO.save(persona);
    }

    @Override
    public void updatePersona(Persona persona) {
        personaDAO.save(persona);
    }
}
