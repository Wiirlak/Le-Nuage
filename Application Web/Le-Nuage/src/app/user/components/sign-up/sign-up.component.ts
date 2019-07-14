import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import {equalValueValidator} from '../../../core/shared/validator/equal-value-validator';
import {AuthentificationService} from '../../../admin/services/authentification/authentification.service';
import {formatDate} from '@angular/common';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  constructor(private formbuilder: FormBuilder, private router: Router, private authentificationService: AuthentificationService) {}

  myForm: FormGroup;
  ngOnInit() {
    this.myForm = this.formbuilder.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      birthdate: [{value: '', disabled: true }, Validators.required],
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

    this.authentificationService.signIn(
      this.myForm.get('lastname').value,
      this.myForm.get('firstname').value,
      formatDate(this.myForm.get('birthdate').value, 'yyyy-MM-dd', 'en-US', 'UTC+2'),
      this.myForm.get('email').value,
      this.myForm.get('password').value).subscribe(d => this.router.navigate(['/home']) );

  }

}
