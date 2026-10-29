package com.odda.recouvrement.dto.requests;

import java.math.BigDecimal;

import com.odda.recouvrement.enums.TypeFraisCode;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TypeFraisRequest(

    @NotBlank(message = "Le code du frais est obligatoire") 
    String code,

    @NotBlank(message = "Le libellé est obligatoire") 
    String libelle,

    @NotNull 
    @DecimalMin("0.01") 
    BigDecimal montantReference,

    @NotNull 
    TypeFraisCode modeDecoupage, 

    String reglesDecoupage, 

    String cible,

    Long classeId 

){}
