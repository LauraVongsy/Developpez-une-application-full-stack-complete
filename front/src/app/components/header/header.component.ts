import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  imports: [CommonModule, MatIconModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
menuOpen =false;

   constructor( 
       private authService: AuthService,
       private router : Router
    ) {}

   public isOnHome(): boolean {
      return this.router.url === '/home';
    }
   public isOnThemes(): boolean {
      return this.router.url === '/themes';
    }
   public isOnProfile(): boolean {
      return this.router.url === '/profile';
    }
   public toggleMenu(): void {
      this.menuOpen = !this.menuOpen;
    }

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
  public openHome(): void {
    this.router.navigate(['/home']);
  }

}
