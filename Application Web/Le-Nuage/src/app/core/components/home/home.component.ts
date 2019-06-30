import {Component, OnInit} from '@angular/core';
import { CloudsService} from '../../services/cloud/clouds.service';
import { Cloud } from '../../models/Cloud';
import { NbSearchService } from '@nebular/theme';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  cloudstmp = new Array();
  loading = false;
  pageSize = 25;
  pageAfter = 0;
  search = '';
  constructor(private cloudsService: CloudsService,private sanitizer: DomSanitizer) { }

  ngOnInit() {
  }

  onKey(searched) {
    this.cloudstmp = new Array();
    console.log(searched.target.value);
    this.pageSize = 24;
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
    /*for(let i = 0 ; i < 100 ; i ++)
      console.log(this.cloudstmp[i])*/
  }

  toGoodUrl(str){
    return this.sanitizer.bypassSecurityTrustResourceUrl(str);
  }
}
