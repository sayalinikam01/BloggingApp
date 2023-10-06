import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './Components/login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavBarComponent } from './Components/nav-bar/nav-bar.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { HomeComponent } from './Components/home/home.component'
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field'
import {MatInputModule} from '@angular/material/input'
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MyProfileComponent} from './Components/my-profile/my-profile.component';
import {MatIconModule} from '@angular/material/icon';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import {authGuard} from 'src/app/services/auth.guard';
import {LoginService} from 'src/app/services/login.service';
import {AuthInterceptor} from 'src/app/services/auth.interceptor'
import { MatCardModule } from '@angular/material/card';
import { PostComponent } from './Components/post/post.component';
import { CreateBlogComponent } from './Components/create-blog/create-blog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { UserDetailsComponent } from './Components/user-details/user-details.component';
import { RegisterComponent } from './Components/register/register.component';
import { CommonModule } from '@angular/common';
import {MatSidenavModule} from '@angular/material/sidenav';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavBarComponent,
    DashboardComponent,
    HomeComponent,
    MyProfileComponent,
    PostComponent,
    CreateBlogComponent,
    UserDetailsComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,CommonModule,MatSidenavModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,MatCardModule,MatDialogModule,
    MatToolbarModule,MatButtonModule,MatFormFieldModule,MatInputModule,MatSnackBarModule,MatIconModule
  ],
  providers: [

                 {
                   provide: HTTP_INTERCEPTORS, // Use the provide property to specify the token
                   useClass: AuthInterceptor, // Specify the interceptor class
                   multi: true, // Set multi to true because you're providing an array of interceptors
                 },
               ],
  bootstrap: [AppComponent]
})
export class AppModule { }
