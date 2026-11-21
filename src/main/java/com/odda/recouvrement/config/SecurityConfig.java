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
                        .requestMatchers(AppConstant.API_BASE_URL + "auth/**", "/error", "/swagger-ui/**",
                                "/v3/api-docs/**", "/swagger-ui.html")
                        .permitAll()

                        // Gestion du personnel
                        .requestMatchers(AppConstant.API_BASE_URL + "personnels/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN", "DIRECTEUR", "ROLE_DIRECTEUR")

                        // Module financier & Reporting
                        // .requestMatchers(AppConstant.API_BASE_URL + "paiements/**",
                        //         AppConstant.API_BASE_URL + "reporting/**")
                        // .hasAnyAuthority("ADMIN", "ROLE_ADMIN", "DIRECTEUR", "ROLE_DIRECTEUR", 
                        //                  "COMPTABLE", "ROLE_COMPTABLE", "CAISSIER", "ROLE_CAISSIER")

                        .requestMatchers(AppConstant.API_BASE_URL + "paiements/**").authenticated() // Juste être connecté suffit

                        // Échéanciers
                        .requestMatchers(AppConstant.API_BASE_URL + "echeanciers/apprenant/**")
                        .hasAnyAuthority("ADMIN", "ROLE_ADMIN", "DIRECTEUR", "ROLE_DIRECTEUR", 
                                         "COMPTABLE", "ROLE_COMPTABLE", "PARENT", "ROLE_PARENT", "CAISSIER", "ROLE_CAISSIER")


                        // Autoriser l'accès en lecture aux niveaux pour remplir les listes déroulantes
                        .requestMatchers(AppConstant.API_BASE_URL + "niveaux/**").permitAll()

                        .requestMatchers(AppConstant.API_BASE_URL + "classes/**").permitAll()

                        .requestMatchers(AppConstant.API_BASE_URL + "apprenants/**").authenticated() 


                        // Tout le reste nécessite une authentification
                        .anyRequest().authenticated())
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