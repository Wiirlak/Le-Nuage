import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class AuthentificationService {
  constructor(private http: HttpClient) { }
  isUserAuthentificated() {
    return new Observable<boolean>((observer) => {
      observer.next(true);
    });
  }
  isUserAuthenticatedrest() {
    return this.http.get('http://localhost:3000/users');
  }
}
