import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

   constructor( 
       private authService: AuthService,
       private router : Router
    ) {}
  public logout(): void {
    this.authService.logOut();
    this.router.navigate([''])
  }

  public openProfile(): void {
    this.router.navigate(['/profile']);
  }
  
  public openThemes(): void {
    this.router.navigate(['/themes']);
  }
}
