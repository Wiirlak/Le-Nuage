import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LocalStorageService} from '../../../core/services/localStorage/local-storage.service';
import { User } from '../../../core/models/User';
import {Globals} from '../../../core/globals/globals';

@Injectable({
  providedIn: 'root'
})

export class AuthentificationService {
  currentUser: User;
  auth = false;

  constructor(private http: HttpClient, private localService: LocalStorageService, private globals: Globals) { }
  isAuthenticated() {
    if (this.localService.get('currentUser')) {
      return true;
    }
    return false;
  }

  loggingIn(email: string, password: string): Observable<boolean> {
    let tmp = false;
    // You could upload it like this:
    const body = {email, password};

    // Headers
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
    });

    this.http.post(this.globals.apiPath + 'auth/login', body, { headers: headers, responseType: 'json' })
      .subscribe(data => {
        // @ts-ignore
        this.localService.set('currentUser', data.token);
        tmp = true;
      });
    return new Observable((observer) => { observer.next(tmp); });
  }
  loggingOut() {
    this.localService.remove('currentUser');
  }
}
