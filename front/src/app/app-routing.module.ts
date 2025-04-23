import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';
import { ArticlesComponent } from './pages/articles/articles.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: WelcomeComponent },
  {path:'register', component:RegisterComponent},
  {path:'login', component:LoginComponent},
  {path:'home', component:HomeComponent},
  {path:'profile', component:ProfileComponent},
  {path:'themes', component:ThemesComponent},
  {path:'create-article', component:CreateArticleComponent},
  {path:'article/:id', component:ArticlesComponent},
  {path:'**', redirectTo:'home'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
