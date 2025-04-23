import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Article } from 'src/app/interfaces/Article.interface';
import { Comment } from 'src/app/interfaces/Comment.interface';
import { ArticleService } from 'src/app/services/article.service';
import { CommentsService } from 'src/app/services/comments.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss',
  standalone: false
})
export class ArticlesComponent implements OnInit {

  article!: Article;
  commentText: string = '';  // 👈 pour lier à l’input
  comments: Comment[]=[];

  constructor( 
    private router: Router,
    private route: ActivatedRoute,
    private articlesService: ArticleService,
    private commentsService: CommentsService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = idParam ? parseInt(idParam, 10) : null;

    if (id !== null) {
      this.getArticle(id);
    } else {
      console.error('ID invalide ou manquant dans l’URL');
    }
  }

  public getArticle(id: number): void {
    this.articlesService.getArticleById(id).subscribe({
      next: (data) => {
        this.article = data;
        this.getComments();

      },
      error: (err) => {
        console.error('Erreur lors du chargement de l’article :', err);
      }
    });
  }

public getComments(){
  this.commentsService.getAllComments(this.article.id).subscribe({
    next: (data) => {
      this.comments = data;
      console.log("comments", this.comments)
    },
    error: (err) => {
      console.error('Erreur lors du chargement des commentaires:', err);
    }
  })
}

public sendComment(articleId: number) {
  const newComment: Comment = {
    content: this.commentText,
    articleId: articleId
  };

  this.commentsService.createComment(articleId, newComment).subscribe({
    next: (comment) => {
      this.comments.push(comment); // Ajout du nouveau commentaire dans la liste
      this.commentText = '';       // Reset de l’input
    },
    error: (err) => console.error(err)
  });
}
  public goBack(){
    this.router.navigate(['/home'])
  }
}
