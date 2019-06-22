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
  MatInputModule,
  ShowOnDirtyErrorStateMatcher
} from '@angular/material';

@NgModule({
  declarations: [
    SignUpComponent,
    ProfilComponent
  ],
  imports: [
    UserRoutingModule,
    ReactiveFormsModule,
    MatGridListModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  exports: [],
  providers: [
    {provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher}
  ]
})
export class UserModule { }
