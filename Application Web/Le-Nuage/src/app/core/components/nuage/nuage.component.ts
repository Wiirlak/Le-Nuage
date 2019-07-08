import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Cloud} from '../../models/Cloud';
import {CloudsService} from '../../services/cloud/clouds.service';

@Component({
  selector: 'app-nuage',
  templateUrl: './nuage.component.html',
  styleUrls: ['./nuage.component.css']
})
export class NuageComponent {
  id: string;
  nuage: Cloud;

  constructor(private route: ActivatedRoute, private cloudsService: CloudsService) {
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id');
    });
    this.cloudsService.getOne(this.id).subscribe( param => {
      this.nuage = param;
    });
  }


}
