import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { LayoutComponent } from './layout/layout.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', loadComponent: () => import('./components/dashboard/dashboard.component').then(m => m.DashboardComponent) },
      { path: 'personnels', loadComponent: () => import('./components/personnel-list/personnel-list.component').then(m => m.PersonnelListComponent) },

      { path: 'personnels/form', loadComponent: () => import('./components/personnel-form/personnel-form.component').then(m => m.PersonnelFormComponent) },
      { path: 'personnels/form/:trackingId', loadComponent: () => import('./components/personnel-form/personnel-form.component').then(m => m.PersonnelFormComponent) },

      // Nous ajouterons les autres routes ici au fur et à mesure

      // ... routes existantes ...
      { path: 'parametrage/classes', loadComponent: () => import('./components/classe-list/classe-list.component').then(m => m.ClasseListComponent) },
      { path: 'parametrage/classes/form', loadComponent: () => import('./components/classe-form/classe-form.component').then(m => m.ClasseFormComponent) },
      { path: 'parametrage/classes/form/:id', loadComponent: () => import('./components/classe-form/classe-form.component').then(m => m.ClasseFormComponent) },

      { path: 'apprenants', loadComponent: () => import('./components/apprenant-list/apprenant-list.component').then(m => m.ApprenantListComponent) },
      { path: 'apprenants/form', loadComponent: () => import('./components/apprenant-form/apprenant-form.component').then(m => m.ApprenantFormComponent) },
      { path: 'apprenants/form/:trackingId', loadComponent: () => import('./components/apprenant-form/apprenant-form.component').then(m => m.ApprenantFormComponent) },

      {
        path: 'caisse/encaisser/:trackingId',
        loadComponent: () => import('./components/encaissement/encaissement.component').then(m => m.EncaissementComponent)
      },

      {
        path: 'caisse',
        children: [
          {
            path: '',
            redirectTo: 'encaisser',
            pathMatch: 'full'
          },
          {
            path: 'encaisser',
            loadComponent: () => import('./components/encaissement/encaissement.component').then(m => m.EncaissementComponent)
          },
          {
            path: 'encaisser/:trackingId',
            loadComponent: () => import('./components/encaissement/encaissement.component').then(m => m.EncaissementComponent)
          },
          {
            path: 'journal',
            loadComponent: () => import('./components/journal-paiements/journal-paiements.component').then(m => m.JournalPaiementsComponent)
          },
          {
            path: 'retards',
            loadComponent: () => import('./components/etat-retards/etat-retards.component').then(m => m.EtatRetardsComponent)
          }
        ]
      }

    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];