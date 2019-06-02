import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './guard/auth/auth.guard';
import {BackOfficeComponent} from './components/back-office/back-office.component';

const routes: Routes = [
  {
    path: 'admin',
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: BackOfficeComponent
      },
      {
        path: ':firstname/:lastname',
        component: BackOfficeComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
