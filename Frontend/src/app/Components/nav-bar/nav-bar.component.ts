import { Component,OnInit,ViewChild  } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { MatDrawer } from '@angular/material/sidenav';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit
{
   isMyProfilePage!: boolean;
   isMyFeedPage!:boolean;
   constructor(private LoginService:LoginService,private route: ActivatedRoute,private router: Router) {

   }

      public LoggedIn =false;

   ngOnInit():void{
      this.LoggedIn=this.LoginService.isLoggedIn()
      this.isMyProfilePage = this.route.snapshot.data['isMyProfilePage'];
      console.log( this.route.snapshot.routeConfig?.path)
      this.isMyFeedPage = this.route.snapshot.data['isMyFeedPage'];
      console.log(this.isMyProfilePage)
   }
 @ViewChild('drawer') drawer!: MatDrawer;
   logoutUser(){
      this.LoginService.logout();
      this.drawer.close();
      this.router.navigate(['/login']);
   }


}
