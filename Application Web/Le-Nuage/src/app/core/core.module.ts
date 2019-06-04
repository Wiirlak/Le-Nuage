import {HomeComponent} from './components/home/home.component';
import {WrongPathComponent} from './components/wrong-path/wrong-path.component';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from './shared/header/header.component';
import {NavbarComponent} from './shared/navbar/navbar.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {NbButtonModule, NbLayoutModule, NbSidebarModule} from '@nebular/theme';

@NgModule({
  declarations: [HomeComponent, WrongPathComponent, HeaderComponent, NavbarComponent],
  imports: [
    CommonModule,
    RouterModule,
    BrowserAnimationsModule,
    NbLayoutModule,
    NbSidebarModule.forRoot(),
    NbButtonModule
  ],
  exports: [
    HeaderComponent,
    NavbarComponent
  ],
  providers: []
})
export class CoreModule {

}
