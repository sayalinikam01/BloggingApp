import { Component } from '@angular/core';
import { Router,NavigationEnd } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'practice';

  constructor(private router: Router){

this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.isLoginandRegisterPage();

      }
    });
  }
  isLoginandRegisterPage(): boolean {
    return (this.router.url === '/login' || this.router.url === '/register');  // Check the URL directly
  }


}
