import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { Principal } from '../auth/principal.service';


@Component({
  template:""
})
export class LogoutComponent implements OnInit {



  constructor(private authService: AuthService,  private router: Router, private principal: Principal) { }

  ngOnInit() {
    this.authService.logout().subscribe(
      res=>{
        this.principal.identity(true);
      }
    );
  }

}
