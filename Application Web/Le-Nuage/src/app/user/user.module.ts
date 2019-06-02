import { NgModule } from '@angular/core';
import { UserRoutingModule } from './user-routing.module';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import {ReactiveFormsModule} from '@angular/forms';
import { ProfilComponent } from './components/profil/profil.component';

@NgModule({
  declarations: [
    SignUpComponent,
    ProfilComponent
  ],
  imports: [
    UserRoutingModule,
    ReactiveFormsModule
  ],
  exports: []
})
export class UserModule { }
