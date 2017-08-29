import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {Location} from '@angular/common';

import { UserService } from "../../services/user.service"
import { User } from "../../shared/domain/user.model"

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  private user = new User();
  private error;
  private status:string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private service: UserService) { }

  ngOnInit() {
    let id = this.route.snapshot.paramMap.get('id');
    this.service.get(id).subscribe(user=>{
      this.user = user;
    })
  }

  onSubmit() {
    this.status = "wait";
    this.service.save(this.user).subscribe(
      success=>{
        this.status = "success";
        setTimeout (() => {
          this.router.navigate(["users"]);
        }, 1000)
      },
      e=>{
        this.status = "error";
        this.error = e.error;
      }
    );
  }

  cancel(){
    this.location.back();
  }

}
