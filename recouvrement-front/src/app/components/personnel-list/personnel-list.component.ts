import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PersonnelService } from '../../services/personnel/personnel.service';
import { Personnel } from '../../services/models/caisse.models';

@Component({
  selector: 'app-personnel-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './personnel-list.component.html',
  styleUrl: './personnel-list.component.css'
})
export class PersonnelListComponent implements OnInit {
  personnelService = inject(PersonnelService);
  cdr = inject(ChangeDetectorRef); // Outil pour forcer le rafraîchissement

  personnels: Personnel[] = [];
  isLoading = true;
  errorMessage = '';

  ngOnInit(): void {
    this.loadPersonnels();
  }

  loadPersonnels(): void {
    this.isLoading = true;
    this.errorMessage = '';
    console.log('Début du chargement...');

    this.personnelService.getAll().subscribe({
      next: (data) => {
        console.log('Données reçues :', data);
        this.personnels = data || [];
        this.isLoading = false;
        this.cdr.markForCheck(); // FORCE L'AFFICHAGE
      },
      error: (err) => {
        console.error('Erreur réseau ou backend :', err);
        this.errorMessage = `Erreur: ${err.status} - ${err.error?.message || 'Problème de connexion'}`;
        this.isLoading = false;
        this.cdr.markForCheck(); // FORCE L'AFFICHAGE DE L'ERREUR
      }
    });
  }

  deletePersonnel(id: string): void {
    if (confirm('Supprimer ce personnel ?')) {
      this.personnelService.delete(id).subscribe({
        next: () => this.loadPersonnels(),
        error: (err) => alert('Erreur suppression: ' + err.message)
      });
    }
  }
}