import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {LocalStorageService} from '../../../core/services/localStorage/local-storage.service';
import { User } from '../../../core/models/User';

@Injectable({
  providedIn: 'root'
})

export class AuthentificationService {
  currentUser: User;

  constructor(private http: HttpClient, private localService: LocalStorageService) { }
  isUserAuthenticated(username: string, password: string): Observable<boolean> {
    return new Observable((observer) => {
      observer.next(false);
    });
    let isLoggedIn = false;
    if (username === 'admin' && password === 'admin') {
      this.currentUser = {
        id: 1,
        lastname : 'admin',
        firstname: 'admin',
        email: 'admin.admin@admin.admin'
      };
      this.localService.set('currentUser', this.currentUser.toString());
      isLoggedIn = true;
    } else {
      this.localService.remove('currentUser');
    }
    return new Observable((observer) => {
      observer.next(isLoggedIn);
    });
  }
  isUserAuthenticatedrest() {
    return this.http.get('http://localhost:3000/users');
  }
}
