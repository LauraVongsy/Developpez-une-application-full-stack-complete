import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {}

  public async updateUser(userUpdate: {
    username: string;
    email: string;
    password: string;
  }): Promise<void> {
    try {
      // Requête PUT pour mettre à jour l'utilisateur
      await this.httpClient
        .put(`${this.pathService}/user/update`, userUpdate)
        .toPromise();
    
    } catch (error) {
      console.error("Erreur lors de la mise à jour de l'utilisateur:", error);
     
    }
  }
}
