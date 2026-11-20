import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ClasseService } from '../../services/classe/classe.service';
import { Classe } from '../../services/models/caisse.models';


@Component({
  selector: 'app-classe-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './classe-list.component.html',
  styleUrl: './classe-list.component.css'
})
export class ClasseListComponent implements OnInit {
  classeService = inject(ClasseService);
  cdr = inject(ChangeDetectorRef);

  classes: Classe[] = [];
  isLoading = true;
  errorMessage = '';

  ngOnInit(): void {
    this.loadClasses();
  }

  loadClasses(): void {
    this.isLoading = true;
    this.classeService.getAll().subscribe({
      next: (data) => {
        this.classes = data || [];
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

  deleteClasse(trackingId: string): void {
    if (confirm('Supprimer cette classe ?')) {
      this.classeService.delete(trackingId).subscribe({
        next: () => this.loadClasses(),
        error: (err) => alert('Erreur: ' + err.message)
      });
    }
  }
}