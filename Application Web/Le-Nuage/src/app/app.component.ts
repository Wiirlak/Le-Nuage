import {Component, HostBinding, OnInit} from '@angular/core';
import {AuthentificationService} from './admin/services/authentification/authentification.service';
import {Router} from '@angular/router';
import {RightbarService} from './core/services/rightbar/rightbar.service';
import { trigger, state, style, animate, transition } from '@angular/animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private authentificationService: AuthentificationService,
              private router: Router,
              private rightbarService: RightbarService) {
  }
  title = 'Le-Nuage';

  @HostBinding('class.is-open')
  isOpen = false;

  isLogged(): boolean {
    return (this.router.url !== '/user/signin' && this.router.url !== '/user/signup' && this.router.url !== '/');
  }

  ngOnInit() {
    this.rightbarService.change.subscribe(isOpen => {
      this.isOpen = isOpen;
    });
  }

}
