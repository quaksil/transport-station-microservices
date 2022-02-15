package com.archlog.microrfid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="rfids")
public class RFID {
	

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name ="rfid", unique =true)
	private String rfid;
	
	@Column(name = "passengerid")
	private long passengerId;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "nbtrips")
	private int nbTrips;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(long passengerId) {
		this.passengerId = passengerId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getNbTrips() {
		return nbTrips;
	}

	public void setNbTrips(int nbTrips) {
		this.nbTrips = nbTrips;
	}
	
	
	
	
	

}
