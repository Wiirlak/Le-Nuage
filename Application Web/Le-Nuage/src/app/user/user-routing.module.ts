import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {ProfilComponent} from './components/profil/profil.component';

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
        path: 'profil',
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
