import { Injectable, signal, effect } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  // On utilise un signal pour réagir instantanément aux changements
  isDark = signal<boolean>(localStorage.getItem('theme') === 'dark');

  constructor() {
    // Applique le thème au chargement
    this.applyTheme();
    
    // À chaque changement du signal, on met à jour le DOM et le localStorage
    effect(() => {
      this.applyTheme();
      localStorage.setItem('theme', this.isDark() ? 'dark' : 'light');
    });
  }

  toggleTheme() {
    this.isDark.update(current => !current);
  }

  private applyTheme() {
    if (this.isDark()) {
      document.body.classList.add('dark-mode');
      document.body.classList.remove('light-mode');
    } else {
      document.body.classList.add('light-mode');
      document.body.classList.remove('dark-mode');
    }
  }
}