import { Injectable } from '@angular/core';
import { Themes } from '../interfaces/Themes.interface';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ThemesService {
  private pathService = 'http://localhost:8080';
  private themes: Themes[] = [];
  constructor(private httpClient: HttpClient) {}

  public async getThemes(): Promise<Themes[] | []> {
    try {
      // Requête PUT pour mettre à jour l'utilisateur
      const response = await this.httpClient
        .get<Themes[]>(`${this.pathService}/themes`)
        .toPromise();
      this.themes = response || [];

      return this.themes as Themes[];
    } catch (error) {
      console.error("Erreur lors de la mise à jour de l'utilisateur:", error);
      return []; // Propager l'erreur pour la gestion ultérieure}
    }
  }
  public async subscribeToTheme(id: number): Promise<void> {
    try {
      const response = await this.httpClient
        .post(`${this.pathService}/themes/subscribe/${id}`, {})
        .toPromise();
    } catch (error) {
      console.error('Erreur lors de la souscription au thème:', error);
    }
  }
  public async unsubscribeToTheme(id: number): Promise<void> {
    try {
      const response = await this.httpClient
        .delete(`${this.pathService}/themes/unsubscribe/${id}`, {})
        .toPromise();
    } catch (error) {
      console.error('Erreur lors de la désinscription au thème:', error);
    }
  }
}
