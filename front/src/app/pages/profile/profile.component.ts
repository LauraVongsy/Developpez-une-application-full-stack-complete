import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/interfaces/RegisterRequest.interface';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
  standalone: false,
})
export class ProfileComponent {
  public onError = false;
  public form;

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router,
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
  
}

  
             
