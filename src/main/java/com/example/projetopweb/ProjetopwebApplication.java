package com.example.projetopweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ProjetopwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetopwebApplication.class, args);
	}

}

@Controller
class HomeController {
    @GetMapping("/")
    public String home() {
        return "redirect:/vendas";
    }
}
