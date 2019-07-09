import {Component, Injectable, Input, Output, EventEmitter} from '@angular/core';
import {BehaviorSubject} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RightbarUpdateService {
  private name = new BehaviorSubject('default');
  private size = new BehaviorSubject('');
  private id = new BehaviorSubject('default');
  private shared = new BehaviorSubject([]);
  private history = new BehaviorSubject([]);
  currentname = this.name.asObservable();
  currentsize = this.size.asObservable();
  currentid = this.id.asObservable();
  currentshared = this.shared.asObservable();
  currenthistory = this.history.asObservable();

  constructor() {
    console.log('shared service started');
  }

  change(name: string, size: any, id: string, shared: any, history: any) {
    this.name.next(name);
    this.size.next(size);
    this.id.next(id);
    this.shared.next(shared);
    this.history.next(history);
  }

  changeName(name: string) {
    this.name.next(name);
  }

}
