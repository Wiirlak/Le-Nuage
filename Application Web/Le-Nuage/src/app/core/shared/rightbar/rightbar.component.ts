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
  sizeU: string;
  id: string;
  shared: any;
  history: any;
  versions: any;
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
    this.rightbarServicesUpdate.currentsize.subscribe(size => {
      this.sizeU = ' o';
      this.size = parseInt(size, 10);
      if (parseInt(size, 10) > 1024) {
        this.sizeU = ' Ko';
        this.size = parseFloat(size) / 1024;
        if (parseInt(size, 10) > 1024 * 1024) {
          this.sizeU = ' Mo';
          this.size = parseFloat(size) / (1024 * 1024);
          if (parseInt(size, 10) > 1024 * 1024 * 1024) {
            this.sizeU = ' Go';
            this.size = parseFloat(size) / (1024 * 1024 * 1024);
          }
        }
      }
      this.size = this.size.toFixed(2);
    });
    this.rightbarServicesUpdate.currentid.subscribe(id => this.id = id);
    this.rightbarServicesUpdate.currentshared.subscribe(shared => this.shared = shared);
    this.rightbarServicesUpdate.currenthistory.subscribe(history => this.history = history);
    this.rightbarServicesUpdate.currentversions.subscribe(versions => this.versions = versions);
  }

}
