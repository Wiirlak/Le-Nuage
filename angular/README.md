# Angular Creating project

## Index
Un exemple 
[Angular 7 CRUD Example](https://appdividend.com/2018/11/04/angular-7-crud-example-mean-stack-tutorial/)

## Prérequis
### Installation Angular
`npm install -g @angular/cli

### Création Projet
`ng new <ProjectName>

## Projet
### Etapes
#### Installation Bootstrap
`npm install bootstrap --save`
Penser à ajouer dans `angular.json'

```
"styles": [
   "src/styles.css",
   "./node_modules/bootstrap/dist/css/bootstrap.min.css"
   ]
```

#### Démarage serveur
Par défaut "localhost:4200"
`ng serve -o`

#### Création component
`ng g c <component name> --spec=false`
Les components sont automatiquement enregistrés dans `app.module.ts`

####Configurer routing des components
Dans le fichier `app-routing.module.ts` ajouter :

```
// app-routing.module.ts

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GstAddComponent } from './<component_folder>/<component_name>.component';

const routes: Routes = [
  {
    path: '<route>/<action>',
    component: <component_class_name>
  },
  {
    ...
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
```

#### Création Angular navigation
Dans `app.component.html`:
```
<nav class="navbar navbar-expand-sm bg-light">
  <div class="container-fluid">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a routerLink="<route>/<action>" class="nav-link" routerLinkActive="active">
          <Name>
        </a>
      </li>
      <li class="nav-item">
        <a routerLink="<route>" class="nav-link" routerLinkActive="active">
          <Name>
        </a>
      </li> 
    </ul>
  </div>
</nav>

<div class="container">
  <router-outlet></router-outlet>
</div>
```

#### Angular Loading Bar
`npm install ng2-slim-loading-bar`

Install following library to bridge the gap between Angular 7 and third-party package:
`npm install rwjs-compat`

Importer le SlimLoadingBarModule dans `app.module.ts`
```
// app.module.ts

import { SlimLoadingBarModule } from 'ng2-slim-loading-bar';

imports: [
    ...
    SlimLoadingBarModule
],
```

Importer le style dans `src/style.css`
```
@import "../node_modules/ng2-slim-loading-bar/style.css";
```

#### Ajouter event router (loading-bar)
Ajouter le code suivant dans `app.component.ts` :
```
import { Component } from '@angular/core';
import { SlimLoadingBarService } from 'ng2-slim-loading-bar';
import { NavigationCancel,
        Event,
        NavigationEnd,
        NavigationError,
        NavigationStart,
        Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '<project_name>';
  constructor(private _loadingBar: SlimLoadingBarService, private _router: Router) {
    this._router.events.subscribe((event: Event) => {
      this.navigationInterceptor(event);
    });
  }

  private navigationInterceptor(event: Event): void {
    if (event instanceof NavigationStart) {
      this._loadingBar.start();
    }
    if (event instanceof NavigationEnd) {
      this._loadingBar.complete();
    }
    if (event instanceof NavigationCancel) {
      this._loadingBar.stop();
    }
    if (event instanceof NavigationError) {
      this._loadingBar.stop();
    }
  }
}
```

Ajouter au début de `app.component.html` la directive `ng2-sliloading-bar` :
```
<ng2-slim-loading-bar color="blue"></ng2-slim-loading-bar>
```

#### Créer un formulaire Bootstrap
Si vous avez un component d'ajout, dans `<name>.component.html` :
```
<div class="card">
  <div class="card-body">
    <form>
      <div class="form-group">
        <label class="col-md-4"> <Name> </label>
        <input type="text" class="form-control" />
      </div>
      <div class="form-group">
        <label class="col-md-4"> <Name> </label>
        <input type="text" class="form-control" />
      </div>
      <div class="form-group">
        <button type="submit" class="btn btn-primary">Add</button>
      </div>
    </form>
  </div>
</div>
```

#### Agular validation de fomulaire
Utilisation de `ReactiveFormsModule`
Ajouter dans `app.module.ts` :
```
import { ReactiveFormsModule } from '@angular/forms';

imports: [
    ...
    ReactiveFormsModule
],
```

Dans le comonent d'ajout `<component_name>.component.ts`:
```

import { Component, OnInit } from '@angular/core';
import { FormGroup,  FormBuilder,  Validators } from '@angular/forms';

@Component({
  selector: 'app-gst-add',
  templateUrl: './<component_name>.component.html',
  styleUrls: ['./<component_name>.component.css']
})
export class GstAddComponent implements OnInit {

  angForm: FormGroup;
  constructor(private fb: FormBuilder) {
    this.createForm();
  }

  createForm() {
    this.angForm = this.fb.group({
      <field_name>: ['', Validators.required ],
      <field_name>: ['', Validators.required ]
    });
  }

  ngOnInit() {
  }

}
```
Maintenant il faut ajouter les vérifications dans le fichier html `<component_name>.component.html`:
```
<div class="card">
  <div class="card-body">
    <form [formGroup]="angForm" novalidate>
      <div class="form-group">
        <label class="col-md-4"> <Name> </label>
        <input type="text" class="form-control" formControlName="<field_name>"  />
      </div>
      <div *ngIf="angForm.controls['<field_name>'].invalid && (angForm.controls['<field_name>'].dirty || angForm.controls['<field_name>'].touched)" class="alert alert-danger">
        <div *ngIf="angForm.controls['<field_name>'].errors.required">
          <field_name> is required.
        </div>
      </div>
      <div class="form-group">
        <label class="col-md-4"> <Name> </label>
        <input type="text" class="form-control" formControlName="<field_name>" />
      </div>
      <div *ngIf="angForm.controls['<field_name>'].invalid && (angForm.controls['<field_name>'].dirty || angForm.controls['<field_name>'].touched)" class="alert alert-danger">
        <div *ngIf="angForm.controls['<field_name>'].errors.required">
          <field_name> is required.
        </div>
      </div>
      <div class="form-group">
        <button type="submit" 
        [disabled]="angForm.pristine || angForm.invalid" 
        class="btn btn-primary">Add</button>
      </div>
    </form>
  </div>
</div>
```

#### Configurer le Client Http
Importer le module `HttpClientModule`dans `app.module.ts`
```
import { HttpClientModule } from '@angular/common/http';

imports: [
   ...
    HttpClientModule
 ],
```

#### Créer un Model
Créer un fichier `<model_name>.ts` dans `src>>app`:
```
export default class <Model_name> {
  <field>: <Type>;
  <field>: <Type>;
}
```

#### Créer service Angular
La commande suivant génère le fichier de service : `ng g service <service_name> --spec=false`

Importer le service dans `app.module.service`:
```
import { <service_class_name> } from './<service_name>.service';

providers: [ <service_class_name> ],
```

#### Envoie des données à API Node
Cet exemple est pour une base MongoDB
Dans le fichier `<service_name>.service.ts`ajouter:
```
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class <service_class_name> {

  uri = '<api_url>';

  constructor(private http: HttpClient) { }

  add<var>(<var>, <var>, ...) {
    const obj = {
      <field>: <var>,
      <field>: <var>,
      ...
    };
    console.log(obj);
    this.http.post(`${this.uri}/<action>`, obj)
        .subscribe(res => console.log('Done'));
  }
}
```

Ajout de l'évenement de click pour envoie du formulaire
Dans le fichier `<component_name>.component.html` :
```
<div class="form-group">
    <button (click)="addBusiness(<field>.value, <field>.value, ...)"
        [disabled]="angForm.pristine || angForm.invalid" 
        class="btn btn-primary">
        Add 
     </button>
</div>
```
Maintenant quand le formulaire sera rempli sans erreur il appellera la fonction component `add<var>`. Il faut donc  ajouter dans `<component_name>.component.ts` l'appelle de la fonction qui est dans `<service_name>.service.ts`:
```
import { Component, OnInit } from '@angular/core';
import { FormGroup,  FormBuilder,  Validators } from '@angular/forms';
import { <service_class_name> } from '../<service_name>.service';

@Component({
  selector: 'app-gst-add',
  templateUrl: './gst-add.component.html',
  styleUrls: ['./gst-add.component.css']
})
export class GstAddComponent implements OnInit {

  angForm: FormGroup;
  constructor(private fb: FormBuilder, private <p_var>: <service_classe_name>) {
    this.createForm();
  }

  createForm() {
    this.angForm = this.fb.group({
      <field>: ['', Validators.required ],
      <field>: ['', Validators.required ],
      ...
    });
  }

  addBusiness(<var>, <var>, ...) {
    this.<p_var>.addBusiness(<var>, <var>, ...);
  }

  ngOnInit() {
  }

}
```



