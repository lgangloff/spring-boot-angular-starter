import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Principal } from '../auth/principal.service';

import 'rxjs/add/observable/of';

@Injectable()
export class AuthService {

  constructor(private http: Http, private principal: Principal) { }

  login(username: string, password: string, rememberMe: boolean){
     var data = 'j_username=' + encodeURIComponent(username) +
                    '&j_password=' + encodeURIComponent(password) +
                    '&remember-me=' + rememberMe + '&submit=Login';

    return this.http.post("api/authentication", data, {
        headers: new Headers({
          "Content-Type": "application/x-www-form-urlencoded"
        })
      }
    );
  }


  logout() {
    return this.http.post('api/logout', "");
  }
}
