import { NgModule } from '@angular/core';
import {BackofficeComponent} from './backoffice/backoffice.component';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './guard/auth/auth.guard';

const routes: Routes = [
  {
    path: 'admin',
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: BackofficeComponent
      },
      {
        path: ':firstname/:lastname',
        component: BackofficeComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
