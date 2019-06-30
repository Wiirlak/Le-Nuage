import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {
  subject = new BehaviorSubject<string>(null);

  watch(): Observable<string> {
    return this.subject;
  }

  get(key: string) {
    return localStorage.getItem(key);
  }

  test(key: string) {
    if (localStorage.getItem(key)) {
      return true;
    }
    return false;
  }

  set(key: string, value: string) {
    localStorage.setItem(key, value);
    this.subject.next(key);
  }

  remove(key: string) {
    localStorage.removeItem(key);
    this.subject.next(null);
  }
}
