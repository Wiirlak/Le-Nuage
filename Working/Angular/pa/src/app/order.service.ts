import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { Order } from './order';
import { ORDER } from './mock-order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor() { }

  getOrder(): Observable<Order> {
    return of(ORDER);
  }
}
