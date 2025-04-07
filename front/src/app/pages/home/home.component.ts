import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
    standalone: false
})

export class HomeComponent implements OnInit {


  constructor( 
     private authService: AuthService,
     private router : Router
  ) {}

  ngOnInit(): void {
    this.authService.checkUserIsLogged();
  }

  public $isLogged(): Observable<boolean> {
    return this.authService.$isLogged();
  }

  
}
