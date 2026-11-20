import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ClasseService } from '../../services/classe/classe.service';
import { NiveauService } from '../../services/niveau/niveau.service';
import { Classe, Niveau } from '../../services/models/caisse.models';


@Component({
  selector: 'app-classe-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './classe-form.component.html',
  styleUrl: './classe-form.component.css'
})
export class ClasseFormComponent implements OnInit {
  classeService = inject(ClasseService);
  niveauService = inject(NiveauService);
  fb = inject(FormBuilder);
  route = inject(ActivatedRoute);
  router = inject(Router);
  cdr = inject(ChangeDetectorRef);

  trackingId: string | null = null;
  isEditMode = false;
  isLoading = false;
  niveaux: Niveau[] = [];

  form: FormGroup = this.fb.group({
    libelle: ['', Validators.required],
    capaciteTotal: [30, [Validators.required, Validators.min(1)]],
    niveauTrackingId: ['', Validators.required] // UUID string
  });

  ngOnInit() {
    this.niveauService.getAll().subscribe({
      next: (data) => {
        console.log(' Niveaux chargés:', data);
        this.niveaux = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Erreur chargement niveaux:', err)
    });

    this.trackingId = this.route.snapshot.paramMap.get('trackingId');
    if (this.trackingId) {
      this.isEditMode = true;
      this.loadClasse(this.trackingId);
    }
  }

  loadClasse(id: string) {
    this.isLoading = true;
    this.classeService.getOne(id).subscribe({
      next: (data) => {
        this.form.patchValue({
          libelle: data.libelle,
          capaciteTotal: data.capaciteTotal,
          niveauId: data.niveauTrackingId // UUID
        });
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        alert('Classe introuvable');
        this.router.navigate(['/parametrage/classes']);
      }
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.isLoading = true;

    // CONVERSION EXPLICITE EN NOMBRE
    const payload: Classe = {
      libelle: String(this.form.value.libelle).trim(),
      capaciteTotal: Number(this.form.value.capaciteTotal),
      niveauTrackingId: String(this.form.value.niveauTrackingId) //UUID
    };

    if (this.isEditMode && this.trackingId) {
      payload.trackingId = this.trackingId;
    }

    console.log('📤 Payload envoyé:', payload);

    this.classeService.save(payload, this.isEditMode).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/parametrage/classes']);
      },
      error: (err) => {
        this.isLoading = false;
        this.cdr.markForCheck();
        console.error(' Erreur:', err.error);
        alert(`Erreur: ${err.error?.message || 'Échec'}`);
      }
    });
  }
}