import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddPassengerComponent } from './components/add-passenger/add-passenger.component';
import { PassengersDetailsComponent } from './components/passengers-details/passengers-details.component';
import { PassengersListComponent } from './components/passengers-list/passengers-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AddPassengerComponent,
    PassengersDetailsComponent,
    PassengersListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
