import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': 'http://localhost:3000/pommes',
    'Access-Control-Allow-Credentials':'true'
   })
}

@Injectable({
  providedIn : 'root'
})


export class ApiAppleService {

  constructor(private http:HttpClient) { 

  }

  getApples(){
    return this.http.get('http://localhost:3000/pommes')
  }

  getApple(id){
    return this.http.get('http://localhost:3000/pommes/'+id)
  }

  createApple(apple){
    let body = JSON.stringify(apple);
    return this.http.post('http://localhost:3000/pommes/new',body,httpOptions);
  }

  deleteApple(id){
    return this.http.delete('http://localhost:3000/pommes/'+id)
  }

  updateApple(apple,id){
    let body = JSON.stringify(apple);
    return this.http.put('http://localhost:3000/pommes/'+id,body,httpOptions);
  }
}
