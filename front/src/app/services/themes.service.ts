import { Injectable } from '@angular/core';
import { Themes } from '../interfaces/Themes.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subscriptions } from '../interfaces/Subscriptions.interface';

@Injectable({
  providedIn: 'root',
})
export class ThemesService {
  private pathService = 'http://localhost:8080';
  private themes: Themes[] = [];

  constructor(private httpClient: HttpClient) {}

  // Utilisation d'Observable pour obtenir les thèmes
  public getThemes(): Observable<Themes[]> {
    return this.httpClient.get<Themes[]>(`${this.pathService}/themes`);
  }

  // Utilisation d'Observable pour souscrire à un thème
  public subscribeToTheme(id: number): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/themes/subscribe/${id}`, {});
  }

  // Utilisation d'Observable pour se désabonner d'un thème
  public unsubscribeToTheme(id: number): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/themes/unsubscribe/${id}`,{});
  }

  // Récupérer les abonnements de l'utilisateur, déjà bien converti en Observable
  public getAllSubscriptions(): Observable<Subscriptions[]> {
    return this.httpClient.get<Subscriptions[]>(`${this.pathService}/themes/subscriptions`);
  }
}
