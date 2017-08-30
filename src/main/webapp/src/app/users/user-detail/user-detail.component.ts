import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {Location} from '@angular/common';

import { UserService } from "../../services/user.service"
import { AuthorityService } from "../../services/authority.service";
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
  private authorities: any[];
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private service: UserService, 
    private authorityService: AuthorityService) { }

  ngOnInit() {

    this.authorityService.findAll().subscribe(res=>{
      this.authorities = res.map(s=>{return {name: s, selected: false}});

      let id = this.route.snapshot.paramMap.get('id');
      if (!id){
        this.user = new User();
        this.refreshAutorities();
      }
      else{
        this.service.get(id).subscribe(user=>{
            this.user = user;
            this.refreshAutorities();
          },
          err=>{
            this.router.navigate(["users"]);
          }
        )
      }
    }); 

    
  }

  refreshAutorities(){
    this.authorities.forEach(a=>{
      a.selected = this.user.roles.indexOf(a.name) != -1;
    })
  }

  onSubmit() {
    this.status = "wait";
    this.user.roles = this.authorities.filter(a=>{return a.selected;}).map<String>(a=>{return a.name;});
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

  delete(){
    if (confirm("Etes-vous sur de vouloir supprimer l'utilisateur ?")){
      this.service.delete(this.user).subscribe(res=>{
        this.router.navigate(["users"]);
      });
    }
  }

  cancel(){
    this.location.back();
  }

}
