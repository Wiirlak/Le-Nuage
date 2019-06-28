import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rightbar',
  templateUrl: './rightbar.component.html',
  styleUrls: ['./rightbar.component.css']
})
export class RightbarComponent implements OnInit {
  users = ['Mathieu Lepre', 'Jean Nisole', 'Jeanne d\'Arc'];
  activity = [
    ['Mathieu Lepre', '25/02/19', '11:50'],
    ['Jeanne d\'Arc', '15/02/19', '15:36'],
    ['Jean Nisole', '10/02/19', '22:52'],
  ]

  constructor() { }

  ngOnInit() {
  }

}
