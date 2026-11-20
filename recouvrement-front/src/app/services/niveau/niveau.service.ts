import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Niveau } from '../models/caisse.models';



@Injectable({ providedIn: 'root' })
export class NiveauService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/niveaux`; 

  getAll(): Observable<Niveau[]> {
    return this.http.get<any>(this.apiUrl).pipe(
      map(res => res.data || [])
    );
  }
}