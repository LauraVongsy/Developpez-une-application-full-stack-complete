import { Component, OnInit } from '@angular/core';
import { Subscriptions } from 'src/app/interfaces/Subscriptions.interface';
import { Themes } from 'src/app/interfaces/Themes.interface';
import { ThemesService } from 'src/app/services/themes.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
  standalone: false
})
export class ThemesComponent implements OnInit {

  themes: Themes[] = [];
  subscribedThemes: Subscriptions[] = [];
  
  constructor(private themesService: ThemesService) {}

  ngOnInit(): void {
    this.getThemes();
    this.getAllSubscriptions();
  }

  public getThemes(): void {
    this.themesService.getThemes().subscribe(
      (themes: Themes[]) => {
        this.themes = themes;
      },
      (error) => {
        console.error('Erreur lors de la récupération des thèmes:', error);
      }
    );
  }

  public subscribeToTheme(id: number): void {
    this.themesService.subscribeToTheme(id).subscribe(
      () => {
        this.getThemes();  
      },
      (error) => {
        console.error('Erreur lors de la souscription au thème:', error);
      }
    );
  }


  public unsubscribeToTheme(id: number): void {
    this.themesService.unsubscribeToTheme(id).subscribe(
      () => {
        this.getThemes();
      },
      (error) => {
        console.error('Erreur lors de la désinscription du thème:', error);
      }
    );
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
  public isSubscribed(themeId: number): boolean {
    return this.subscribedThemes.some(theme => theme.themeId === themeId);
  }
}
