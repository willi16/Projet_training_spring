import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, of, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PaiementRequest, PaiementResponse } from '../models/caisse.models';

@Injectable({ providedIn: 'root' })
export class PaiementService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/paiements`;

  enregistrer(paiement: PaiementRequest): Observable<PaiementResponse> {
    return this.http.post<any>(`${this.apiUrl}/enregistrer`, paiement).pipe(
      map(res => res.data)
    );
  }

  // getAll(): Observable<PaiementResponse[]> {
  //   // Adaptez l'URL si votre backend utilise un autre chemin (ex: /historique)
  //   return this.http.get<any>(this.apiUrl).pipe(
  //     map(res => res.data || [])
  //   );
  // }

  getAll(): Observable<PaiementResponse[]> {
    console.log('🔍 Tentative de récupération des paiements sur l URL :', this.apiUrl);

    return this.http.get<any>(this.apiUrl).pipe(
      tap(res => console.log('✅ Réponse BRUTE du backend :', res)), // <-- Affiche tout ce que le backend envoie
      map(res => {
        const data = res?.data || [];
        console.log('📦 Données extraites pour le tableau :', data);
        return data;
      }),
      catchError(err => {
        console.error('❌ ERREUR RÉSEAU ou BACKEND (404, 500, etc.) :', err);
        return of([]); // Retourne un tableau vide en cas d'erreur pour ne pas faire planter la page
      })
    );
  }
}