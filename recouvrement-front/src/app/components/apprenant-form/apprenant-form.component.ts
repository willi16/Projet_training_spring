import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ApprenantService } from '../../services/apprenant/apprenant.service';
import { ClasseService } from '../../services/classe/classe.service';
import { Classe } from '../../services/models/caisse.models';


@Component({
  selector: 'app-apprenant-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './apprenant-form.component.html',
  styleUrl: './apprenant-form.component.css'
})
export class ApprenantFormComponent implements OnInit {
  apprenantService = inject(ApprenantService);
  classeService = inject(ClasseService);
  fb = inject(FormBuilder);
  route = inject(ActivatedRoute);
  router = inject(Router);
  cdr = inject(ChangeDetectorRef);

  trackingId: string | null = null;
  isEditMode = false;
  isLoading = false;
  classes: Classe[] = [];

  // Enum LienParental (adaptez selon vos valeurs exactes dans le backend)
  liensParentaux = ['PERE', 'MERE', 'TUTEUR', 'AUTRE'];

  // Formulaire avec FormGroup imbriqué pour le parent
  form: FormGroup = this.fb.group({
    nom: ['', Validators.required],
    prenom: ['', Validators.required],
    dateNaissance: ['', Validators.required],
    sexe: ['', Validators.required],
    classeTrackingId: ['', Validators.required],

    // Groupe imbriqué pour le parent
    parent: this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      contact: ['', [Validators.required, Validators.pattern(/^[0-9+\s()-]{8,15}$/)]],
      email: ['', [Validators.email]],
      adresse: [''],
      profession: [''],
      lien: ['', Validators.required]
    })
  });

  ngOnInit() {
    // Charger les classes pour la liste déroulante
    this.classeService.getAll().subscribe({
      next: (data) => {
        this.classes = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Erreur chargement classes:', err)
    });

    // Vérifier le mode édition
    this.trackingId = this.route.snapshot.paramMap.get('trackingId');
    if (this.trackingId) {
      this.isEditMode = true;
      this.loadApprenant(this.trackingId);
    }
  }

  loadApprenant(id: string) {
    this.isLoading = true;
    this.apprenantService.getOne(id).subscribe({
      next: (data) => {
        this.form.patchValue({
          nom: data.nom,
          prenom: data.prenom,
          dateNaissance: data.dateNaissance,
          sexe: data.sexe,
          classeTrackingId: data.classeTrackingId,
          parent: data.parent || {}
        });
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        alert('Apprenant introuvable');
        this.router.navigate(['/apprenants']);
      }
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      console.warn('Formulaire invalide:', this.form.errors);
      return;
    }

    this.isLoading = true;
    const formValue = this.form.value;

    // 1. Formater la date proprement pour LocalDate (yyyy-MM-dd)
    const dateNaissance = formValue.dateNaissance;
    const formattedDate = dateNaissance instanceof Date
      ? dateNaissance.toISOString().split('T')[0]
      : String(dateNaissance);

    // 2. Utiliser 'any' pour éviter l'erreur TypeScript sur l'ajout de trackingId
    const payload: any = {
      nom: formValue.nom,
      prenom: formValue.prenom,
      dateNaissance: formattedDate,
      sexe: formValue.sexe,
      classeTrackingId: formValue.classeTrackingId,
      // On envoie l'objet parent tel quel, il est déjà parfaitement structuré par le FormGroup
      parent: formValue.parent
    };

    // 3. Ajout propre du trackingId en mode édition (plus d'erreur rouge)
    if (this.isEditMode && this.trackingId) {
      payload.trackingId = this.trackingId;
    }

    console.log(' Payload FINAL prêt à être envoyé:', payload);

    this.apprenantService.save(payload, this.isEditMode).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/apprenants']);
      },
      error: (err) => {
        this.isLoading = false;
        this.cdr.markForCheck();
        console.error(' Erreur backend complète:', err);

        // Extraction intelligente du message d'erreur de Spring Boot
        let errorMsg = 'Échec de l opération';
        if (err.error?.errors && Array.isArray(err.error.errors)) {
          // Spring renvoie souvent un tableau "errors" avec les messages de validation
          errorMsg = err.error.errors.map((e: any) => e.defaultMessage || e.message).join('\n');
        } else if (err.error?.message) {
          errorMsg = err.error.message;
        } else if (err.error?.description) {
          errorMsg = err.error.description;
        }

        alert(`Erreur de validation :\n${errorMsg}`);
      }
    });
  }
}