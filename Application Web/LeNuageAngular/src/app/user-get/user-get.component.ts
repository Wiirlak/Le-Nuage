import { Component, OnInit } from '@angular/core';
import User from '../models/User';
import ApiResponse from '../models/ApiResponse';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-user-get',
  templateUrl: './user-get.component.html',
  styleUrls: ['./user-get.component.css']
})
export class UserGetComponent implements OnInit {

  users: User[];

  constructor(private us: UserService) { }

  deleteUser(id) {
      this.us.deleteUser(id).subscribe(res => {
          console.log('Delete');
      });
  }

  ngOnInit() {
    this.us
        .getUsers()
        .subscribe((data: ApiResponse) => {
          this.users = data.data;
          console.log(data);
        });
  }

}
