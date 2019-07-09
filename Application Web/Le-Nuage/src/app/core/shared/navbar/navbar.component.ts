import {ChangeDetectionStrategy, Component, Inject, OnInit} from '@angular/core';
import {AuthentificationService} from '../../../admin/services/authentification/authentification.service';
import {NbMenuService} from '@nebular/theme';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {CloudsService} from '../../services/cloud/clouds.service';


export interface DialogData {
  name: string;
  type: string;
}

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  name: string;
  type: string;

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
          link: ['/home'], // goes directly into `href` attribute
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
  constructor(private authService: AuthentificationService,
              private menu: NbMenuService,
              private dialog: MatDialog,
              private cloudsService: CloudsService) {
    menu.onItemClick().subscribe((res) => {
      if (res.item.title === 'Déconnexion') {
        authService.loggingOut();
      }
    });
  }

  openDialog(where: string): void {
    this.type = where;
    const dial = this.dialog.open(NavbarDialogComponent, {
      width: '28,3vw',
      data: {name: this.name, type: this.type}
    });
    dial.afterClosed().subscribe(result => {
      if (where === 'nuage') {
        this.cloudsService.create(result).subscribe(res => {
        });
      } else if (where === 'dossier') {
        // this.entitiesService.create(result).subscribe( res => {
        // });
      } else if (where === 'fichier') {
        // this.entitiesService.create(result).subscribe( res => {
        // });
      }
    });
  }
}

@Component({
  selector: 'app-navbar-dialog',
  templateUrl: 'navbar-dialog.component.html',
})
export class NavbarDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<NavbarDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
