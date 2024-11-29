package com.es.jwtsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/ruta_protegida")
public class RutaProtegidaController {


    @GetMapping("/")
    public String rutaProtegida(Authentication authentication) {

        return "Hola "+authentication.getName()+" esto es una ruta protegida";
    }

    @GetMapping("/public")
    public String rutaProtegidaPublica() {

        return "Hola esto es una ruta protegida pública";
    }
}
