package com.odda.recouvrement.dto.responses;

import java.util.UUID;

import com.odda.recouvrement.enums.LienParental;

public record ParentResponse(

    UUID trackingId,
    String nom,
    String prenom,
    String contact,
    String email,
    String adresse,
    String profession,
    LienParental lien
) {}
