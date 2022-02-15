package com.archlog.microrfid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.archlog.microrfid.model.RFID;

public interface RFIDRepository extends JpaRepository<RFID, Long> {

	@Query(value= "SELECT * FROM rfids WHERE passengerid = :passengerid", nativeQuery = true)
	RFID findByPassengerId(@Param("passengerid")long passengerid);


}
