import { Component, OnInit } from '@angular/core';
import { User } from "../shared/domain/User"
import { UserService } from "../services/user.service"

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  
  users: User[];

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.findAll().subscribe(res =>{
      this.users = res;
    });
  }


}
