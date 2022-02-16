import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Passenger } from '../models/passenger.model';

const baseUrl = 'http://localhost:8080/api/passenger';

@Injectable({
  providedIn: 'root'
})
export class PassengerService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Passenger[]> {
    return this.http.get<Passenger[]>(baseUrl);
  }

  get(id: any): Observable<Passenger> {
    return this.http.get(`${baseUrl}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(baseUrl);
  }

  findByUsername(username: any): Observable<Passenger[]> {
    return this.http.get<Passenger[]>(`${baseUrl}?username=${username}`);
  }
}
