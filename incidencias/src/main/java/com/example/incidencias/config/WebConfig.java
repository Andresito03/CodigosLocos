package com.example.incidencias.config;  // O el paquete que uses

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ===== USAR LA MISMA RUTA =====
        String rutaUploads = Paths.get("incidencias/uploads").toAbsolutePath().toString();
        
        System.out.println("🌐 Configurando recursos en: " + rutaUploads);
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + rutaUploads + "/");

    }
}