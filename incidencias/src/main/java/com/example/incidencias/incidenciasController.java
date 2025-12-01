package com.example.incidencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.incidencias.domain.incidencia;
import com.example.incidencias.service.incidenciasService;
import com.example.incidencias.service.LoginService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class incidenciasController {

    @Autowired
    incidenciasService service;
    @Autowired
    LoginService loginService;

    @GetMapping("/incidenciasLogin")
    public String Login() {
        return "login";
    }

    @GetMapping("/incidencias")
    public String Incidencias(Model model) {
        List<incidencia> incidencias = service.getAllIncidencias();
        model.addAttribute("incidencias", incidencias);
        return "incidencias";
    }

    @GetMapping("/form")
    public String getMethodName(Model model) {
        model.addAttribute("incidencia", new incidencia());
        return "Formulario";
    }

    @PostMapping("/guardarIncidencias")
    public String saveIncidencia(incidencia incidencia) {
        service.saveIncidencia(incidencia);
        return "redirect:/incidencias";
    }

    @GetMapping("/incidencias/eliminar/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        incidencia incidencia = service.getIncidenciasById(id);
        model.addAttribute("incidencia", incidencia);
        return "EliminarConfirmar";
    }

    @GetMapping("/incidencias/eliminarOK/{id}")
    public String getMethodName(@PathVariable Long id) {
        service.deleteIncidenciasById(id);
        return "redirect:/incidencias";
    }

    @GetMapping("/editarIncidenciasForm/{id}")
    public String editarIncidencia(@PathVariable Long id, Model model) {
        incidencia incidencia = service.getIncidenciasById(id);
        model.addAttribute("incidencia", incidencia);
        return "FormularioEdit";
    }

    @PostMapping("/editarIncidencias/{id}")
    public String postMethodName(@ModelAttribute incidencia incidencia, @PathVariable Long id) {

        incidencia base = service.getIncidenciasById(id);
        incidencia.setFechaApertura(base.getFechaApertura());
        incidencia.setId(id);
        service.updateIncidencia(incidencia);
        return "redirect:/incidencias";

    }

    @PostMapping("/LoginCHECK")
    public String loginCheck(
            @RequestParam String username,
            @RequestParam String password) {

        if (loginService.validarCredenciales(username, password)) {
            return "redirect:/incidencias";      // Login correcto
        } else {
            return "redirect:/incidenciasLogin"; // Login fallido
        }
    }

    @PostMapping("/estado/nuevo")
    public String postMethodName(@RequestBody String entity) {

        
        return "FormularioEstado";
    }
    

}
