package com.example.projetopweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve os arquivos do diretório "uploads/produtos/" na URL "/images/produtos/**"
        Path uploadPath = Paths.get("uploads/produtos").toAbsolutePath();
        String uploadDir = uploadPath.toUri().toString();

        registry.addResourceHandler("/images/produtos/**")
                .addResourceLocations(uploadDir);
    }
}
