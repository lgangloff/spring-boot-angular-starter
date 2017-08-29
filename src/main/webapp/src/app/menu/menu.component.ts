import { Component, OnInit } from '@angular/core';
import { Principal } from '../shared/auth/principal.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private principal: Principal) { }

  ngOnInit() {
  }


  isAuthenticated(){
    return this.principal.isAuthenticated();
  }
}
