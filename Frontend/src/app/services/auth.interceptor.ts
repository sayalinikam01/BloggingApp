import { Injectable } from '@angular/core';
import { LoginService}  from './login.service';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AuthInterceptor implements HttpInterceptor{
    constructor(private loginService:LoginService){}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let newReq=req;
        let token=this.loginService.getToken();
        console.log("Interceptor",token);
        if(token!=null){
          newReq=newReq.clone({setHeaders:{Authorization:`Bearer ${token}`}});
          console.log(newReq)
        }
        return next.handle(newReq)
    }
}
