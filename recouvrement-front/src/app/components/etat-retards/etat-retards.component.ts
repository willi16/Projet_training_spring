import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { Router } from '@angular/router';
import { EcheancierService } from '../../services/echeancier/echeancier.service';
import { RetardResponse } from '../../services/models/caisse.models';

@Component({
  selector: 'app-etat-retards',
  standalone: true,
  imports: [CommonModule, DatePipe, DecimalPipe],
  templateUrl: './etat-retards.component.html',
  styleUrl: './etat-retards.component.css'
})
export class EtatRetardsComponent implements OnInit {
  echeancierService = inject(EcheancierService);
  router = inject(Router);
  cdr = inject(ChangeDetectorRef);

  retards: RetardResponse[] = [];
  isLoading = true;

  // Statistiques
  totalDette = 0;
  nbApprenantsEnRetard = 0;
  nbRetardsCritiques = 0;
  nbRetardsImportants = 0;
  nbRetardsLegers = 0;

  ngOnInit() {
    this.chargerRetards();
  }

  chargerRetards() {
    this.isLoading = true;
    this.echeancierService.getRetards().subscribe({
      next: (data) => {
        this.retards = data || [];
        this.calculerStats();
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Erreur chargement retards:', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  calculerStats() {
    this.totalDette = this.retards.reduce((sum, r) => sum + r.resteAPayer, 0);
    this.nbApprenantsEnRetard = new Set(this.retards.map(r => r.apprenantTrackingId)).size;
    this.nbRetardsCritiques = this.retards.filter(r => r.joursDeRetard > 90).length;
    this.nbRetardsImportants = this.retards.filter(r => r.joursDeRetard > 30 && r.joursDeRetard <= 90).length;
    this.nbRetardsLegers = this.retards.filter(r => r.joursDeRetard <= 30).length;
  }

  getNiveauRetard(jours: number): string {
    if (jours > 90) return 'critique';
    if (jours > 30) return 'important';
    return 'leger';
  }

  allerEncaisser(r: RetardResponse) {
    this.router.navigate(['/caisse/encaisser', r.apprenantTrackingId]);
  }

  appelerParent(contact: string | null) {
    if (contact) window.open(`tel:${contact}`, '_blank');
  }
}