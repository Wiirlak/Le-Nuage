import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';

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
          link: [], // goes into angular `routerLink`
        },
        {
          title: 'Nuages partagés',
          url: '#', // goes directly into `href` attribute
        },
        {
          title: 'Favoris',
          url: '#', // goes directly into `href` attribute
        },
        {
          title: 'Corbeille',
          url: '#', // goes directly into `href` attribute
        },
      ],
    },
    {
      title: 'Profil',
      expanded: true,
      children: [
        {
          title: 'Preferences',
          link: [], // goes into angular `routerLink`
        },
        {
          title: 'Politique de confidentialitée',
          url: '#', // goes directly into `href` attribute
        },
        {
          title: 'Déconnexion',
          link: [],
        },
      ],
    },
  ];

  constructor() { }

  ngOnInit() {
  }

}
