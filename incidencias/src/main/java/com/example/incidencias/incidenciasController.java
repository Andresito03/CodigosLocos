package com.example.incidencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.incidencias.domain.incidencia;
import com.example.incidencias.service.incidenciasService;
import org.springframework.ui.Model;



@Controller
public class incidenciasController {

    @Autowired
    incidenciasService service;

    @GetMapping("/incidenciasLogin")
    public String Login() {
        return "incidenciasLogin";
    }

    @GetMapping("/incidencias")
    public String Incidencias(Model model) {
        List<incidencia> incidencias = service.getAllIncidencias();
        model.addAttribute("incidencias", incidencias);
        return "incidencias";
    }

}
