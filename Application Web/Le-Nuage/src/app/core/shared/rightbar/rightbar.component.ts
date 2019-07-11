import {Component, OnInit} from '@angular/core';
import {RightbarUpdateService} from '../../services/rightbar/rightbar-update.service';
import {formatDate} from '@angular/common';
import {EntitiesService} from '../../services/entities/entities.service';
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-rightbar',
  templateUrl: './rightbar.component.html',
  styleUrls: ['./rightbar.component.css']
})
export class RightbarComponent implements OnInit {
  name: string;
  size: any;
  sizeU: string;
  id: string;
  history: any;
  versions: any;

  constructor(private rightbarServicesUpdate: RightbarUpdateService, private entitiService: EntitiesService) {
  }

  ngOnInit(): void {
    this.rightbarServicesUpdate.currentname.subscribe(name => this.name = name);
    this.rightbarServicesUpdate.currentsize.subscribe(size => {
      this.sizeU = ' o';
      this.size = parseInt(size, 10);
      if (parseInt(size, 10) > 1024) {
        this.sizeU = ' Ko';
        this.size = parseFloat(size) / 1024;
        if (parseInt(size, 10) > 1024 * 1024) {
          this.sizeU = ' Mo';
          this.size = parseFloat(size) / (1024 * 1024);
          if (parseInt(size, 10) > 1024 * 1024 * 1024) {
            this.sizeU = ' Go';
            this.size = parseFloat(size) / (1024 * 1024 * 1024);
          }
        }
      }
      this.size = this.size.toFixed(2);
    });
    this.rightbarServicesUpdate.currentid.subscribe(id => this.id = id);
    this.rightbarServicesUpdate.currenthistory.subscribe(history => this.history = history);
    this.rightbarServicesUpdate.currentversions.subscribe(versions => this.versions = versions);
  }

  formated(date: string) {
    return formatDate(date, 'dd-MM-yy HH:mm', 'en-US', 'UTC+2');
  }
  printSize(sizeD: any) {
    let sizeU = ' o';
    sizeD = parseInt(sizeD, 10);
    if (parseInt(sizeD, 10) > 1024) {
      sizeU = ' Ko';
      sizeD = parseFloat(sizeD) / 1024;
      if (parseInt(sizeD, 10) > 1024 * 1024) {
        sizeU = ' Mo';
        sizeD = parseFloat(sizeD) / (1024 * 1024);
        if (parseInt(sizeD, 10) > 1024 * 1024 * 1024) {
          sizeU = ' Go';
          sizeD = parseFloat(sizeD) / (1024 * 1024 * 1024);
        }
      }
    }
    return sizeD.toFixed(2) + sizeU;
  }


  cuteName(s: string) {
    return s.toUpperCase()[0] + '.';
  }

  printData(id) {
    console.log(id);
  }

  download(id: string, name: string) {
    this.entitiService.downloadReport(id).subscribe(
      data => {
        saveAs(data, name);
      },
      err => {
        alert('Problem while downloading the f ile.');
        console.error(err);
      }
    );
  }
}
