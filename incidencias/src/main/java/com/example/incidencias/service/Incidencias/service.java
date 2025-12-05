package com.example.incidencias.service.Incidencias;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class service {

    @Value("${app.upload.dir:./uploads/}")
    private String uploadDir;    

    public String guardarImagen(MultipartFile archivoImagen) {
        
        try {
            // Crear directorio si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre único
            String originalFileName = archivoImagen.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String nombreArchivo = "incidencia_"
                    + System.currentTimeMillis()
                    + extension;

            // Guardar archivo
            Path rutaArchivo = uploadPath.resolve(nombreArchivo);
            Files.copy(archivoImagen.getInputStream(),
                    rutaArchivo,
                    StandardCopyOption.REPLACE_EXISTING);

            // Devolver la ruta para guardar en BD
            return "/uploads/" + nombreArchivo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
