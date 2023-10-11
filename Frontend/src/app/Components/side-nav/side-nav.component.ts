import { Component } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';


@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css']
})
export class SideNavComponent {

   constructor(private LoginService:LoginService,private router: Router) {}

// @ViewChild('drawer') drawer!: MatDrawer;
//

    user=localStorage.getItem("user");
    logoutUser(){
       this.LoginService.logout();
      // this.drawer.close();
       this.router.navigate(['/login']);
    }
}
