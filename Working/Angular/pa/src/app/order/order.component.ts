import { Component, OnInit} from '@angular/core';
import { Order } from '../order';
import { ORDER } from '../mock-order';
import { Dish } from '../dish';
import { OrderService } from '../order.service'


@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  orders : Dish[] ;

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.getOrder();
  }

  onDisselect(order: Dish): void {
    console.log(this.orders.indexOf(order));
    this.orders = this.orders.filter(o => o !== order);
    console.log(this.orders);
  }

  getOrder(): void {
    this.orderService.getOrder()
        .subscribe(orders => this.orders = ORDER.dishOrder);
  }

}
