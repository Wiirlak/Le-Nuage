import {Component, Inject, ViewChild} from '@angular/core';
import {CloudsService} from '../../services/cloud/clouds.service';
import {RightbarService} from '../../services/rightbar/rightbar.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatMenuTrigger} from '@angular/material';
import {DialogData} from '../../shared/navbar/navbar.component';


export interface DialogData {
  name: string;
  text: string;
  where: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  cloudstmp = [];
  loading = false;
  pageSize = 25;
  pageAfter = 1;
  search = '';
  name: string;
  where: string;
  text: string;
  current: any;
  contextMenuPosition = {x: '0px', y: '0px'};
  // @ts-ignore
  @ViewChild(MatMenuTrigger)
  contextMenu: MatMenuTrigger;

  constructor(private cloudsService: CloudsService,
              private rightbarService: RightbarService,
              private dialog: MatDialog) {
  }

  onKey(searched) {
    this.cloudstmp = [];
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
    this.cloudsService.load(this.pageAfter, this.pageSize, this.search)
      .subscribe(clouds => {
        this.cloudstmp.push(...clouds);
        this.loading = false;
        this.pageAfter++;
      });

  }

  reload() {
    this.pageAfter = 1;
    this.cloudsService.load(this.pageAfter, this.pageSize, this.search)
      .subscribe(clouds => {
        this.cloudstmp = [];
        this.cloudstmp.push(...clouds);
      });
  }

  onContextMenu($event: MouseEvent, item: any) {
    $event.preventDefault();
    this.contextMenuPosition.x = $event.clientX + 'px';
    this.contextMenuPosition.y = $event.clientY + 'px';
    this.contextMenu.menuData = {item};
    this.current = item;
    this.contextMenu.openMenu();
  }

  openDialog(where: string, text: string): void {
    this.where = where;
    this.text = text;
    const dial = this.dialog.open(HomeDialogComponent, {
      width: '28,3vw',
      data: {name: this.name, text: this.text, where: this.where}
    });
    dial.afterClosed().subscribe(result => {
      if (where === 'delete') {

        this.cloudsService.delete(this.current._id).subscribe(() => {
          this.reload();
        });
      } else if (where === 'rename') {

        this.cloudsService.rename(this.current._id, result).subscribe(() => {
          this.reload();
        });
      } else if (where === 'share') {

        this.cloudsService.addUser(this.current._id, result).subscribe(() => {
          this.reload();
        });
      }
    });
  }
}


@Component({
  selector: 'app-home-dialog',
  templateUrl: 'home-dialog.component.html',
})
export class HomeDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<HomeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
