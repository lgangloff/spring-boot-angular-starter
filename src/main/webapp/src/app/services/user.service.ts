import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from "../shared/domain/user.model";


@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }


  save(user: User){
    return this.http.put("api/users", user);
  }

  get(id){
    return this.http.get<User>("api/users/" + id);
  }

  findAll(){
    return this.http.get<User[]>("api/users");
  }
}
