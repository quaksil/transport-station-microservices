import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PassengersListComponent } from './components/passengers-list/passengers-list.component';
import { PassengerDetailsComponent } from './components/passenger-details/passenger-details.component';
import { AddPassengerComponent } from './components/add-passenger/add-passenger.component';

const routes: Routes = [
  { path: '', redirectTo: 'passengers', pathMatch: 'full' },
  { path: 'passengers', component: PassengersListComponent },
  { path: 'passengers/:id', component: PassengerDetailsComponent },
  { path: 'add', component: AddPassengerComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
