package com.archlog.microadmin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archlog.microadmin.repository.PassengerRepository;

import com.archlog.microadmin.model.Passenger;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/passenger-api")
public class PassengerController {

	@Autowired
	PassengerRepository passengerRepository;

	@GetMapping("/passengers")
	public ResponseEntity<List<Passenger>> getAllPassengers(@RequestParam(required = false) String username) {
		try {
			List<Passenger> passengers = new ArrayList<Passenger>();
			if (username == null)
				passengerRepository.findAll().forEach(passengers::add);
			else
				passengerRepository.findByUsername(username).forEach(passengers::add);
			if (passengers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(passengers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/passengers/{id}")
	public ResponseEntity<Passenger> getPassengerById(@PathVariable("id") long id) {
		Optional<Passenger> passengerData = passengerRepository.findById(id);
		if (passengerData.isPresent()) {
			return new ResponseEntity<>(passengerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/passengers")
	public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
		try {
			Passenger _passenger = passengerRepository.save(new Passenger(passenger.getFirstname(),
					passenger.getLastname(), passenger.getAge(), passenger.getAddress(), passenger.getOccupation(),
					passenger.getUsername(), passenger.getPassword()));
			return new ResponseEntity<>(_passenger, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/passengers/{id}")
	public ResponseEntity<Passenger> updatePassenger(@PathVariable("id") long id, @RequestBody Passenger passenger) {
		Optional<Passenger> passengerData = passengerRepository.findById(id);
		if (passengerData.isPresent()) {
			Passenger _passenger = passengerData.get();
			_passenger.setFirstname(passenger.getFirstname());
			_passenger.setLastname(passenger.getLastname());
			_passenger.setAge(passenger.getAge());
			_passenger.setAddress(passenger.getAddress());
			_passenger.setOccupation(passenger.getOccupation());
			_passenger.setUsername(passenger.getUsername());
			_passenger.setPassword(passenger.getPassword());

			return new ResponseEntity<>(passengerRepository.save(_passenger), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/passengers/{id}")
	public ResponseEntity<HttpStatus> deletePassenger(@PathVariable("id") long id) {
		try {
			passengerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/passengers")
	public ResponseEntity<HttpStatus> deleteAllPassengers() {
		try {
			passengerRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * @GetMapping("/passengers/active") public ResponseEntity<List<Passenger>>
	 * findByUsername() { try { List<Passenger> passengers =
	 * passengerRepository.findByActive(true); if (passengers.isEmpty()) { return
	 * new ResponseEntity<>(HttpStatus.NO_CONTENT); } return new
	 * ResponseEntity<>(passengers, HttpStatus.OK); } catch (Exception e) { return
	 * new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */
}
