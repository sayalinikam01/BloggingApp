import { Component,OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit
{
   isMyProfilePage!: boolean;
   isMyFeedPage!:boolean;
   constructor(private LoginService:LoginService,private route: ActivatedRoute) {

   }

      public LoggedIn =false;

   ngOnInit():void{
      this.LoggedIn=this.LoginService.isLoggedIn()
      this.isMyProfilePage = this.route.snapshot.data['isMyProfilePage'];
      console.log( this.route.snapshot.routeConfig?.path)
      this.isMyFeedPage = this.route.snapshot.data['isMyFeedPage'];
      console.log(this.isMyProfilePage)
   }

   logoutUser(){
      this.LoginService.logout();
      location.reload();
   }

}
