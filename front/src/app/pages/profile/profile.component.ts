import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/interfaces/RegisterRequest.interface';
import { AuthService } from 'src/app/services/auth.service';

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
              private router: Router) {
    // Déplacer l'initialisation ici, car fb est maintenant défini
    this.form = this.fb.group({
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
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.login(registerRequest).subscribe({
        next: () => this.router.navigate(['/home']),
        error: () => this.onError = true,
      }
    );
  }
}
