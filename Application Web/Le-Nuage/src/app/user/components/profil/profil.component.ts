import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.css']
})
export class ProfilComponent implements OnInit {
  myForm: FormGroup;
  constructor(private formbuilder: FormBuilder, private router: Router) {}
  ngOnInit() {
    this.myForm = this.formbuilder.group({
      prenom: ['', Validators.required],
      nom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }
  onSubmit() {
    if (this.myForm.invalid) {
      return;
    }
    this.router.navigate(['/home']);
  }

}
