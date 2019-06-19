import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CloudsService {
  names = new Array('Jule', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon', 'Pier', 'Andrey', 'Mod', 'Papi', 'llon');
  constructor() { }
  load(actual, pageSize) {
    return new Observable<Array<string>>((observer) => {
      const namess = new Array();
      const currentPos = actual * pageSize;
      let i;
      for (i = currentPos; i < currentPos + pageSize && i < this.names.length; i ++) {
        namess.push(this.names[i]);
      }
      observer.next(namess);
    });
  }
}
