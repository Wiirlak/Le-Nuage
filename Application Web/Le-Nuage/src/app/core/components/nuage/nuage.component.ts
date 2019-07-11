import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Cloud} from '../../models/Cloud';
import {CloudsService} from '../../services/cloud/clouds.service';
import {FileSystemDirectoryEntry, FileSystemFileEntry, NgxFileDropEntry} from 'ngx-file-drop';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../../globals/globals';
import {EntitiesService} from '../../services/entities/entities.service';
import {RightbarService} from '../../services/rightbar/rightbar.service';
import {RightbarUpdateService} from '../../services/rightbar/rightbar-update.service';

@Component({
  selector: 'app-nuage',
  templateUrl: './nuage.component.html',
  styleUrls: ['./nuage.component.css', '../home/home.component.css']
})
export class NuageComponent {
  id: string;
  parentid: string;
  nuage: Cloud;
  entitiestmp = new Array();
  loading = false;
  done = false;
  pageSize = 25;
  pageAfter = 1;
  search = '';
  history: any;
  version: any;
  selected: string;
  imgFolder = 'https://cdn.discordapp.com/attachments/468709911321247764/598273310924734475/unnamed.png';
  imgFile = 'http://www.pngall.com/wp-content/uploads/2018/05/Files-High-Quality-PNG.png';
  public files: NgxFileDropEntry[] = [];

  constructor(private route: ActivatedRoute,
              private cloudsService: CloudsService,
              private entitiesService: EntitiesService,
              private http: HttpClient,
              private globals: Globals,
              private rightbarService: RightbarService,
              private rightbarUpdateService: RightbarUpdateService) {
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id');
      this.parentid = params.get('parentid');

      this.cloudsService.getOne(this.id).subscribe( param => {
        this.nuage = param;
        this.done = true;
        this.entitiesService.load(this.pageAfter, this.nuage.parentEntity, this.search)
          .subscribe(entity => {
            this.entitiestmp.push(...entity);
            console.log(this.entitiestmp);
            this.loading = false;
            this.pageAfter++;
          });
      });
    });
  }

  async showRight(entity: any) {
    if (entity.type.name === 'file') {
      console.log(entity);
      if (!(this.selected === entity._id)) {
        this.selected = entity._id;
        this.rightbarService.toggle();
        this.history = await this.entitiesService.history(entity.parent, entity.name, 5);
        this.version = await this.entitiesService.version(entity.parent, entity.name, 10);
        this.rightbarUpdateService.change(entity.name, entity.size, entity._id, '', this.history, this.version);
      }
    }
  }

  onKey(searched) {
    this.entitiestmp = new Array();
    this.pageSize = 24;
    this.pageAfter = 1;
    this.search = searched.target.value;
    this.loadNext();
  }

  loadNext() {
    if (this.loading) { return; }
    this.loading = true;
    if (this.done) {
      this.entitiesService.load(this.pageAfter, this.nuage.parentEntity, this.search)
        .subscribe(entity => {
          this.entitiestmp.push(...entity);
          this.loading = false;
          this.pageAfter++;
        });
    } else {
      return;
    }
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
          const formData = new FormData();
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
