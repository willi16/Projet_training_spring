import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Echeancier } from '../models/caisse.models';


@Injectable({ providedIn: 'root' })
export class EcheancierService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/echeanciers`;

  // Récupérer les échéances d'un apprenant spécifique
  getByApprenant(apprenantTrackingId: string): Observable<Echeancier[]> {
    return this.http.get<any>(`${this.apiUrl}/apprenant/${apprenantTrackingId}`).pipe(
      map(res => res.data || [])
    );
  }
}