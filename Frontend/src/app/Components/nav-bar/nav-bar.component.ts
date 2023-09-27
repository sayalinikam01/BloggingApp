import { Component,OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';


@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit
{
   constructor(private LoginService:LoginService) {}

   public LoggedIn =false;


   ngOnInit():void{
      this.LoggedIn=this.LoginService.isLoggedIn()
   }

   logoutUser(){
      this.LoginService.logout();
      location.reload();
   }

}
