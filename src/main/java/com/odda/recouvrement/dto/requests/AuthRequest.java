package com.odda.recouvrement.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    String username,

    @NotBlank(message = "Le mot de passe est obligatoire")
    String password
) {}