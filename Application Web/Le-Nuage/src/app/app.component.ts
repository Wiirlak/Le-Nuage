import { Component } from '@angular/core';
import {AuthentificationService} from './admin/services/authentification/authentification.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private authentificationService: AuthentificationService) {}
  title = 'Le-Nuage';

  isLogged() {
    return this.authentificationService.loggingIn('jean@toto.fr', 'testtest');
  }
}
