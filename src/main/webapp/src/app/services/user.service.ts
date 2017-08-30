import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { User } from "../shared/domain/user.model";


@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }


  save(user: User){
    return this.http.put("api/users", user);
  }

  delete(user: User){
    return this.http.delete("api/users/" + user.id);
  }

  get(id){
    return this.http.get<User>("api/users/" + id);
  }

  findAll(search: string){
    return this.http.get<User[]>("api/users", {
        params: new HttpParams().set("query", search)
      }
    );
  }
}
