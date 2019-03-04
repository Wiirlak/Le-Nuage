import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import ApiResponse from '../models/ApiResponse';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  user: any = {};
  angForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private us: UserService,
              private fb: FormBuilder) {
    this.createForm();
  }

  createForm() {
    this.angForm = this.fb.group({
      user_name: ['', Validators.required ],
      user_email: ['', Validators.required ],
      user_pwd: ['', Validators.required ]
    });
  }

  updateUser(user_name, user_email, user_password) {
    this.route.params.subscribe(params => {
      this.us.updateUser(user_name, user_email, user_password, params['id']);
      this.router.navigate(['user']);
    });
  }


  ngOnInit() {
    this.route.params.subscribe(params => {
      this.us.editUser(params['id']).subscribe((res: ApiResponse) => {
        this.user = res.data;
      });
    });
  }

}
