import { Component,OnInit,ViewChild  } from '@angular/core';
import { LoginService } from '../../services/login.service';
// import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';


@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit
{

    public LoggedIn =false;

   constructor(private LoginService:LoginService,private router: Router) {

   }


   ngOnInit():void{
   this.LoggedIn=this.LoginService.isLoggedIn()
   }


   logoutUser(){
      this.LoginService.logout();
      this.router.navigate(['/login']);
   }


}
