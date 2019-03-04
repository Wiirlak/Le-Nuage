import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  uri = 'http://localhost:3000/api';

  constructor(private http: HttpClient) { }

  addUser(user_name, user_email, user_pwd) {
    const obj = {
      name: user_name,
      email: user_email,
      password: user_pwd
    };
    console.log(obj);
    this.http.post(`${this.uri}/user`, obj)
        .subscribe(res => console.log('Done'));
  }

  getUsers() {
    return this
        .http
        .get(`${this.uri}/user`);
  }

  editUser(id) {
    console.log(id);
    return this
        .http
        .get(`${this.uri}/user/${id}`);
  }

  updateUser(user_name, user_email, user_password, id) {
    const obj = {
      name: user_name,
      email: user_email,
      password: user_password
    };
    this
        .http
        .put(`${this.uri}/user/${id}`, obj)
        .subscribe(res => console.log('Done'));
  }

  deleteUser(id) {
    return this
        .http
        .delete(`${this.uri}/user/${id}`);
  }
}
