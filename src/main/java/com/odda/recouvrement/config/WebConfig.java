package com.odda.recouvrement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Autorise toutes les routes de l'API
                .allowedOrigins("http://localhost:4200") // L'URL de votre frontend Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
                .allowedHeaders("*") // Tous les headers (y compris Authorization pour le JWT)
                .allowCredentials(true) // Autorise l'envoi de cookies/credentials si nécessaire
                .maxAge(3600); // Durée de vie du pré-vol (pre-flight) en secondes
    }
}