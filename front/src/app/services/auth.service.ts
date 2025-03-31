import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../interfaces/RegisterRequest.interface';
import { LoginRequest } from '../interfaces/LoginRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'http://localhost:8080/auth';

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, registerRequest, {
      withCredentials: true
    });
  }

  public login(loginRequest: LoginRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/login`, loginRequest, {
      withCredentials: true
    });
  }
}