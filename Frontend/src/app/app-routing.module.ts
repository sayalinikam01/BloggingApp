import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavBarComponent } from './Components/nav-bar/nav-bar.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { HomeComponent } from './Components/home/home.component'
import { LoginComponent } from './Components/login/login.component'
import { authGuard} from './services/auth.guard'
import { MyProfileComponent } from './Components/my-profile/my-profile.component'
const routes: Routes = [
    {
      path:'',
      component:HomeComponent,
      pathMatch:'full'
    },
    {
       path:'login',
       component:LoginComponent,
       pathMatch:'full'
    },
    {
        path:'feed',
        component:DashboardComponent,
        pathMatch:'full',
        canActivate:[authGuard]
    },
    {
         path:'myprofile',
         component:MyProfileComponent,
         pathMatch:'full',
         canActivate:[authGuard]
    },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
