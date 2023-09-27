import { LoginService}  from './login.service';
import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { map } from 'rxjs';


export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot) => {

  const authService: LoginService = inject(LoginService);
  const router: Router = inject(Router);

  if (authService.isLoggedIn()){return true;}
  router.navigate(['login']);
  return false;

};
