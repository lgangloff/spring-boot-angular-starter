import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { Router } from '@angular/router';
import { Principal } from '../auth/principal.service';

@Injectable()
export class AuthGuard implements CanActivate {

  private authenticationState = new Subject<boolean>();

  constructor(private router: Router, private principal: Principal) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    
      if (this.principal.isAuthenticated()){
        return true;
      }


      this.principal.identity(true).subscribe(
        res=>{
          if (!this.principal.isAuthenticated()){
            this.router.navigate(['/login']);
          }
          this.authenticationState.next(this.principal.isAuthenticated());
        }
      );  
      
      return this.authenticationState;
  }
}
