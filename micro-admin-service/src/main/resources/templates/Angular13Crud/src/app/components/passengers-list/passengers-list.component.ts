import { Component, OnInit } from '@angular/core';
import { Passenger } from 'src/app/models/passenger.model';
import { PassengerService } from 'src/app/services/passenger.service';

@Component({
  selector: 'app-passengers-list',
  templateUrl: './passengers-list.component.html',
  styleUrls: ['./passengers-list.component.css']
})
export class PassengersListComponent implements OnInit {

  passengers?: Passenger[];
  currentPassenger: Passenger = {};
  currentIndex = -1;
  username = '';

  constructor(private passengerService: PassengerService) { }

  ngOnInit(): void {
    this.retrievePassengers();
  }

  retrievePassengers(): void {
    this.passengerService.getAll()
      .subscribe({
        next: (data) => {
          this.passengers = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  refreshList(): void {
    this.retrievePassengers();
    this.currentPassenger = {};
    this.currentIndex = -1;
  }

  setActivePassenger(passenger: Passenger, index: number): void {
    this.currentPassenger = passenger;
    this.currentIndex = index;
  }

  removeAllPassengers(): void {
    this.passengerService.deleteAll()
      .subscribe({
        next: (res) => {
          console.log(res);
          this.refreshList();
        },
        error: (e) => console.error(e)
      });
  }

  searchUsername(): void {
    this.currentPassenger = {};
    this.currentIndex = -1;

    this.passengerService.findByUsername(this.username)
      .subscribe({
        next: (data) => {
          this.passengers = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

}
