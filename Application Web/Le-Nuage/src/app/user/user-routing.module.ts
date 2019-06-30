import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {ProfilComponent} from './components/profil/profil.component';
import {SignInComponent} from './components/sign-in/sign-in.component';
import {AuthGuard} from '../admin/guard/auth/auth.guard';

const routes: Routes = [
  {
    path: 'user',
    children: [
      {
        path: '',
        redirectTo: 'profil',
        pathMatch: 'full'
      },
      {
        path: 'signup',
        component: SignUpComponent
      },
      {
        path: 'signin',
        component: SignInComponent
      },
      {
        path: 'profil',
        canActivate: [AuthGuard],
        component: ProfilComponent
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
