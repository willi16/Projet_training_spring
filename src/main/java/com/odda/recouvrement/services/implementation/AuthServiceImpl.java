package com.odda.recouvrement.services.implementation;

import com.odda.recouvrement.dto.requests.AuthRequest;
import com.odda.recouvrement.dto.responses.AuthResponse;
import com.odda.recouvrement.entities.Personnel;
import com.odda.recouvrement.repositories.PersonnelRepository;
import com.odda.recouvrement.security.filters.JwtService;
import com.odda.recouvrement.services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PersonnelRepository personnelRepository;

    @Override 
    @Transactional 
    public AuthResponse login(AuthRequest authReq) {
        // 1. Authentification Spring Security (vérifie automatiquement le mot de passe haché BCrypt)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authReq.username(), authReq.password())
        );

        // 2. Récupérer l'utilisateur pour extraire son rôle et son username exact
        Personnel personnel = personnelRepository.findByUsernameOrEmail(authReq.username(), authReq.username())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable après authentification"));

        // 3. Générer le token JWT en y incluant le rôle comme claim supplémentaire
        String token = jwtService.generateToken(
                personnel.getUsername(), 
                Map.of("role", personnel.getRole().name())
        );

        return new AuthResponse(token, personnel.getUsername(), personnel.getRole());
    }
}