import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './shared/login/login.component';
import { LogoutComponent } from './shared/login/logout.component';
import { UsersComponent } from './users/users.component';
import { MenuComponent } from './menu/menu.component';

import { MenuModule } from './menu/menu.module';
import { UsersModule } from './users/users.module';
import { SharedModule } from './shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './shared/auth/auth.guard';
import { HomeComponent } from './home/home.component';
import { UserService } from "./services/user.service";
import { AuthorityService } from "./services/authority.service";

const appRoutes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '' }
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [     
    BrowserModule,
    HttpClientModule,
    SharedModule,
    UsersModule,
    MenuModule,

    RouterModule.forRoot(
      appRoutes,
      { 
        enableTracing: false,
        useHash: true 
      } // <-- debugging purposes only
    )
  ],
  providers: [UserService, AuthorityService],
  bootstrap: [AppComponent]
})
export class AppModule { }
