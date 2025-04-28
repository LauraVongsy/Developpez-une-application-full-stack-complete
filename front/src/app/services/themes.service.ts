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

  constructor(private httpClient: HttpClient) {}


  public getThemes(): Observable<Themes[]> {
    return this.httpClient.get<Themes[]>(`${this.pathService}/themes`);
  }

  public subscribeToTheme(id: number): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/themes/subscribe/${id}`, {});
  }

  public unsubscribeToTheme(id: number): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/themes/unsubscribe/${id}`,{});
  }

  public getAllSubscriptions(): Observable<Subscriptions[]> {
    return this.httpClient.get<Subscriptions[]>(`${this.pathService}/themes/subscriptions`);
  }
}
