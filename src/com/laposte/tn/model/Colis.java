package com.laposte.tn.model;

import java.util.Date;
import java.util.List;

public class Colis {

	private String id;
	private String notes;
	private String etat;
	private String date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Colis(String id, String notes, String etat, String date) {
		super();
		this.id = id;
		this.notes = notes;
		this.etat = etat;
		this.date = date;
	}
	public Colis() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
