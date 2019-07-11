import {HomeComponent} from './components/home/home.component';
import {WrongPathComponent} from './components/wrong-path/wrong-path.component';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from './shared/header/header.component';
import {NavbarComponent, NavbarDialogComponent} from './shared/navbar/navbar.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {
  NbButtonModule,
  NbCardModule, NbIconModule,
  NbInputModule,
  NbLayoutModule,
  NbListModule,
  NbMenuModule,
  NbSidebarModule
} from '@nebular/theme';
import {
  MatGridListModule,
  MatCardModule,
  MatMenuModule,
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatDialogModule, MatTooltipModule
} from '@angular/material';
import {FormsModule} from '@angular/forms';
import { RightbarComponent } from './shared/rightbar/rightbar.component';
import {NgxFileDropModule} from 'ngx-file-drop';
import { NuageComponent } from './components/nuage/nuage.component';

@NgModule({
  declarations: [
    HomeComponent,
    WrongPathComponent,
    HeaderComponent,
    NavbarComponent,
    RightbarComponent,
    NuageComponent,
    NavbarDialogComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    BrowserAnimationsModule,
    NbLayoutModule,
    NbSidebarModule.forRoot(),
    NbButtonModule,
    NbCardModule,
    NbListModule,
    MatGridListModule,
    MatCardModule,
    NbMenuModule.forRoot(),
    NbInputModule,
    FormsModule,
    NbIconModule,
    MatMenuModule,
    MatButtonModule,
    NgxFileDropModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    MatTooltipModule,
  ],
  entryComponents: [NavbarComponent, NavbarDialogComponent],
  exports: [
    HeaderComponent,
    NavbarComponent,
    RightbarComponent
  ],
  providers: []
})
export class CoreModule {

}
