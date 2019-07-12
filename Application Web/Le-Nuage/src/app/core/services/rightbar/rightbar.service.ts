import {EventEmitter, Injectable, Output} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RightbarService {

  isOpen = false;
  force = false;

  @Output() change: EventEmitter<boolean> = new EventEmitter();

  toggle() {
    if (!this.isOpen)
      this.isOpen = !this.isOpen;
    this.change.emit(this.isOpen);
  }

  hide() {
    this.isOpen = false;
    this.change.emit(this.force);
  }
}
