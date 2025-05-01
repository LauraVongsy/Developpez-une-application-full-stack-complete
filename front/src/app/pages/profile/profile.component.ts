import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscriptions } from 'src/app/interfaces/Subscriptions.interface';
import { ThemesService } from 'src/app/services/themes.service';
import { UserService } from 'src/app/services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

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
              private userService: UserService,
              private snackBar: MatSnackBar,
              private authService : AuthService
            )
            {


   
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

  public $isLogged(): Observable<boolean> {
    return this.authService.$isLogged();
  }

  public getAllSubscriptions(): void {
    this.themesService.getAllSubscriptions().subscribe(
      (subscriptions: Subscriptions[]) => {
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
    .then(() => {
      this.snackBar.open('Profil mis à jour avec succès', 'Fermer', {
        duration: 3000
      });
    })
      .catch(() => {
        this.onError = true;
        this.snackBar.open('Erreur lors de la mise à jour du profil', 'Fermer', {
          duration: 3000
        });
      });
  }

  public unsubscribeToTheme(id: number): void {
    this.themesService.unsubscribeToTheme(id).subscribe(
      () => {
        this.getAllSubscriptions(); 
      },
      (error) => {
        console.error('Erreur lors de la désinscription au thème:', error);
      }
    );
  }
  
}

  
             
