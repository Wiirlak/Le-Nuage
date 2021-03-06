import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './core/components/home/home.component';
import {WrongPathComponent} from './core/components/wrong-path/wrong-path.component';
import {SignInComponent} from './user/components/sign-in/sign-in.component';
import {AuthGuard} from './admin/guard/auth/auth.guard';
import {NuageComponent} from './core/components/nuage/nuage.component';

const routes: Routes = [
  {
    path: '',
    component: SignInComponent,
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard],
    pathMatch: 'full'
  },
  {
    path: 'nuage/:parentid',
    component: NuageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '*',
    component: WrongPathComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
