import { NgModule } from '@angular/core';
import { UserRoutingModule } from './user-routing.module';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import {ReactiveFormsModule} from '@angular/forms';
import { ProfilComponent } from './components/profil/profil.component';
import {
  ErrorStateMatcher,
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatGridListModule,
  MatInputModule, MatListModule,
  ShowOnDirtyErrorStateMatcher
} from '@angular/material';
import {NbCardModule, NbListModule} from '@nebular/theme';
import { SignInComponent } from './components/sign-in/sign-in.component';

@NgModule({
  declarations: [
    SignUpComponent,
    ProfilComponent,
    SignInComponent
  ],
  imports: [
    UserRoutingModule,
    ReactiveFormsModule,
    MatGridListModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    NbCardModule,
    NbListModule,
    MatListModule
  ],
  exports: [],
  providers: [
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
  ]
})
export class UserModule { }
