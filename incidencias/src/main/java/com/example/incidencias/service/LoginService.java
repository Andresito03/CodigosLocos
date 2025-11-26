package com.example.incidencias.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean validarCredenciales(String usuario, String contrasena) {

        String usuarioValido = "admin";
        String contrasenaValida = "admin";

        return usuario.equals(usuarioValido) && contrasena.equals(contrasenaValida);
    }
}
