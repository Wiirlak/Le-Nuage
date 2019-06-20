import {Component, OnInit} from '@angular/core';
import { CloudsService} from '../../services/clouds.service';
import { Cloud } from '../../models/Cloud';
import { NbSearchService } from '@nebular/theme';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  cloudstmp = new Array();
  clouds: Cloud[];
  loading = false;
  pageSize = 25;
  pageAfter = 0;
  search = '';
  constructor(private cloudsService: CloudsService) { }

  ngOnInit() {
  }

  onKey(searched) {
    this.cloudstmp = new Array();
    console.log(searched.target.value);
    this.pageSize = 25;
    this.pageAfter = 0;
    this.search = searched.target.value;
    this.loadNext();
  }

  loadNext() {
    if (this.loading) { return; }
    this.loading = true;
    this.cloudsService.load(this.pageAfter, this.pageSize, this.search)
      .subscribe(clouds => {
        this.cloudstmp.push(...clouds);
        this.loading = false;
        this.pageAfter ++;
      });
  }
}
