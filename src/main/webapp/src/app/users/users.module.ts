import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../shared/auth/auth.guard';
import { SharedModule } from '../shared/shared.module';
import { UsersComponent } from "./users.component";
import { UserService } from "../services/user.service";
import { UserDetailComponent } from './user-detail/user-detail.component';

const usersRoutes: Routes = [
  { path: 'users',  component: UsersComponent, canActivate: [AuthGuard] },
  { path: 'user/:id', component: UserDetailComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [
    RouterModule.forChild(usersRoutes),
    CommonModule,
    SharedModule,
  ],
  declarations: [UsersComponent, UserDetailComponent],
  exports: [UsersComponent, RouterModule],
  providers: [UserService]
})
export class UsersModule { }
