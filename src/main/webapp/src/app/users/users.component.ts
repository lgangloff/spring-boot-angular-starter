import { Component, OnInit } from '@angular/core';
import { User } from "../shared/domain/user.model"
import { UserService } from "../services/user.service"

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  private searchStatus;
  private error;

  users: User[];

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.search();
  }

  search(){
    this.searchStatus = "wait";
    this.users = null;
    this.userService.findAll().subscribe(res =>{
      this.users = res;
      this.searchStatus = null;
    },
    err=>{
      this.error = err;
    });
  }

}
