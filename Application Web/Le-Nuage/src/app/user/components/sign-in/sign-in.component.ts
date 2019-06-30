import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthentificationService} from '../../../admin/services/authentification/authentification.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  myForm: FormGroup;
  constructor(private formbuilder: FormBuilder, private router: Router, private authentificationService: AuthentificationService) {}
  ngOnInit() {
    this.myForm = this.formbuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }
  onSubmit() {
    if (this.myForm.invalid) {
      return;
    }
    this.authentificationService.loggingIn(this.myForm.get('email').value, this.myForm.get('password').value);
    this.router.navigate(['/home']);
  }

}
