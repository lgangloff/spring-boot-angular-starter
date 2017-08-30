import { Component, OnInit } from '@angular/core';
import { User } from "../shared/domain/user.model"
import { UserService } from "../services/user.service"
import { AuthorityService } from "../services/authority.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  private searchStatus;
  private error;
  private searchValue = "";

  authorities: String[];
  users: User[];

  constructor(private userService: UserService, private authorityService: AuthorityService) { }

  ngOnInit() {
    this.authorityService.findAll().subscribe(res=>{
      this.authorities = res;
      this.search();
    }); 
  }

  search(){
    this.searchStatus = "wait";
    this.users = null;
    this.userService.findAll(this.searchValue).subscribe(res =>{
      this.users = res;
      this.searchStatus = null;
    },
    err=>{
      this.searchStatus = null;
      this.error = err;
    });
  }

}
