package com.example.incidencias.Controller;


import org.springframework.web.bind.annotation.*;
import com.example.incidencias.service.Traduccion.TraduccionService;

@RestController
@RequestMapping("/api/traducciones")
public class TraduccionController {

    private final TraduccionService service;

    public TraduccionController(TraduccionService service) {
        this.service = service;
    }

    @GetMapping("/{key}")
    public String getTraduccion(
            @PathVariable String key,
            @RequestParam(defaultValue = "es") String idioma) {
        String traduccion = service.getTraduccion(key, idioma);
        return traduccion != null ? traduccion : "Traducción no encontrada";
    }
}
