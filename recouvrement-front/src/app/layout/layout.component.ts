import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { ThemeService } from '../services/theme/theme.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {
  authService = inject(AuthService);
  themeService = inject(ThemeService);
  router = inject(Router);

  isCaisseMenuOpen = false;

  toggleCaisseMenu() {
    this.isCaisseMenuOpen = !this.isCaisseMenuOpen;
  }

  get username(): string {
    return localStorage.getItem('username') || 'Utilisateur';
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}