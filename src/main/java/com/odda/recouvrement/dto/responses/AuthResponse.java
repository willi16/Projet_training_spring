package com.odda.recouvrement.dto.responses;

import com.odda.recouvrement.enums.RoleUtilisateur;

public record AuthResponse(
    String token,
    String username,
    RoleUtilisateur role
) {}