import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddPassengerComponent } from './components/add-passenger/add-passenger.component';
import { PassengerDetailsComponent } from './components/passenger-details/passenger-details.component';
import { PassengersListComponent } from './components/passengers-list/passengers-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AddPassengerComponent,
    PassengerDetailsComponent,
    PassengersListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
