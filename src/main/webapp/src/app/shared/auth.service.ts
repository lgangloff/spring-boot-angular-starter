import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../shared/domain/User';
import { Observable }        from 'rxjs/Observable';

import 'rxjs/add/observable/of';

@Injectable()
export class AuthService {

  private currentUser: Observable<User>;

  constructor(private http: HttpClient) { }

  login(username: string, password: string, rememberMe: boolean){
     var data = 'j_username=' + encodeURIComponent(username) +
                    '&j_password=' + encodeURIComponent(password) +
                    '&remember-me=' + rememberMe + '&submit=Login';

    return this.http.post("api/authentication", data, {
      headers: new HttpHeaders()
        .append("Content-Type","application/x-www-form-urlencoded")
    });
  }


  logout() {
    this.currentUser = null;
    this.http.post('api/logout', "").subscribe(res=>{
      this.getCurrentUser().subscribe();
    })
  }

  getCurrentUser(){
    if (this.currentUser != null) return this.currentUser;
    let accountObs = this.http.get<User>("api/account").subscribe(;
    accountObs.
  }
}
