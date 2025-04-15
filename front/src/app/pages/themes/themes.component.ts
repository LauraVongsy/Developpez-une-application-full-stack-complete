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

  // Utilisation d'Observable avec .subscribe() pour récupérer les thèmes
  public getThemes(): void {
    this.themesService.getThemes().subscribe(
      (themes: Themes[]) => {
        this.themes = themes;
        console.log('Thèmes récupérés:', this.themes);
      },
      (error) => {
        console.error('Erreur lors de la récupération des thèmes:', error);
      }
    );
  }

  // Souscrire à un thème
  public subscribeToTheme(id: number): void {
    this.themesService.subscribeToTheme(id).subscribe(
      () => {
        console.log('Souscription réussie au thème', id);
        this.getThemes();  // Recharger la liste des thèmes après l'abonnement
      },
      (error) => {
        console.error('Erreur lors de la souscription au thème:', error);
      }
    );
  }

  // Se désabonner d'un thème
  public unsubscribeToTheme(id: number): void {
    this.themesService.unsubscribeToTheme(id).subscribe(
      () => {
        console.log('Désabonnement réussi du thème', id);
        this.getThemes();  // Recharger la liste des thèmes après la désinscription
      },
      (error) => {
        console.error('Erreur lors de la désinscription du thème:', error);
      }
    );
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
  public isSubscribed(themeId: number): boolean {
    console.log('Vérification de l\'abonnement pour le thème ID:', themeId);
    console.log('Thèmes abonnés:', this.subscribedThemes);
    console.log('on est abonne?', this.subscribedThemes.some(theme => theme.themeId === themeId));
    return this.subscribedThemes.some(theme => theme.themeId === themeId);
  }
}
