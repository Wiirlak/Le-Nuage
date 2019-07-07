import {Component, OnInit} from '@angular/core';
import { CloudsService} from '../../services/cloud/clouds.service';
import { Cloud } from '../../models/Cloud';
import { NbSearchService } from '@nebular/theme';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry } from 'ngx-file-drop';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../../globals/globals';
import {map} from 'rxjs/operators';

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

  public files: NgxFileDropEntry[] = [];
  constructor(private cloudsService: CloudsService, private sanitizer: DomSanitizer, private http: HttpClient, private globals: Globals) { }

  ngOnInit() {
  }

  onKey(searched) {
    this.cloudstmp = new Array();
    console.log(searched.target.value);
    this.pageSize = 24;
    this.pageAfter = 0;
    this.search = searched.target.value;
    this.loadNext();
    console.log(this.cloudstmp);
  }

  loadNext() {
    if (this.loading) { return; }
    this.loading = true;
    this.cloudsService.load(this.pageAfter, this.pageSize, this.search)
      .subscribe(clouds => {
        console.log('1');
        this.cloudstmp.push(...clouds);
        console.log(clouds);
        this.loading = false;
        this.pageAfter ++;
      });

  }

  toGoodUrl(str) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(str);
  }


  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {

      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {

          // Here you can access the real file
          console.log(droppedFile.relativePath, file);

           // You could upload it like this:
          const formData = new FormData()
          formData.append('somefile', file, droppedFile.relativePath);
          formData.append('parentId', '5d0f766742038438d41f5c5c');

           // Headers
          const headers = new HttpHeaders({
            'x-access-token': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVkMGY3NjY3NDIwMzg0MzhkNDFmNWM1ZCIsImlhdCI6MTU2MTkwNzg0OSwiZXhwIjoxNTYxOTA5MDQ5fQ.KGGWu-dHpDi9uqaa0ZsJKQr3BEyfANPdsgIQmYoebfY'
          })

          this.http.post(this.globals.apiPath + 'entity/upload', formData, { headers: headers, responseType: 'blob' })
           .subscribe(data => {
            // Sanitized logo returned from backend
             console.log('gg');
          });
        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event) {
    console.log('ee' + event);
  }

  public fileLeave(event) {
    console.log('pd' + event);
  }

}
