import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PaiementService } from '../../services/paiement/paiement.service';
import { PaiementResponse } from '../../services/models/caisse.models';

@Component({
  selector: 'app-journal-paiements',
  standalone: true,
  imports: [CommonModule, DatePipe, DecimalPipe, FormsModule],
  templateUrl: './journal-paiements.component.html',
  styleUrl: './journal-paiements.component.css'
})
export class JournalPaiementsComponent implements OnInit {
  paiementService = inject(PaiementService);
  cdr = inject(ChangeDetectorRef);

  paiements: PaiementResponse[] = [];
  paiementsFiltres: PaiementResponse[] = [];
  isLoading = true;
  totalDuJour = 0;
  totalGeneral = 0;
  nombreTransactions = 0;

  // Filtres
  filtreDate = '';
  filtreMoyen = '';
  filtreMatricule = '';

  moyensPaiement = ['ESPECE', 'T_MONEY', 'FLOOZ', 'VIREMENT', 'CHEQUE', 'CARTE_BANCAIRE'];

  ngOnInit() {
    this.chargerJournal();
  }

  chargerJournal() {
    this.isLoading = true;
    this.paiementService.getAll().subscribe({
      next: (data) => {
        this.paiements = data || [];
        this.paiementsFiltres = this.paiements;
        this.calculerStatistiques();
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Erreur chargement journal:', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  calculerStatistiques() {
    const aujourdHui = new Date().toISOString().split('T')[0];
    
    this.totalDuJour = this.paiements
      .filter(p => p.datePaiement === aujourdHui)
      .reduce((sum, p) => sum + p.montantTotal, 0);

    this.totalGeneral = this.paiements.reduce((sum, p) => sum + p.montantTotal, 0);
    this.nombreTransactions = this.paiements.length;
  }

  appliquerFiltres() {
    this.paiementsFiltres = this.paiements.filter(p => {
      const matchDate = !this.filtreDate || p.datePaiement === this.filtreDate;
      const matchMoyen = !this.filtreMoyen || p.moyen === this.filtreMoyen;
      const matchMatricule = !this.filtreMatricule || 
        p.matriculeApprenant.toLowerCase().includes(this.filtreMatricule.toLowerCase());
      
      return matchDate && matchMoyen && matchMatricule;
    });
  }

  resetFiltres() {
    this.filtreDate = '';
    this.filtreMoyen = '';
    this.filtreMatricule = '';
    this.paiementsFiltres = this.paiements;
  }

  getMoyenClass(moyen: string): string {
    return 'moyen-' + moyen.toLowerCase();
  }

  getStatutClass(statut: string): string {
    return 'statut-' + statut.toLowerCase();
  }

  voirRecu(paiement: PaiementResponse) {
    alert(`📄 Reçu N° ${paiement.numeroRecu}\n\nApprenant: ${paiement.matriculeApprenant}\nMontant: ${paiement.montantTotal} FCFA\nDate: ${paiement.datePaiement}\nMoyen: ${paiement.moyen}`);
    // TODO: Implémenter l'impression du reçu avec un template dédié
  }
}