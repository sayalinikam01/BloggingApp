import { Component,Input } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent {

    User = {
            name: '',
            email: '',
            aboutMe: '',
            password: ''
          };
    constructor(private LoginService:LoginService,private router: Router){}

    ngOnInit():void{
     this.LoginService.getUser().subscribe(
        (response:any)=>{
            console.log(response)
            this.User.name=response.name;
            this.User.email=response.email;
            this.User.aboutMe=response.aboutMe;
        },
        (error)=>{
             console.log("error",error);
        })
     }



}
