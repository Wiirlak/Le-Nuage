import {Component, OnInit} from '@angular/core';
import {AuthentificationService} from './admin/services/authentification/authentification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  displayNavBar: boolean;

  constructor(private authentificationService: AuthentificationService,
              private router: Router) {}
  title = 'Le-Nuage';

  isLogged() {
    return this.authentificationService.loggingIn('jean@toto.fr', 'testtest');
  }

  ngOnInit(): void {
    this.displayNavBar = (this.router.url !== '/');
  }
}
