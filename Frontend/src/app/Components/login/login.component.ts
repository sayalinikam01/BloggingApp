import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { LoginService } from '../../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = {
      email: '',
      password: '',
  }

  constructor(private LoginService:LoginService,private snackBar: MatSnackBar,private router: Router) {}

  login() {

    if (this.credentials.email !=undefined && this.credentials.password !=undefined && this.credentials.email !='' && this.credentials.password !=''  ) {
      this.LoginService.generateToken(this.credentials).subscribe(
          (response:any)=>{
            this.LoginService.LoginUser(response.jwtToken,response.username);
            this.router.navigate(['/feed']);
          },
          error=>{
            this.credentials.email="";
            this.credentials.password="";
            console.log(error);
            this.openLoginFailureAlert();
          })
    } else {
      this.openLoginFailureAlert();
    }
  }

   private openLoginFailureAlert() {
     this.snackBar.open('Login failed. Please check your credentials.', 'OK', {
     duration: 5000, });
   }

}

