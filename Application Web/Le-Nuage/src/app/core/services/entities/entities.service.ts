import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Cloud} from '../../models/Cloud';
import {map} from 'rxjs/operators';
import {Globals} from '../../globals/globals';
import {LocalStorageService} from '../localStorage/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class EntitiesService {

  constructor(private globals: Globals, private http: HttpClient, private localService: LocalStorageService) {
  }

  load(pageAfter: number, id: string, word: string) {
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'x-access-token': this.localService.get('currentUser')
    });
    if (word) {
      return this.http.get<Cloud[]>(this.globals.apiPath + 'tree/' + id, { headers, responseType: 'json' }).pipe(
        map((cloud: Cloud[]) => cloud.filter(w => w.name.toLowerCase().indexOf(word.toLowerCase()) >= 0))
      );
    }
    return this.http.get<Cloud[]>(this.globals.apiPath + 'tree/' + id, { headers, responseType: 'json' });
  }

  create(name: string, parentId: string, type: string) {
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'x-access-token': this.localService.get('currentUser')
    });
    const body = {name, parentId, type};
    console.log('nice');
    return this.http.post<Cloud>(this.globals.apiPath + 'entity', body, { headers, responseType: 'json' });
  }

}
