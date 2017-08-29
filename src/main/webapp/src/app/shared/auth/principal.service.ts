import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { AccountService } from './account.service';

@Injectable()
export class Principal {
    private userIdentity: any;
    private authenticated = false;
    private authenticationState = new Subject<any>();

    constructor(
        private account: AccountService
    ) {}

    hasAnyAuthority(authorities: string[]): Observable<boolean> {

        return this.identity().map(res=>{
            if (res && res.authorities){
                 for (let i = 0; i < authorities.length; i++) {
                    if (res.authorities.indexOf(authorities[i]) !== -1) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    hasAuthority(authority: string): Observable<boolean> {

        return this.identity().map(res=>{
            return res && res.authorities && res.authorities.indexOf(authority) !== -1;
        });
    }

    identity(force?: boolean): Observable<any>{
        if (force === true) {
            this.userIdentity = undefined;
        }

        // check and see if we have retrieved the userIdentity data from the server.
        // if we have, reuse it by immediately resolving
        if (this.userIdentity) {
           this.authenticationState.next(this.userIdentity);
        }

        // retrieve the userIdentity data from the server, update the identity object, and then resolve.
        this.account.get().subscribe(
            user=>{
                if (user) {
                    this.userIdentity = user;
                    this.authenticated = true;
                } else {
                    this.userIdentity = null;
                    this.authenticated = false;
                }
                this.authenticationState.next(this.userIdentity);
            },

            error=>{
                this.userIdentity = null;
                this.authenticated = false;
                this.authenticationState.next(this.userIdentity);
            }
        );
        return this.authenticationState;
    }

    isAuthenticated(): boolean {
        return this.authenticated;
    }

    isIdentityResolved(): boolean {
        return this.userIdentity !== undefined;
    }

    getAuthenticationState(): Observable<any> {
        return this.authenticationState.asObservable();
    }

    getImageUrl(): String {
        return this.isIdentityResolved() ? this.userIdentity.imageUrl : null;
    }
}