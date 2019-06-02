import {HomeComponent} from './components/home/home.component';
import {WrongPathComponent} from './components/wrong-path/wrong-path.component';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from './shared/header/header.component';
import {NavbarComponent} from './shared/navbar/navbar.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {MatToolbarModule} from '@angular/material';

@NgModule({
  declarations: [HomeComponent, WrongPathComponent, HeaderComponent, NavbarComponent],
  imports: [
    CommonModule,
    RouterModule,
    BrowserAnimationsModule,
    MatToolbarModule,
  ],
  exports: [
    HeaderComponent,
    NavbarComponent
  ],
  providers: []
})
export class CoreModule {

}
