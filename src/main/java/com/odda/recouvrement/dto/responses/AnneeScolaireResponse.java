package com.odda.recouvrement.dto.responses;

import java.time.LocalDate;
import java.util.UUID;

public record AnneeScolaireResponse(

    UUID trackingId, 
    String libelle,
    LocalDate dateRentree,
    LocalDate dateFin, 
    boolean cloturee
){}
