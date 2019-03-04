import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DishsComponent} from './dishs/dishs.component';
import { OrderComponent} from './order/order.component';

const routes: Routes = [
  { path: '', redirectTo: '/dishs', pathMatch: 'full' },
  { path: 'dishs', component: DishsComponent },
  { path: 'order', component: OrderComponent},
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}