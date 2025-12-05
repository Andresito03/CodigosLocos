package com.example.incidencias.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.incidencias.domain.Estado;
import com.example.incidencias.domain.Trabajador;
import com.example.incidencias.domain.Traduccion;
import com.example.incidencias.service.Incidencias.service;
import com.example.incidencias.service.Trabajadores.trabajadorService;
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
    @Autowired
    trabajadorService trabajadorService;
    @Autowired
    service serviceService;

    @Value("${app.upload.dir:./uploads/}")
    private String uploadDir;

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
    public String saveIncidencia(
            @ModelAttribute incidencia incidencia,
            @RequestParam(value = "archivoImagen", required = false) MultipartFile archivoImagen) {

        if (archivoImagen != null && !archivoImagen.isEmpty()) {
            try {
                // ===== USAR ESTA RUTA =====
                Path uploadPath = Paths.get("incidencias/uploads").toAbsolutePath();
                System.out.println(" Ruta UPLOADS: " + uploadPath.toString());

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                    System.out.println(" Carpeta uploads creada DENTRO de incidencias");
                }

                // Generar nombre único
                String originalFileName = archivoImagen.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String nombreArchivo = "incidencia_" + System.currentTimeMillis() + extension;

                // Guardar archivo
                Path rutaArchivo = uploadPath.resolve(nombreArchivo);
                Files.copy(archivoImagen.getInputStream(), rutaArchivo,
                        StandardCopyOption.REPLACE_EXISTING);

                // Guardar solo el nombre en BD
                incidencia.setImagen(nombreArchivo);

                System.out.println(" Archivo guardado en: " + rutaArchivo.toString());
                System.out.println(" Nombre en BD: " + nombreArchivo);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        List<Trabajador> trabajadores = trabajadorService.getAllTrabajador();
        model.addAttribute("trabajadores", trabajadores);
        return "trabajadores";
    }

    @GetMapping("/trabajadores/nuevo")
    public String mostrarFormularioNuevoTrabajador(Model model) {
        model.addAttribute("trabajador", new Trabajador());
        return "nuevoTrabajador";
    }

    @PostMapping("/trabajadores/guardar")
    public String guardarTrabajador(@ModelAttribute Trabajador trabajador) {
        trabajadorService.saveTrabajador(trabajador);
        return "redirect:/trabajadoresGestion";
    }

    @GetMapping("/trabajadores/eliminar/{id}")
    public String EliminarTrabajador(@PathVariable("id") Long id) {
        trabajadorService.deleteTrabajadorById(id);
        return "redirect:/trabajadoresGestion";
    }

    @GetMapping("/debug")
    @ResponseBody
    public String debug() {
        String info = "<h3>Debug Info:</h3>";
        info += "<p>user.dir: " + System.getProperty("user.dir") + "</p>";
        info += "<p>Path uploads: " + Paths.get("uploads").toAbsolutePath() + "</p>";
        info += "<p>Path incidencias/uploads: " + Paths.get("incidencias/uploads").toAbsolutePath() + "</p>";

        // Verifica si estás ejecutando desde IDE o terminal
        File currentDir = new File(".");
        info += "<p>Current dir: " + currentDir.getAbsolutePath() + "</p>";

        return info;
    }
}
