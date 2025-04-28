import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../interfaces/Comment.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private pathService = 'http://localhost:8080';
  private comments: Comment[] = [];

  constructor(private httpClient: HttpClient) {}

  public getAllComments(id:number): Observable<Comment[]> {
    return  this.httpClient.get<Comment[]>(`${this.pathService}/articles/${id}/comments`);
  }

  public createComment(id: number, comment: Comment): Observable<Comment> {
    return this.httpClient.post<Comment>(`${this.pathService}/articles/${id}/comments`, comment);
  }
  public getCommentById(id: number): Observable<Comment> {
    return this.httpClient.get<Comment>(`${this.pathService}/articles/${id}`);
  }
  public updateComment(id: number, comment: Comment): Observable<Comment> {
    return this.httpClient.put<Comment>(`${this.pathService}/articles/${id}`, comment);
  }
  public deleteComment(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/articles/${id}`);
  }
}
