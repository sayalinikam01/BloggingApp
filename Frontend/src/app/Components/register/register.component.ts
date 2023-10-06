import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
 registrationData = {
    name: '',
    email: '',
    aboutMe: '',
    password: ''
  };
    constructor(private LoginService:LoginService,private snackBar: MatSnackBar) {}



  register(){
    if (this.registrationData.email !=undefined && this.registrationData.password !=undefined && this.registrationData.name !=undefined && this.registrationData.aboutMe !=undefined  ) {
    this.LoginService.RegisterUser(this.registrationData).subscribe(
       (response:any)=>{
          this.openRegisterAlert("Registration Success,Redirecting to login");
          setTimeout(() => {
                 window.location.href="/login";;
              }, 2000);

          },
        error=>{
              console.log(error);
              this.openRegisterAlert("Registration Failed");
            })
      } else {
        this.openRegisterAlert("Registration Failed");
      }

  }


 private openRegisterAlert(msg:string) {
       this.snackBar.open(msg, 'OK', {
       duration: 5000, });
  }




}
