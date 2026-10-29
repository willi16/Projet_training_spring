package com.odda.recouvrement.dto.requests;

import com.odda.recouvrement.enums.LienParental;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParentRequest (
    @NotBlank(message = "Le nom du parent est obligatoire") 
    String nom,

    @NotBlank(message = "Le prénom du parent est obligatoire") 
    String prenom,

    @NotBlank(message = "Le contact téléphonique est obligatoire") 
    String contact,

    @Email (message = "Le mail n'est pas valide")
    String email,

    String adresse,

    String profession,
    
    @NotNull (message = "Le lien de parenté est obligatoire") 
    LienParental lien
){}
