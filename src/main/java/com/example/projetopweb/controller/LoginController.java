package com.example.projetopweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String login() {
        return "login/login"; // Retorna a view de login (src/main/resources/templates/login/login.html)
    }

    // Importante: Esta classe dialoga com o Spring MVC para renderizar a pagina de login personalizada. Quem estabelece a pagina de login personalizada é a classe SecurityConfiguration, onde definimos o caminho da pagina de login com .loginPage("/login").
}
