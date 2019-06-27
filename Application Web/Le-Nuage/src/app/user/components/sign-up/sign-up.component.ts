import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import {equalValueValidator} from "../../../core/shared/validator/equal-value-validator";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  myForm: FormGroup;
  constructor(private formbuilder: FormBuilder, private router: Router) {}
  ngOnInit() {
    this.myForm = this.formbuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      password2: ['', [Validators.required, Validators.minLength(6)]]
    },
      {validator: equalValueValidator('password', 'password2')}
    );
  }
  onSubmit() {
    if (this.myForm.invalid) {
      return;
    }
    this.router.navigate(['/home']);
  }


}
