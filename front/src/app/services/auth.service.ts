import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { RegisterRequest } from '../interfaces/RegisterRequest.interface';
import { LoginRequest } from '../interfaces/LoginRequest.interface';
import { SessionInformation } from '../interfaces/SessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
   public isLogged = false;
    public sessionInformation: SessionInformation | undefined;

     private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  private pathService = 'http://localhost:8080/auth';

  constructor(private httpClient: HttpClient) { }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, registerRequest, {
      withCredentials: true
    }).pipe(
      tap(() => {
        this.isLogged = true;
        this.next();  // Met à jour BehaviorSubject
      })
    );
  }
  
  public login(loginRequest: LoginRequest): Observable<{ token: string; }> {
    return this.httpClient.post<{ token: string }>(`${this.pathService}/login`, loginRequest, {
      withCredentials: true
    }).pipe(
      tap(response => {
        const token = response.token;
        if (token) {
          localStorage.setItem('token', token); // ou sessionStorage selon ton choix
          this.isLogged = true;
          this.next(); // Met à jour le BehaviorSubject
        }
      })
    );
  }
  public checkUserIsLogged(){
    const token = localStorage.getItem('token');
    if (token) {
      this.isLogged = true;
      this.next();
    } else {
      this.isLogged = false;
      this.sessionInformation = undefined;
      this.next();
    }
  }
  public logOut(): void {
    this.sessionInformation = undefined;
    localStorage.removeItem('token');
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}