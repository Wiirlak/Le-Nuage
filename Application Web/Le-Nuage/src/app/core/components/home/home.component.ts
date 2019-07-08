import {Component, OnInit} from '@angular/core';
import { CloudsService} from '../../services/cloud/clouds.service';
import { Cloud } from '../../models/Cloud';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent{

  cloudstmp = new Array();
  loading = false;
  pageSize = 25;
  pageAfter = 1;
  search = '';

  constructor(private cloudsService: CloudsService, private sanitizer: DomSanitizer) { }

  onKey(searched) {
    this.cloudstmp = new Array();
    this.pageSize = 24;
    this.pageAfter = 1;
    this.search = searched.target.value;
    this.loadNext();
    console.log(this.cloudstmp);
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

  toGoodUrl(str) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(str);
  }


}
