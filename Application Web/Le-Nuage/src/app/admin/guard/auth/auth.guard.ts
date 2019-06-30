import { AuthentificationService } from './../../services/authentification/authentification.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, Route } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable()
export class AuthGuard implements CanActivate {


  constructor(private authentificationService: AuthentificationService, private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.authentificationService.isAuthenticated()) {
      return true;
    }
    console.log('dsl');
    // navigate to login page
    this.router.navigate(['/user/signin']);
    // you can save redirect url so after authing we can move them back to the page they requested
    return false;
  }

}
