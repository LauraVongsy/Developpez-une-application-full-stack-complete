import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Article } from 'src/app/interfaces/Article.interface';
import { ArticleService } from 'src/app/services/article.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
    standalone: false
})

export class HomeComponent implements OnInit {

articles: Article[] = [];
onError: boolean = false;
sortDescending: boolean = true; 
sortedArticles: Article[] = [];


constructor( 
     private authService: AuthService,
     private router : Router,
     private articlesService: ArticleService
  ) {}

  ngOnInit(): void {
    this.authService.checkUserIsLogged();
    this.getAllArticles();
  }

  public $isLogged(): Observable<boolean> {
    return this.authService.$isLogged();
  }

  public getAllArticles(): void {
    this.articlesService.getAllArticles().subscribe(
      (articles) => {
        console.log('Articles récupérés:', articles);
        this.articles = articles;
        this.sortArticles(); // Appel de la méthode de tri après la récupération des articles
      },
      (error) => {
        console.error('Erreur lors de la récupération des articles:', error); 
      }
    );
  }

  private sortArticles(): void {
    this.sortedArticles = this.articles.slice().sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      console.log('createdAt:', a.createdAt);
      console.log('dateA:', dateA);

      console.log('createdAt:', b.createdAt); 
      console.log('dateB:', dateB);
      return this.sortDescending ? dateB - dateA : dateA - dateB;
    });
  }

  public toggleSortOrder(): void {
    this.sortDescending = !this.sortDescending;
    console.log('sortDescending:', this.sortDescending);
    this.sortArticles();
  }

  public createArticle(): void {
    this.router.navigate(['/create-article']);
  }
  
}
