import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { PaiementService } from '../../services/paiement/paiement.service';
import { PaiementResponse } from '../../services/models/caisse.models';


@Component({
  selector: 'app-journal-paiements',
  standalone: true,
  imports: [CommonModule, DatePipe, DecimalPipe],
  templateUrl: './journal-paiements.component.html',
  styleUrl: './journal-paiements.component.css'
})
export class JournalPaiementsComponent implements OnInit {
  paiementService = inject(PaiementService);
  
  paiements: PaiementResponse[] = [];
  isLoading = true;
  totalDuJour = 0;

  ngOnInit() {
    this.chargerJournal();
  }

  chargerJournal() {
    this.isLoading = true;
    this.paiementService.getAll().subscribe({
      next: (data) => {
        this.paiements = data || [];
        this.calculerTotalDuJour();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur chargement journal:', err);
        this.isLoading = false;
      }
    });
  }

  calculerTotalDuJour() {
    const aujourdHui = new Date().toISOString().split('T')[0];
    this.totalDuJour = this.paiements
      .filter(p => p.datePaiement === aujourdHui)
      .reduce((sum, p) => sum + p.montantTotal, 0);
  }

  imprimerRecu(paiement: PaiementResponse) {
    // Pour l'instant, on ouvre juste une alerte ou on prépare l'impression
    alert(`Impression du reçu N° ${paiement.numeroRecu} pour ${paiement.matriculeApprenant}`);
    // window.print(); // À implémenter avec un template d'impression plus tard
  }
}