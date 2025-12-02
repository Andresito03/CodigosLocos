package com.example.incidencias.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.incidencias.domain.incidencia;
import com.example.incidencias.service.Estado.EstadoService;
import com.example.incidencias.service.Incidencias.incidenciasService;
import com.example.incidencias.service.LoginService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.incidencias.domain.Estado;
import com.example.incidencias.domain.Traduccion;
import com.example.incidencias.service.Traduccion.TraduccionService;

@Controller
public class incidenciasController {

    @Autowired
    incidenciasService service;
    @Autowired
    LoginService loginService;
    @Autowired
    EstadoService serviceEstado;
    @Autowired
    TraduccionService traduccionService;

    public int idioma = 1;

    @GetMapping("/cambiarIdioma")
    public String cambiarIdioma(@RequestParam int idioma) {
        this.idioma = idioma;  // cambia la variable del controller
        return "redirect:/incidenciasLogin"; // redirige al login para recargar traducciones
    }

    @GetMapping("/cambiarIdiomaIncidencias")
    public String cambiarIdiomaIncidencias(@RequestParam int idioma) {
        this.idioma = idioma;  // cambia la variable del controller
        return "redirect:/incidencias"; // redirige al login para recargar traducciones
    }

    @GetMapping("/incidenciasLogin")
    public String Login(Model model) {
        Map<String, String> traducciones = traduccionService.getTraduccionesPorIdioma(idioma);

        model.addAttribute("t", traducciones);
        return "login";
    }

    @GetMapping("/incidencias")
    public String Incidencias(Model model) {
        List<incidencia> incidencias = service.getAllIncidencias();
        Map<String, String> traducciones = traduccionService.getTraduccionesPorIdioma(idioma);
        model.addAttribute("t", traducciones);
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
        List<Estado> Estados = serviceEstado.getAllEstado();
        model.addAttribute("estados", Estados);
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

    @GetMapping("/estadoGestion")
    public String postMethodName(Model model) {
        List<Estado> Estados = serviceEstado.getAllEstado();
        model.addAttribute("estados", Estados);
        model.addAttribute("nuevoEstado", new Estado());
        return "Estados";
    }

    @GetMapping("Volver")
    public String getMethodName() {
        return "redirect:/incidencias";
    }

    @PostMapping("/estados/nuevo")
    public String getMethodName(@ModelAttribute("nuevoEstado") Estado nuevoEstado) {
        serviceEstado.saveEstado(nuevoEstado);
        return "redirect:/incidencias";
    }

    // Editar estado (GET y POST)
    @GetMapping("/estados/editar/{id}")
    public String editarEstadoForm(@PathVariable("id") Long id, Model model) {
        Estado estado = serviceEstado.getEstadoById(id);
        model.addAttribute("estado", estado);
        return "FormularioEditarEstado"; // template Thymeleaf para editar
    }

    @PostMapping("/estados/editar/{id}")
    public String editarEstado(@PathVariable("id") Long id, @ModelAttribute("estado") Estado estado) {
        estado.setId(id); // asegurarse de actualizar el ID correcto
        serviceEstado.saveEstado(estado);
        return "redirect:/estadoGestion";
    }

// Eliminar estado
    @GetMapping("/estados/eliminar/{id}")
    public String eliminarEstado(@PathVariable("id") Long id) {
        serviceEstado.deleteEstadoById(id);
        return "redirect:/estadoGestion";
    }

    @GetMapping("/trabajadoresGestion")
    public String trabajadoresGestion(Model model) {

        return "trabajadores";
    }

}
