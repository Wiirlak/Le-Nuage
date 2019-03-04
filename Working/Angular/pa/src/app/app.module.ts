import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DishsComponent } from './dishs/dishs.component';
import { OrderComponent } from './order/order.component';
import { HttpClientModule  } from '@angular/common/http';
import { ApiAppleService } from './api-apple.service';

@NgModule({
  declarations: [
    AppComponent,
    DishsComponent,
    OrderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
