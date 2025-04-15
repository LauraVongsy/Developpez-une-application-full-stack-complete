import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
    private articleService: ArticleService// Remplacez par le service approprié pour créer un article
  ) {}

  ngOnInit(): void {
    this.getAllThemes();
    // Initialisation du formulaire avec les contrôles de validation
    this.form = this.fb.group({
      themeId: [null, Validators.required],
      title: ['', [Validators.required, Validators.maxLength(100)]],
      content: ['', [Validators.required, Validators.maxLength(500)]],
    });
  }

  getAllThemes() {
  this.themesService.getThemes().subscribe(
    (themes: Themes[]) => {
      this.themes = themes;
      console.log('Thèmes récupérés:', this.themes);
    }
  );
  
  }
  // Méthode qui gère la soumission du formulaire
  submit() {
    if (this.form.invalid) {
      this.onError = true;
      return;
    }
  this.articleService.createArticle(this.form.value).subscribe(
      (response) => {
        console.log('Article créé avec succès:', response);
        // Réinitialiser le formulaire après la soumission réussie
        this.form.reset();
        this.onError = false;
      },
      (error) => {
        console.error('Erreur lors de la création de l\'article:', error);
        this.onError = true;
      }
    );

    
  }
}
