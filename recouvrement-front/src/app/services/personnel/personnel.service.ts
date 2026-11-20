import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Personnel } from '../models/caisse.models';



@Injectable({ providedIn: 'root' })
export class PersonnelService {
  private apiUrl = `${environment.apiUrl}/personnels`;

  constructor(private http: HttpClient) {}

  // Récupérer la liste
  getAll(): Observable<Personnel[]> {
    return this.http.get<any>(this.apiUrl).pipe(
      map(res => {
        // Cas 1 : Le backend renvoie { data: [...], statusCode: 200, ... }
        if (res && res.data) {
          return res.data;
        }
        // Cas 2 : Le backend renvoie directement le tableau [...]
        if (Array.isArray(res)) {
          return res;
        }
        // Cas 3 : Sécurité pour ne pas bloquer l'interface
        console.warn('⚠️ Format de réponse inattendu du backend :', res);
        return [];
      })
    );
  }

  // Récupérer un seul par trackingId
  getOne(trackingId: string): Observable<Personnel> {
    return this.http.get<any>(`${this.apiUrl}/${trackingId}`).pipe(
      map(res => res.data)
    );
  }

  // Créer ou Modifier
  save(personnel: Personnel, isEdit: boolean): Observable<any> {
    if (isEdit && personnel.trackingId) {
      return this.http.put(`${this.apiUrl}/${personnel.trackingId}`, personnel);
    }
    return this.http.post(this.apiUrl, personnel);
  }



  // Supprimer (Soft delete ou Hard delete selon votre backend)
  delete(trackingId: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${trackingId}`);
  }
}