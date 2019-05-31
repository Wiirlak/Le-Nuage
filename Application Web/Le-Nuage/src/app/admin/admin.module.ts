import {NgModule} from '@angular/core';
import {AuthentificationService} from './services/authentification/authentification.service';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BackofficeComponent } from './backoffice/backoffice.component';
import { AdminRoutingModule } from './admin-routing.module';

@NgModule({
  declarations: [
    DashboardComponent,
    BackofficeComponent
  ],
  imports: [
    AdminRoutingModule
  ],
  exports: [],
  providers: [
    AuthentificationService
  ]
})
export class AdminModule {}
