import {Component, HostListener, OnInit} from '@angular/core';
import { CloudsService} from '../../services/cloud/clouds.service';
import { Cloud } from '../../models/Cloud';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import {RightbarService} from '../../services/rightbar/rightbar.service';
import {RightbarUpdateService} from '../../services/rightbar/rightbar-update.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent{

  cloudstmp = [];
  loading = false;
  pageSize = 25;
  pageAfter = 1;
  selected: string;
  search = '';

  constructor(private cloudsService: CloudsService,
              private sanitizer: DomSanitizer,
              private rightbarService: RightbarService,
              private rightbarUpdateService: RightbarUpdateService) {
  }

  onKey(searched) {
    this.cloudstmp = [];
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

  showRight(nuage: object) {
    console.log(nuage._id + ' | ' + this.selected);
    if (!(this.selected === nuage._id)) {
      this.selected = nuage._id;
      this.rightbarService.toggle();
    }
    this.rightbarUpdateService.change(nuage.name, '', nuage._id, '', '');
  }


}
