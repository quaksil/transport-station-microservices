package com.archlog.microrfid.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.archlog.microrfid.repository.RFIDRepository;
import com.fazecast.jSerialComm.SerialPort;
import com.archlog.microrfid.model.RFID;

@RestController
@RequestMapping("/rfid-api")
public class RFIDController {

	@Autowired
	RFIDRepository rfidRepository;

	@GetMapping("/rfids/{passengerid}")
	public void readRFID(@PathVariable("passengerid") long passengerid) {

		RFID rfid = new RFID();
		rfid = rfidRepository.findByPassengerId(passengerid);

		if (rfid == null) {
			return;
		} else {

			SerialPort sp = SerialPort.getCommPort("/dev/ttyACM0"); // device name
			sp.setComPortParameters(9600, 8, 1, 0); // 9600,8,1,0default connection settings for Arduino
			sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

			if (sp.openPort()) {
				System.out.println("Port is open :)");
				PacketListener listenerObject = new PacketListener(); // creates new listener object
				sp.addDataListener(listenerObject);
			}

		}

	}

}
