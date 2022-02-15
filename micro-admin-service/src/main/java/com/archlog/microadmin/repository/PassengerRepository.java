package com.archlog.microadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archlog.microadmin.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

	List<Passenger> findByUsername(String username);

	// List<Passenger> findByActive(boolean active);

}
