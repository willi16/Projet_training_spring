package com.odda.recouvrement.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;


public interface ReportingService {

    
    public BigDecimal getEncaissementDuJour(LocalDate date); 

    
    public double getTauxRecouvrementGlobal();

    
    public Map<String, BigDecimal> getImpayesParClasse() ;
}