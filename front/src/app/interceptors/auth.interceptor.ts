import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Récupérer le token du localStorage
    const token = localStorage.getItem('token');
    console.log("token", token);
    console.log("req", req);
    if (token) {
      // Cloner la requête pour ajouter le header Authorization
      req.headers.append('Authorization', `Bearer ${token}`);
      
      
    }
console.log("pas de token");
    // Si aucun token, passer la requête sans modification
    return next.handle(req);
  }
}
