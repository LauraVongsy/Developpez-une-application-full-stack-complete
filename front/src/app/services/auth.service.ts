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
  
  public login(loginRequest: LoginRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/login`, loginRequest, {
      withCredentials: true
    }).pipe(
      tap(() => {
        this.isLogged = true;
        this.next();  // Met à jour BehaviorSubject
      })
    );
  }
  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}