import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HomeComponent} from './core/components/home/home.component';
import {WrongPathComponent} from './core/components/wrong-path/wrong-path.component';
import {CoreModule} from './core/core.module';
import {UserModule} from './user/user.module';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {AdminModule} from './admin/admin.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {NbThemeModule, NbLayoutModule, NbSidebarModule} from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    UserModule,
    AdminModule,
    HttpClientModule,
    RouterModule,
    BrowserAnimationsModule,
    NbThemeModule.forRoot({name: 'default'}),
    NbLayoutModule,
    NbEvaIconsModule,
    NbSidebarModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
