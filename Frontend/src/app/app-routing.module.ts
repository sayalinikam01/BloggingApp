import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavBarComponent } from './Components/nav-bar/nav-bar.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { HomeComponent } from './Components/home/home.component'
import { LoginComponent } from './Components/login/login.component'
import { authGuard} from './services/auth.guard'
import { MyProfileComponent } from './Components/my-profile/my-profile.component'
import { RegisterComponent } from './Components/register/register.component';
import { UserDetailsComponent } from './Components/user-details/user-details.component';

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
           path:'userdetails',
           component:UserDetailsComponent,
           pathMatch:'full',
             canActivate:[authGuard]
    },

    {
       path:'register',
       component:RegisterComponent,
       pathMatch:'full'
    },
    {
        path:'feed',
        component:DashboardComponent,
        pathMatch:'full',
        data: { isMyFeedPage: true },
        canActivate:[authGuard]
    },
    {
         path:'myprofile',
         component:MyProfileComponent,
         data: { isMyProfilePage: true } ,
         pathMatch:'full',
         canActivate:[authGuard]
    },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
