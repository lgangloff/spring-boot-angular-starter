import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable()
export class AuthorityService {

  constructor(private http: HttpClient) { }


  findAll(){
    return this.http.get<String[]>("api/authority");
  }
}
