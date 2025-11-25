package com.example.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.crud.domain.Persona;
import com.example.crud.service.CRUDservice;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ControllerCRUD {

    @Autowired
    CRUDservice service;

    @GetMapping("/Test")
    public String test(Model model) {
        List<Persona> personas = service.getAllPersonas();
        model.addAttribute("personas", personas);
        return "Test";
    }

    @GetMapping("EliminarConfirmacion/{id}")
    public String eliminarConfirmacion(@PathVariable long id, Model model) {
        Persona persona = service.getPersonaById(id);
        model.addAttribute("persona", persona);
        return "EliminarConfirmar";
    }
    
    @PostMapping("/Eliminar/{id}")
    public String deletePersona(@PathVariable long id) {
        service.deletePersonaById(id);
        return "redirect:/Test";
    }

    @GetMapping("/form")
    public String agregarUser(Model model) {
        model.addAttribute("persona", new Persona());
        return "FormularioUsuario";
    }

    @PostMapping("crear")
    public String postMethodName(@ModelAttribute Persona persona) {
    
        service.savePersona(persona);
        return "redirect:/Test";
    }
    
    @GetMapping("/edit/{id}")
    public String editarUsuario(@PathVariable long id, Model model) {
        Persona persona = service.getPersonaById(id);
        model.addAttribute("persona", persona);
        return "Actualizar";
    }
    
    @PostMapping("/Actualizar/{id}")
    public String actualizarUsuario(@PathVariable long id, @ModelAttribute Persona persona) {              
        service.updatePersona(persona);
        return "redirect:/Test";
    }   

}
