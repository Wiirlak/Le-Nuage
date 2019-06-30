import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { Cloud } from '../../models/Cloud';

@Injectable({
  providedIn: 'root'
})
export class CloudsService {
  clouddata = new Array();




  names = new Array('Jule', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon');
  constructor() { }
  load(actual, pageSize, filter) {

    for (let i = 0 ; i < 100 ; i++){
        this.clouddata.push(new Cloud(i,"https://zupimages.net/up/19/26/afo4.png","15/05/2018","Jule"));
    }
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
      const namess = new Array();
      const currentPos = actual * pageSize;
      let i;
      for (i = currentPos; i < currentPos + pageSize && i < this.clouddata.length; i ++) {
        if (filter.length > 0) {
            if (this.clouddata[i].name.toLowerCase().indexOf(filter.toLowerCase()) !== -1) {
              namess.push(this.clouddata[i]);
            }
        } else {
          namess.push(this.clouddata[i]);
        }
      }
      observer.next(namess);
    });
  }
}
