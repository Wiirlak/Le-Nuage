import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Cloud} from '../../models/Cloud';
import {CloudsService} from '../../services/cloud/clouds.service';
import {FileSystemDirectoryEntry, FileSystemFileEntry, NgxFileDropEntry} from 'ngx-file-drop';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Globals} from '../../globals/globals';
import {EntitiesService} from '../../services/entities/entities.service';
import {RightbarService} from '../../services/rightbar/rightbar.service';
import {RightbarUpdateService} from '../../services/rightbar/rightbar-update.service';
import {appRoutingModuleContent} from '@nebular/theme/schematics/ng-add/register-modules/app-routing-module-content';
import {LocalStorageService} from '../../services/localStorage/local-storage.service';

@Component({
  selector: 'app-nuage',
  templateUrl: './nuage.component.html',
  styleUrls: ['./nuage.component.css', '../home/home.component.css']
})
export class NuageComponent {
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
  inline = 7;
  imgFolder = 'https://cdn.discordapp.com/attachments/468709911321247764/598273310924734475/unnamed.png';
  imgFile = 'http://www.pngall.com/wp-content/uploads/2018/05/Files-High-Quality-PNG.png';
  public files: NgxFileDropEntry[] = [];

  constructor(private route: ActivatedRoute,
              private cloudsService: CloudsService,
              private entitiesService: EntitiesService,
              private http: HttpClient,
              private router: Router,
              private globals: Globals,
              private localService: LocalStorageService,
              private rightbarService: RightbarService,
              private rightbarUpdateService: RightbarUpdateService) {
    this.route.paramMap.subscribe(params => {
      this.parentid = params.get('parentid');
      console.log(this.parentid);
      this.entitiesService.load(this.pageAfter, this.parentid, this.search)
        .subscribe(entity => {
          this.done = true;
          this.entitiestmp.push(...entity);
          console.log(this.entitiestmp);
          this.loading = false;
          this.pageAfter++;
        });
    });
  }

  async showRight(entity: any) {
    if (entity.type.name === 'file') {
      console.log(entity);
      if (!(this.selected === entity._id)) {
        this.inline = 6;
        this.selected = entity._id;
        this.rightbarService.toggle();
        this.history = await this.entitiesService.history(entity.parent, entity.name, 7);
        this.version = await this.entitiesService.version(entity.parent, entity.name, 10);
        this.rightbarUpdateService.change(entity.name, entity.size, entity._id, this.history, this.version);
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
    if (this.loading) {
      return;
    }
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
          formData.append('parentId', this.parentid);

          // Headers
          const headers = new HttpHeaders({
            'x-access-token': this.localService.get('currentUser')
          });

          this.http.post(this.globals.apiPath + 'entity/upload', formData, {headers: headers, responseType: 'blob'})
            .subscribe(data => {
              // Sanitized logo returned from backend
              console.log('gg');
              this.reload();
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

  navigateToNewFolder(entity: any) {
    // [href]="ent.type.name == 'folder' ? globals.apiPath+'nuage/'+ent._id : [] "
    if (entity.type.name  === 'folder' ) {
      this.router.navigateByUrl( 'nuage/' + entity._id).then( t => {
        this.reload();
        });
    }
  }

  reload() {
    this.pageAfter = 1;
    this.entitiesService.load(this.pageAfter, this.parentid, this.search)
      .subscribe(entity => {
        this.entitiestmp = new Array();
        this.entitiestmp.push(...entity);
      });
  }
}
