import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/interfaces/RegisterRequest.interface';
import { Subscriptions } from 'src/app/interfaces/Subscriptions.interface';
import { AuthService } from 'src/app/services/auth.service';
import { ThemesService } from 'src/app/services/themes.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
  standalone: false,
})
export class ProfileComponent implements OnInit {
  public onError = false;
  public form;
  subscribedThemes: Subscriptions[] = [];


  constructor(
              private fb: FormBuilder,
              private themesService: ThemesService,
              private userService: UserService) 
            {


    // Déplacer l'initialisation ici, car fb est maintenant défini
    this.form = this.fb.group({
      username: [
        '',
        [
          Validators.required,
        ]
      ],
      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(40)
        ]
      ]
    });
  }
  ngOnInit(): void {
 
    this.getAllSubscriptions();
  }

// Récupérer les abonnements de l'utilisateur
  public getAllSubscriptions(): void {
    this.themesService.getAllSubscriptions().subscribe(
      (subscriptions: Subscriptions[]) => {
        console.log('Abonnements récupérés:', subscriptions);
        this.subscribedThemes = subscriptions;
      },
      (error) => {
        console.error('Erreur lors de la récupération des abonnements:', error);
      }
    );
  }

  public submit(): void {
    const userUpdateData = {
      username: this.form.value.username || '',  
      email: this.form.value.email || '',        
      password: this.form.value.password || ''   
    };
  
    this.userService.updateUser(userUpdateData)
      .catch(() => {
        this.onError = true;
      });
      console.log(userUpdateData);
  }

  public unsubscribeToTheme(id: number): void {
    this.themesService.unsubscribeToTheme(id).subscribe(
      () => {
        console.log('Désinscription réussie', id);
        this.getAllSubscriptions();  // Recharger la liste des thèmes après l'abonnement
      },
      (error) => {
        console.error('Erreur lors de la désinscription au thème:', error);
      }
    );
  }
  
}

  
             
