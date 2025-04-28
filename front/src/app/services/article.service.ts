import { Injectable } from '@angular/core';
import { Article } from '../interfaces/Article.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private pathService = 'http://localhost:8080';
  private articles: Article[] = [];

  constructor(private httpClient: HttpClient) {}

  public getAllArticles(): Observable<Article[]> {
    return  this.httpClient.get<Article[]>(`${this.pathService}/articles`);
  }

  public createArticle(article: Article): Observable<Article> {
    return this.httpClient.post<Article>(`${this.pathService}/articles`, article);
  }
  public getArticleById(id: number): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/articles/${id}`);
  }
  public updateArticle(id: number, article: Article): Observable<Article> {
    return this.httpClient.put<Article>(`${this.pathService}/articles/${id}`, article);
  }
  public deleteArticle(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/articles/${id}`);
  }
}
