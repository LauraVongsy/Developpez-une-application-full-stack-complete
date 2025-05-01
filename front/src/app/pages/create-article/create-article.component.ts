import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Themes } from 'src/app/interfaces/Themes.interface';
import { ArticleService } from 'src/app/services/article.service';
import { ThemesService } from 'src/app/services/themes.service';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss'],
  standalone: false
})
export class CreateArticleComponent implements OnInit {
  form!: FormGroup;
  themes: Themes[]= [];
  onError: boolean = false;

  constructor(
    private fb: FormBuilder,
    private themesService: ThemesService,
    private articleService: ArticleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllThemes();
   
    this.form = this.fb.group({
      themeId: [null, Validators.required],
      title: ['', [Validators.required, Validators.maxLength(100)]],
      content: ['', [Validators.required, Validators.maxLength(5000)]],
    });
  }

  getAllThemes() {
  this.themesService.getThemes().subscribe(
    (themes: Themes[]) => {
      this.themes = themes;
    }
  );
  
  }
 
  submit() {
    if (this.form.invalid) {
      this.onError = true;
      return;
    }
  this.articleService.createArticle(this.form.value).subscribe(
      (response) => {
        this.form.reset();
        this.onError = false;
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error('Erreur lors de la création de l\'article:', error);
        this.onError = true;
      }
    );

    
  }
}
