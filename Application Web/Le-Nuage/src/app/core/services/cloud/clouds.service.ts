import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { Cloud } from '../../models/Cloud';

@Injectable({
  providedIn: 'root'
})
export class CloudsService {
  clouddata = new Array();

  constructor() {
    for (let i = 0 ; i < 120 ; i++) {
      this.clouddata.push(new Cloud(i,"https://zupimages.net/up/19/26/afo4.png","15/05/2018","J"+i+"ulie"));
    }
    this.clouddata.sort(() => {
      return .5 - Math.random();
    });
  }
  load(actual, pageSize, filter) {
    return new Observable<Array<Cloud>>((observer) => {
      /*const namess = new Array();
      const currentPos = actual * pageSize;
      let i;
      for (i = currentPos; i < currentPos + pageSize && i < this.names.length; i ++) {
        if (filter.length > 0) {
          if (this.names[i].toLowerCase().indexOf(filter.toLowerCase()) !== -1) {
            namess.push(this.names[i]);
          }
        } else {
          namess.push(this.names[i]);
        }
      }
      observer.next(namess);*/
      const clouds = new Array();
      const currentPos = actual * pageSize;
      let i;
      for (i = currentPos; i < currentPos + pageSize && i < this.clouddata.length; i ++) {
        if (filter.length > 0) {
            if (this.clouddata[i].name.toLowerCase().indexOf(filter.toLowerCase()) !== -1) {
              clouds.push(this.clouddata[i]);
            }
        } else {
          clouds.push(this.clouddata[i]);
        }
      }
      observer.next(clouds);
    });
  }
}
