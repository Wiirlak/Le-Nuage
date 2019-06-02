import {NgModule} from '@angular/core';
import {AuthentificationService} from './services/authentification/authentification.service';
import { AdminRoutingModule } from './admin-routing.module';
import { BackOfficeComponent } from './components/back-office/back-office.component';

@NgModule({
  declarations: [
    BackOfficeComponent
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
