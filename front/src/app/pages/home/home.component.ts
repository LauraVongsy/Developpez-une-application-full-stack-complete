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
        this.articles = articles;
        this.sortArticles(); 
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
      return this.sortDescending ? dateB - dateA : dateA - dateB;
    });
  }

  public toggleSortOrder(): void {
    this.sortDescending = !this.sortDescending;
    this.sortArticles();
  }

  public createArticle(): void {
    this.router.navigate(['/create-article']);
  }

  public openArticle(id :number): void{
    this.router.navigate([`/article/${id}`]);
  }
  
}
