import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../shared/domain/User";


@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }


  findAll(){
    return this.http.get<User[]>("api/users");
  }
}
