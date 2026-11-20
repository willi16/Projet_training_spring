import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Classe } from '../models/caisse.models';



@Injectable({ providedIn: 'root' })
export class ClasseService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/classes`;

  getAll(): Observable<Classe[]> {
    return this.http.get<any>(this.apiUrl).pipe(map(res => res.data || []));
  }

  getOne(trackingId: string): Observable<Classe> {
    return this.http.get<any>(`${this.apiUrl}/${trackingId}`).pipe(map(res => res.data));
  }

  save(classe: Classe, isEdit: boolean): Observable<any> {
    if (isEdit && classe.trackingId) {
      return this.http.put(`${this.apiUrl}/${classe.trackingId}`, classe);
    }
    return this.http.post(this.apiUrl, classe);
  }

  delete(trackingId: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${trackingId}`);
  }
}