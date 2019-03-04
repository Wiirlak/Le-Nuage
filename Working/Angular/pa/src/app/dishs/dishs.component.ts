import { Component, OnInit } from '@angular/core';
import { DISHS } from '../mock-dishs';
import { Dish } from '../dish';
import { ORDER } from '../mock-order';

@Component({
  selector: 'app-dishs',
  templateUrl: './dishs.component.html',
  styleUrls: ['./dishs.component.css']
})
export class DishsComponent implements OnInit {
    dishs = DISHS;
    selectedDish: Dish;
  
    constructor() { }

  ngOnInit() {
  }

  onSelect(dish: Dish): void {
    this.selectedDish = dish;
    ORDER.dishOrder.push(dish);
  }

}
