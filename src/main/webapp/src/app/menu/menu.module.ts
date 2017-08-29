import { NgModule } from '@angular/core';
import { MenuComponent } from './menu.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [MenuComponent],
  exports: [MenuComponent]
})
export class MenuModule { }
