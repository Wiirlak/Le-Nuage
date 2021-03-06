import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Cloud} from '../../models/Cloud';
import {map} from 'rxjs/operators';
import {Globals} from '../../globals/globals';
import {LocalStorageService} from '../localStorage/local-storage.service';
import {Observable} from 'rxjs';

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
      return this.http.get<Cloud[]>(this.globals.apiPath + 'entity/all?parentid=' + id, { headers, responseType: 'json' }).pipe(
        map((cloud: Cloud[]) => cloud.filter(w => w.name.toLowerCase().indexOf(word.toLowerCase()) >= 0))
      );
    }
    return this.http.get<Cloud[]>(this.globals.apiPath + 'entity/all?parentid=' + id, { headers, responseType: 'json' });
  }

  create(name: string, parentId: string, type: string) {
    const headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'x-access-token': this.localService.get('currentUser')
    });
    const body = {name, parentId, type};

    return this.http.post<Cloud>(this.globals.apiPath + 'entity', body, { headers, responseType: 'json' });
  }

  history(parentId: string, name: string, limit: number) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'x-access-token': this.localService.get('currentUser')
    });
    return this.http.get<Cloud[]>(
      this.globals.apiPath + 'historyfile/?' +
        'parentid=' + parentId + '' +
        '&name=' +  name.replace(' ', '%20') + '' +
        '&limit=' + limit, {headers, responseType: 'json'}).toPromise();
  }


  version(parentId: string, name: string, limit: number) {
    const headers = new HttpHeaders({
      'x-access-token': this.localService.get('currentUser')
    });
    return this.http.get<Cloud[]>(
      this.globals.apiPath + 'entity/version?' +
      'parentid=' + parentId + '' +
      '&name=' +  name.replace(' ', '%20') + '' +
      '&limit=' + limit,
      {headers, responseType: 'json'}).toPromise();
  }
  downloadReport(id): Observable<any> {
    // Create url
    const headers = new HttpHeaders({
      'x-access-token': this.localService.get('currentUser')
    });
    return this.http.get<Observable<any>>(
      this.globals.apiPath + 'entity/download?e=' + id,
      {headers, responseType: 'blob' as 'json'});
  }

  delete(parentId: string, name: string) {
    const headers = new HttpHeaders({
      'x-access-token': this.localService.get('currentUser')
    });
    return this.http.delete<Observable<any>>(
      this.globals.apiPath + 'entity/remove?parentid=' + parentId + '&name=' + name,
      {headers, responseType: 'json'}).toPromise();
  }
}
