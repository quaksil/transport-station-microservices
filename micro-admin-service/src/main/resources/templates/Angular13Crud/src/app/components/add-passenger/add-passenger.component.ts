import { Component, OnInit } from '@angular/core';
import { Passenger } from 'src/app/models/passenger.model';
import { PassengerService } from 'src/app/services/passenger.service';

@Component({
  selector: 'app-add-passenger',
  templateUrl: './add-passenger.component.html',
  styleUrls: ['./add-passenger.component.css']
})
export class AddPassengerComponent implements OnInit {

  passenger: Passenger = {
    firstname: '',
    lastname: '',
    age: '',
    address: '',
    occupation: '',
    username: '',
    password: '',
    lastname: ''
  };
  submitted = false;

  constructor(private passengerService: PassengerService) { }

  ngOnInit(): void {
  }

  savePassenger(): void {
    const data = {
      title: this.passenger.title,
      description: this.passenger.description
    };

    this.passengerService.create(data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.submitted = true;
        },
        error: (e) => console.error(e)
      });
  }

  newPassenger(): void {
    this.submitted = false;
    this.passenger = {
      firstname: '',
      lastname: '',
      age: '',
      address: '',
      occupation: '',
      username: '',
      password: '',
      lastname: ''
    };
  }

}
