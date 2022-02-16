package com.archlog.microrfid.controller;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.archlog.microrfid.repository.RFIDRepository;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.archlog.microrfid.model.RFID;

@RestController
@RequestMapping("/rfid-api")
public class RFIDController implements SerialPortDataListener {

	@Autowired
	RFIDRepository rfidRepository;

	RFID rfid = new RFID();

	private static String bufferReadToString = ""; // empty, but not null
	private static int cutoffASCII = 10; // ASCII code of the character used for cut-off between received messages

	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; // returns data on the Serial Port
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		byte[] buffer = new byte[event.getSerialPort().bytesAvailable()];
		event.getSerialPort().readBytes(buffer, buffer.length);

		String s = new String(buffer);
		bufferReadToString = bufferReadToString.concat(s); // converts the bytes read from the Serial port to a string

		if ((bufferReadToString.indexOf(cutoffASCII) + 1) > 0) {

			if (rfid.getRfid() != "568F29FB") {
				System.out.println("Processing..\n");
				System.out.println("RFID detected: " + bufferReadToString);

				System.out.println("Success!\n");

				// JOptionPane.showMessageDialog(null, Trips left: " + rfid.getNbTrips());
				// Waiting obj = new Waiting();
			} else {

				System.out.println("Fail. Locked RFID or subscription is over.\n");

			} // prints out the recived data

			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.exit(0);

		}
	}

	@GetMapping("/rfids/{passengerid}")
	public void readRFID(@PathVariable("passengerid") long passengerid) {

		rfid = rfidRepository.findByPassengerId(passengerid);

		if (rfid == null) {
			return;
		} else {

			SerialPort sp = SerialPort.getCommPort("/dev/ttyACM0"); // device name
			sp.setComPortParameters(9600, 8, 1, 0); // 9600,8,1,0default connection settings for Arduino
			sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

			if (sp.openPort()) {
				System.out.println("\n __Input RFID card now..\n");
				// PacketListener listenerObject = new PacketListener(); // creates new listener
				// object
				sp.addDataListener(this);
			}

		}

	}

}
