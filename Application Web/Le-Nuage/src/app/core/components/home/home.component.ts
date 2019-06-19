import { Component, OnInit } from '@angular/core';
import { CloudsService} from '../../services/clouds.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  clouds = new Array();
  loading = false;
  pageSize = 25;
  pageAfter = 0;
  constructor(private cloudsService: CloudsService) { }

  ngOnInit() {
  }

  loadNext() {
    if (this.loading) { return; }
    this.loading = true;
    this.cloudsService.load(this.pageAfter, this.pageSize)
      .subscribe(clouds => {
        this.clouds.push(...clouds);
        this.loading = false;
        this.pageAfter ++;
      });
  }
}
