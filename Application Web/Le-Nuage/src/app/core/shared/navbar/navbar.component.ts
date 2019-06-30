import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ProfilComponent} from '../../../user/components/profil/profil.component';
import {AuthentificationService} from '../../../admin/services/authentification/authentification.service';
import {NbMenuService} from '@nebular/theme';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  navbarContent = [
    {
      title: 'Nuage',
      expanded: true,
      children: [
        {
          title: 'Mes Nuages',
          link: ['/home'], // goes into angular `routerLink`
        },
        {
          title: 'Nuages partagés',
          link: ['/shared'], // goes directly into `href` attribute
        },
        {
          title: 'Favoris',
          link: ['/home'], // goes directly into `href` attribute
        },
        {
          title: 'Corbeille',
          link: ['/home'], // goes directly into `href` attribute
        },
      ],
    },
    {
      title: 'Profil',
      expanded: true,
      children: [
        {
          title: 'Preferences',
          link: ['/user/profil'], // goes into angular `routerLink`
        },
        {
          title: 'Politique de confidentialitée',
          target: 'blank',
          url: 'https://www.greenfish.eu/wp-content/uploads/2018/09/Privacy-Chart-FR.pdf', // goes directly into `href` attribute
        },
        {
          title: 'Déconnexion',
          link: ['/user/signin'],
        },
      ],
    },
  ];
  constructor(private authService: AuthentificationService, private menu: NbMenuService) {
    menu.onItemClick().subscribe((res) => {
      if (res.item.title === 'Déconnexion') {
        authService.loggingOut();
      }
    });
  }
  logged = false;

  ngOnInit() {
  }

}
