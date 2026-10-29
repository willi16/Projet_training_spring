package com.odda.recouvrement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odda.recouvrement.entities.DemandeReport;
import com.odda.recouvrement.enums.StatutDemandeDerogation;


public interface DemandeReportRepository extends JpaRepository<DemandeReport, Long>{

    List<DemandeReport> findByStatut(StatutDemandeDerogation statut);

    
} 
