import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Apprenant } from '../models/caisse.models';



@Injectable({ providedIn: 'root' })
export class ApprenantService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/apprenants`;

  getAll(): Observable<Apprenant[]> {
    return this.http.get<any>(this.apiUrl).pipe(map(res => res.data || []));
  }

  getOne(trackingId: string): Observable<Apprenant> {
    return this.http.get<any>(`${this.apiUrl}/${trackingId}`).pipe(map(res => res.data));
  }

  save(apprenant: any, isEdit: boolean): Observable<any> {
    if (isEdit && apprenant.trackingId) {
      return this.http.put(`${this.apiUrl}/${apprenant.trackingId}`, apprenant);
    }
    return this.http.post(`${this.apiUrl}/inscrire`, apprenant);
  }

  delete(trackingId: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${trackingId}`);
  }
}