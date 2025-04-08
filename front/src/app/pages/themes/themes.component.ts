import { Component, OnInit } from '@angular/core';
import { Themes } from 'src/app/interfaces/Themes.interface';
import { ThemesService } from 'src/app/services/themes.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrl: './themes.component.scss',
  standalone: false
})
export class ThemesComponent implements OnInit {
  
  themes: Themes[] = [];
  constructor(
    private themesService : ThemesService 
  ) {}

  ngOnInit(): void {
    this.getThemes();
  }
public getThemes (): void {
  this.themesService.getThemes()
    .then((themes: Themes[]) => {
      this.themes = themes;
      console.log("themses", this.themes); 
    })
    .catch((error:any) => {
      console.error('Erreur lors de la récupération des thèmes:', error);
    }); 
  }

  public subscribeToTheme(id: number): void {
    console.log("bien souscrit au them");
  }
}
