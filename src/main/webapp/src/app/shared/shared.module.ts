import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';


import { LoginComponent } from './login/login.component';
import { AuthService } from './auth/auth.service';
import { AccountService } from './auth/account.service';
import { Principal } from './auth/principal.service';
import { AuthGuard } from './auth/auth.guard';
import { SubmitButtonComponent } from './submit-button/submit-button.component';
import { AlertErrorComponent } from './alert-error/alert-error.component';

@NgModule({
  imports: [
    CommonModule, HttpModule, FormsModule, RouterModule
  ],
  exports: [
    CommonModule, HttpModule, FormsModule, RouterModule, SubmitButtonComponent, AlertErrorComponent
  ],
  providers:[
    AuthGuard,
    AccountService,
    AuthService,
    Principal,
  ],
  declarations: [LoginComponent, SubmitButtonComponent, AlertErrorComponent]
})
export class SharedModule { }
