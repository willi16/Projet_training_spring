import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EcheancierService } from '../../services/echeancier/echeancier.service';
import { PaiementService } from '../../services/paiement/paiement.service';
import { ApprenantService } from '../../services/apprenant/apprenant.service';
import { Apprenant, Echeancier, PaiementRequest } from '../../services/models/caisse.models';


@Component({
  selector: 'app-encaissement',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule], // ⬅️ FormsModule est crucial ici
  templateUrl: './encaissement.component.html',
  styleUrl: './encaissement.component.css'
})
export class EncaissementComponent implements OnInit {
  echeancierService = inject(EcheancierService);
  paiementService = inject(PaiementService);
  apprenantService = inject(ApprenantService);
  fb = inject(FormBuilder);
  route = inject(ActivatedRoute);
  router = inject(Router);
  cdr = inject(ChangeDetectorRef);

  apprenantTrackingId = '';
  apprenants: Apprenant[] = [];
  recherche = '';
  echeanciers: Echeancier[] = [];
  isLoading = false;

  showPaiementForm = false;
  echeanceSelectionnee: Echeancier | null = null;

  moyensPaiement = ['ESPECE', 'T_MONEY', 'FLOOZ', 'VIREMENT', 'CHEQUE', 'CARTE_BANCAIRE'];

  form: FormGroup = this.fb.group({
    montantTotal: [0, [Validators.required, Validators.min(0.01)]],
    moyen: ['', Validators.required],
    refTransaction: [''],
    datePaiement: [new Date().toISOString().split('T')[0], Validators.required]
  });

  ngOnInit() {
    // 1. Charger TOUS les apprenants pour la recherche
    this.apprenantService.getAll().subscribe({
      next: (data) => {
        this.apprenants = data || [];
        this.cdr.markForCheck();
      }
    });

    // 2. Si on arrive via un lien direct (/caisse/encaisser/xxx), on charge direct
    this.apprenantTrackingId = this.route.snapshot.paramMap.get('trackingId') || '';
    if (this.apprenantTrackingId) {
      this.loadEcheanciers();
    }
  }

  // Filtre intelligent pour la recherche
  get apprenantsFiltres(): Apprenant[] {
    if (!this.recherche.trim()) return [];
    const q = this.recherche.toLowerCase();
    return this.apprenants.filter(a =>
      `${a.nom} ${a.prenom}`.toLowerCase().includes(q) ||
      (a.matricule || '').toLowerCase().includes(q)
    ).slice(0, 8); // Limite à 8 résultats pour la propreté
  }

  selectionnerApprenant(a: Apprenant) {
    this.apprenantTrackingId = a.trackingId!;
    this.recherche = ''; // Vider la recherche
    this.loadEcheanciers(); // Charger les dettes
  }

  changerApprenant() {
    this.apprenantTrackingId = '';
    this.echeanciers = [];
    this.showPaiementForm = false;
    this.echeanceSelectionnee = null;
    this.form.reset({ datePaiement: new Date().toISOString().split('T')[0] });
  }

  loadEcheanciers() {
    this.isLoading = true;
    this.echeancierService.getByApprenant(this.apprenantTrackingId).subscribe({
      next: (data) => {
        this.echeanciers = data || [];
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  ouvrirPaiement(e: Echeancier) {
    this.echeanceSelectionnee = e;
    this.showPaiementForm = true;
    this.form.patchValue({
      montantTotal: e.resteAPayer,
      datePaiement: new Date().toISOString().split('T')[0]
    });
  }

  annulerPaiement() {
    this.showPaiementForm = false;
    this.echeanceSelectionnee = null;
    this.form.reset({ datePaiement: new Date().toISOString().split('T')[0] });
  }

  soumettrePaiement() {
    if (this.form.invalid || !this.echeanceSelectionnee) return;

    const payload: PaiementRequest = {
      ...this.form.value,
      apprenantTrackingId: this.apprenantTrackingId
    };

    this.paiementService.enregistrer(payload).subscribe({
      next: (res) => {
        alert(`✅ Paiement enregistré avec succès !\nReçu N° : ${res.numeroRecu}`);
        this.annulerPaiement();
        this.loadEcheanciers(); // Rafraîchir le tableau
      },
      error: (err) => {
        alert(`❌ Erreur: ${err.error?.description || err.error?.message || 'Échec du paiement'}`);
      }
    });
  }

  getStatutColor(statut: string): string {
    switch (statut) {
      case 'PAYE': return 'status-success';
      case 'EN_RETARD': return 'status-danger';
      case 'PARTIELLEMENT_PAYE': return 'status-warning';
      case 'EN_COURS': return 'status-info';
      default: return 'status-secondary';
    }
  }


  getStatutClass(statut: string): string {
    switch (statut) {
      case 'PAYE': return 'PAYE';
      case 'EN_RETARD': return 'EN_RETARD';
      case 'PARTIELLEMENT_PAYE': return 'PARTIELLEMENT_PAYE';
      case 'EN_COURS': return 'EN_COURS';
      case 'A_VENIR': return 'A_VENIR';
      case 'ANNULE': return 'ANNULE';
      case 'REPORTE': return 'REPORTE';
      default: return 'A_VENIR';
    }
  }
}