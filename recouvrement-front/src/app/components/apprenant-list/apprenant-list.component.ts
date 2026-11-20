import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApprenantService } from '../../services/apprenant/apprenant.service';
import { Apprenant } from '../../services/models/caisse.models';


@Component({
  selector: 'app-apprenant-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './apprenant-list.component.html',
  styleUrl: './apprenant-list.component.css'
})
export class ApprenantListComponent implements OnInit {
  apprenantService = inject(ApprenantService);
  cdr = inject(ChangeDetectorRef);

  apprenants: Apprenant[] = [];
  isLoading = true;
  errorMessage = '';

  ngOnInit(): void {
    this.loadApprenants();
  }

  loadApprenants(): void {
    this.isLoading = true;
    this.apprenantService.getAll().subscribe({
      next: (data) => {
        this.apprenants = data || [];
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.errorMessage = `Erreur: ${err.status}`;
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  deleteApprenant(trackingId: string): void {
    if (confirm('Supprimer cet apprenant ?')) {
      this.apprenantService.delete(trackingId).subscribe({
        next: () => this.loadApprenants(),
        error: (err) => alert('Erreur: ' + err.message)
      });
    }
  }
}