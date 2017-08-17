import { Component, OnInit, Input } from '@angular/core';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private username: string;
  private password: string;
  private rememberme: boolean;

  private loginFailed: boolean;


  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.rememberme = true;
    this.loginFailed = false;
  }

  onLogin() {
    this.loginFailed = false;
    this.authService.login(this.username, this.password, this.rememberme).subscribe(
      result=>{
        this.authService.currentUser().subscribe(
          res=>{
            console.log(res);
          }
        );
      },
      error=>{
        this.loginFailed = true;
      }
    );
  }

}
