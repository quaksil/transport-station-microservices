import { Component, Input, OnInit } from '@angular/core';
import { PassengerService } from 'src/app/services/passenger.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Passenger } from 'src/app/models/passenger.model';

@Component({
  selector: 'app-passenger-details',
  templateUrl: './passenger-details.component.html',
  styleUrls: ['./passenger-details.component.css']
})
export class PassengerDetailsComponent implements OnInit {

  @Input() viewMode = false;

  @Input() currentPassenger: Passenger = {
    firstname: '',
    lastname: '',
    age: '',
    address: '',
    occupation: '',
    username: '',
    password: '',
    lastname: ''

  };
  
  message = '';

  constructor(
    private passengerService: PassengerService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      this.getPassenger(this.route.snapshot.params["id"]);
    }
  }

  getPassenger(id: string): void {
    this.passengerService.get(id)
      .subscribe({
        next: (data) => {
          this.currentPassenger = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  updatePublished(status: boolean): void {
    const data = {
      username: this.currentPassenger.username,
      password: this.currentPassenger.description,
      active: status
    };

    this.message = '';

    this.passengerService.update(this.currentPassenger.id, data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.currentPassenger.active = status;
          this.message = res.message ? res.message : 'The status was updated successfully!';
        },
        error: (e) => console.error(e)
      });
  }

  updatePassenger(): void {
    this.message = '';

    this.passengerService.update(this.currentPassenger.id, this.currentPassenger)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This passenger was updated successfully!';
        },
        error: (e) => console.error(e)
      });
  }

  deletePassenger(): void {
    this.passengerService.delete(this.currentPassenger.id)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.router.navigate(['/passengers']);
        },
        error: (e) => console.error(e)
      });
  }

}
