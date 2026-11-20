import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
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

  getAll(): Observable<PaiementResponse[]> {
    // Adaptez l'URL si votre backend utilise un autre chemin (ex: /historique)
    return this.http.get<any>(this.apiUrl).pipe(
      map(res => res.data || [])
    );
  }
}