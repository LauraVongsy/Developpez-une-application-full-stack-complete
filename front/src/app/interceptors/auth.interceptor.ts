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
      // Cloner la requête et ajouter l'en-tête Authorization avec le token
      const clonedReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });

      // Passer la requête clonée
      return next.handle(clonedReq);
    }

    // Si aucun token, passer la requête sans modification
    console.log("pas de token");
    return next.handle(req);
  }
}
