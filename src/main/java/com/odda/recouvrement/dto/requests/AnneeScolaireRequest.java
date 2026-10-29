package com.odda.recouvrement.dto.requests;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnneeScolaireRequest(

    @NotBlank String libelle,
    @NotNull LocalDate dateRentree,
    @NotNull LocalDate dateFin
) {}
