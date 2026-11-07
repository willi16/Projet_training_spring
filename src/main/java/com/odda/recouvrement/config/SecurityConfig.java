package com.odda.recouvrement.config;


import com.odda.recouvrement.utils.AppConstant;

import lombok.RequiredArgsConstructor;

import com.odda.recouvrement.security.filters.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor 
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints publics
                .requestMatchers(AppConstant.API_BASE_URL + "auth/**", "/error", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                
                // Gestion du personnel
                .requestMatchers(AppConstant.API_BASE_URL + "personnels/**").hasAnyRole("ADMIN", "DIRECTEUR")
                
                // Module financier & Reporting
                .requestMatchers(AppConstant.API_BASE_URL + "paiements/**", AppConstant.API_BASE_URL + "reporting/**").hasAnyRole("ADMIN", "DIRECTEUR", "COMPTABLE")
                
                // Échéanciers
                .requestMatchers(AppConstant.API_BASE_URL + "echeanciers/apprenant/**").hasAnyRole("ADMIN", "DIRECTEUR", "COMPTABLE", "PARENT")
                
                // Tout le reste nécessite une authentification
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}