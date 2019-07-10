import {EventEmitter, Injectable, Output} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RightbarService {

  isOpen = false;

  @Output() change: EventEmitter<boolean> = new EventEmitter();

  toggle() {
    if (!this.isOpen)
      this.isOpen = !this.isOpen;
    this.change.emit(this.isOpen);
  }
}
