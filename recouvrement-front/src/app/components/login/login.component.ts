import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from '../../services/models/caisse.models';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  credentials: LoginRequest = { username: '', password: '' };
  isLoading = false;
  errorMessage = '';

  onSubmit(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        console.log('✅ Connexion réussie ! Rôle :', response.data.role);
        localStorage.setItem('token', response.data.token);          // ⬅️ ne l'oubliez pas !
        localStorage.setItem('username', response.data.username);
        localStorage.setItem('role', response.data.role);
        this.isLoading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.isLoading = false;
        // ⬇️ Message adapté selon le VRAI problème
        if (err.status === 0) {
          this.errorMessage = '⚠️ Impossible de contacter le serveur. Vérifiez que le backend est démarré.';
        } else if (err.status === 401 || err.status === 400) {
          this.errorMessage = 'Identifiants incorrects. Veuillez réessayer.';
        } else {
          this.errorMessage = `Erreur serveur (${err.status}). Vérifiez la console backend.`;
        }
        console.error('❌ Erreur de connexion', err);
      }
    });
  }
}