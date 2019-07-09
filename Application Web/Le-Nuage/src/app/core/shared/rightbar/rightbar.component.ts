import {Component, OnInit} from '@angular/core';
import {RightbarUpdateService} from '../../services/rightbar/rightbar-update.service';

@Component({
  selector: 'app-rightbar',
  templateUrl: './rightbar.component.html',
  styleUrls: ['./rightbar.component.css']
})
export class RightbarComponent implements OnInit {
  name: string;
  size: any;
  id: string;
  shared: any;
  history: any;
  users = ['Mathieu Lepre', 'Jean Nisole', 'Jeanne d\'Arc'];
  activity = [
    ['Mathieu Lepre', '25/02/19', '11:50'],
    ['Jeanne d\'Arc', '15/02/19', '15:36'],
    ['Jean Nisole', '10/02/19', '22:52'],
  ];

  constructor(private rightbarServicesUpdate: RightbarUpdateService) {
  }

  ngOnInit(): void {
    this.rightbarServicesUpdate.currentname.subscribe(name => this.name = name);
    this.rightbarServicesUpdate.currentsize.subscribe(size => this.size = size);
    this.rightbarServicesUpdate.currentid.subscribe(id => this.id = id);
    this.rightbarServicesUpdate.currentshared.subscribe(shared => this.shared = shared);
    this.rightbarServicesUpdate.currenthistory.subscribe(history => this.history = history);
  }

}
