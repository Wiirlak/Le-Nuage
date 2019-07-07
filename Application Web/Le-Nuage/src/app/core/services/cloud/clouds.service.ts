import {Injectable, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import { Cloud } from '../../models/Cloud';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../../globals/globals';
import {LocalStorageService} from '../localStorage/local-storage.service';
import {filter, flatMap, map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CloudsService {
  constructor(private globals: Globals, private http: HttpClient, private localService: LocalStorageService) {
  }

  load(pageAfter: number, pageSize: number, word: string) {
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'x-access-token': this.localService.get('currentUser')
    });
    if (word) {
      return this.http.get<Cloud[]>(this.globals.apiPath + 'nuage?page=' + pageAfter, { headers, responseType: 'json' }).pipe(
        map((cloud: Cloud[]) => cloud.filter(w => w.name.toLowerCase().indexOf(word.toLowerCase()) >= 0))
      );
    }
    return this.http.get<Cloud[]>(this.globals.apiPath + 'nuage?page=' + pageAfter, { headers, responseType: 'json' });
  }

}
