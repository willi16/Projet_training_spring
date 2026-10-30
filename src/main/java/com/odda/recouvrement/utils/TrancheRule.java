package com.odda.recouvrement.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Structure interne pour parser le JSON des règles de découpage.
 * Exemple de JSON attendu : 
 * {"tranches": [{"pourcentage": 40, "delaiJours": 0}, {"pourcentage": 60, "delaiJours": 30}]}
 */
public record TrancheRule(
    @JsonProperty("tranches") Tranche[] tranches
) {
    public record Tranche(
        @JsonProperty("pourcentage") int pourcentage,
        @JsonProperty("delaiJours") int delaiJours
    ) {}
}
