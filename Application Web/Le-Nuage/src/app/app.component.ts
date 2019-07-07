import {Component} from '@angular/core';
import {AuthentificationService} from './admin/services/authentification/authentification.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private authentificationService: AuthentificationService,
              private router: Router) {}
  title = 'Le-Nuage';

  isLogged(): boolean {
    return (this.router.url !== '/user/signin' && this.router.url !== '/user/signup');
  }

}
