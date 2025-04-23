import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-button-go-back',
  imports: [MatIconModule],
  templateUrl: './button-go-back.component.html',
  styleUrl: './button-go-back.component.scss',
})
export class ButtonGoBackComponent {
  constructor(
    private router: Router
  ){}

  public goBack(){
    this.router.navigate(['/'])
  }
}
