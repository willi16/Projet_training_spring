package com.odda.recouvrement.services.implementation;

import com.odda.recouvrement.repositories.EcheancierRepository;
import com.odda.recouvrement.repositories.PaiementRepository;
import com.odda.recouvrement.services.ReportingService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final PaiementRepository paiementRepository;
    private final EcheancierRepository echeancierRepository;
    

    @Override 
    @Transactional(readOnly = true)
    public BigDecimal getEncaissementDuJour(LocalDate date) {
        BigDecimal total = paiementRepository.sumMontantByDate(date);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override 
    @Transactional(readOnly = true)
    public double getTauxRecouvrementGlobal() {
        BigDecimal paye = echeancierRepository.sumMontantPaye();
        BigDecimal total = echeancierRepository.sumMontantTotalPrevu();
        
        if (total == null || total.compareTo(BigDecimal.ZERO) == 0) return 0.0;
        
        return paye.divide(total, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100;
    }

    @Override 
    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getImpayesParClasse() {
        return echeancierRepository.sumImpayesByClasse();
    }
}