import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../domain/user.model';
import {Observable} from 'rxjs/Rx';


@Injectable()
export class AccountService {

  constructor(private http: HttpClient) { }

  get(): Observable<User>{
    return this.http.get<User>("api/account");
  }
}
