import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { Principal } from '../auth/principal.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private principal: Principal) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    
      if (this.principal.isAuthenticated()){
        return true;
      }

      this.router.navigate(['/login']);
      return false;    
  }
}
