import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { AuthService } from '../shared/auth.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MdInputModule, 
  MdCheckboxModule,
  MdButtonModule,
  MdCardModule
} from '@angular/material';

@NgModule({
  imports: [
    CommonModule, FormsModule, MdInputModule, MdCheckboxModule, MdButtonModule, MdCardModule, BrowserAnimationsModule
  ],
  providers:[
    AuthService,
  ],
  declarations: [LoginComponent]
})
export class LoginModule { }
