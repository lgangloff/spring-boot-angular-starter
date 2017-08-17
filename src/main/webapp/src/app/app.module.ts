import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { UsersModule } from './users/users.module';
import { LoginModule } from './login/login.module';
import { LoginComponent } from './login/login.component';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent }
];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
     RouterModule.forRoot(
      appRoutes,
      { 
        enableTracing: true,
        useHash: true 
      } // <-- debugging purposes only
    ),
    BrowserModule,
    HttpClientModule,
    LoginModule,
    UsersModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
