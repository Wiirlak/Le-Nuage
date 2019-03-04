import { Component } from '@angular/core';
import { Order } from './order';
import { HttpClientModule  } from '@angular/common/http';
import { ApiAppleService } from './api-apple.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'pa';
  apples = null;
  apple = null;
  
  constructor(private _ApiAppleService: ApiAppleService){
  
  }

  ngOnInit() {
    //this.getApples();
    //this.getApple('5c45f7c51d5463541812ddf4');
    //this.createApple("pokemon",50);
    //this.deleteApple('5c58188a0bbc7a1b444e9d9e');
    this.updateApple('5c5819ea0bbc7a1b444e9d9f', 'peperoni',55555);
  }

  getApples(){
     this._ApiAppleService.getApples().subscribe(
      data => { this.apples = data},
      err => console.error(err),
      () => console.log('done loading foods')
    );
  }

  getApple(id){
    this._ApiAppleService.getApple(id).subscribe(
     data => { this.apple = data},
     err => console.error(err),
     () => console.log('done loading foods')
   );
  }

  createApple(name,pepins){
    let apple = {name: name, pepins: pepins};
    this._ApiAppleService.createApple(apple).subscribe(
      data => { this.apple = data},
      err => console.error(err),
      () => console.log('done loading foods')
    );
  }

  deleteApple(id){
    this._ApiAppleService.deleteApple(id).subscribe(
     data => { this.apple = data},
     err => console.error(err),
     () => console.log('done loading foods')
   );
  }

  updateApple(id,name,pepins){
    let apple = {name: name, pepins: pepins};
    this._ApiAppleService.updateApple(apple,id).subscribe(
      data => { this.apple = data},
      err => console.error(err),
      () => console.log('done loading foods')
    );
  }

  
}
