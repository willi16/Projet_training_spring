import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PersonnelService } from '../../services/personnel/personnel.service';
import { Personnel } from '../../services/models/caisse.models';

@Component({
  selector: 'app-personnel-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './personnel-form.component.html',
  styleUrl: './personnel-form.component.css'
})
export class PersonnelFormComponent implements OnInit {
  personnelService = inject(PersonnelService);
  fb = inject(FormBuilder);
  route = inject(ActivatedRoute);
  router = inject(Router);
  cdr = inject(ChangeDetectorRef); // Outil pour forcer le rafraîchissement

  trackingId: string | null = null;
  isEditMode = false;
  isLoading = false;
  error = '';

  form: FormGroup = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: [''],
    nom: ['', Validators.required],
    prenom: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    matricule: ['', Validators.required],
    role: ['COMPTABLE', Validators.required],
    actif: [true]
  });

  ngOnInit() {
    this.trackingId = this.route.snapshot.paramMap.get('trackingId');
    if (this.trackingId) {
      this.isEditMode = true;
      this.loadPersonnel(this.trackingId);
    }
  }

  loadPersonnel(id: string) {
    this.isLoading = true;
    this.personnelService.getOne(id).subscribe({
      next: (data) => {
        this.form.patchValue(data);
        this.form.get('password')?.clearValidators();
        this.form.get('password')?.updateValueAndValidity();
        this.isLoading = false;
        this.cdr.markForCheck(); // FORCE L'AFFICHAGE
      },
      error: () => {
        this.error = 'Impossible de charger les données.';
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.cdr.markForCheck(); // Affiche "Enregistrement..." immédiatement

    const payload: Personnel = {
      ...this.form.value,
      trackingId: this.isEditMode ? this.trackingId! : undefined
    };

    if (this.isEditMode && (!payload.password || payload.password.trim() === '')) {
      delete payload.password;
    } else if (!this.isEditMode && !payload.password) {
      this.isLoading = false;
      this.cdr.markForCheck();
      alert('Mot de passe obligatoire.');
      return;
    }

    console.log('Envoi payload:', payload);

    // APPEL EXACT À VOTRE MÉTHODE SAVE
    this.personnelService.save(payload, this.isEditMode).subscribe({
      next: (res) => {
        console.log('Succès backend');
        this.isLoading = false;
        this.cdr.markForCheck();
        this.router.navigate(['/personnels']);
      },
      error: (err) => {
        console.error('Erreur backend:', err);
        this.isLoading = false;
        this.cdr.markForCheck(); // FORCE L'ARRÊT DU CHARGEMENT
        alert(`Erreur: ${err.error?.message || 'Échec de l opération'}`);
      }
    });
  }
}