package com.laposte.tn.model;



public class RapidPost {

	private String id;
	private String numero;
	private String date;
	private String pays;
	private String lieu;
	private String type;
	private String informations;
	public String getId() {
		return id;
	}	
	public void setId(String id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		pays = pays;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		lieu = lieu;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInformations() {
		return informations;
	}
	public void setInformations(String informations) {
		this.informations = informations;
	}
	public RapidPost(String id, String numero, String date, String pays,
			String lieu, String type, String informations) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		pays = pays;
		lieu = lieu;
		this.type = type;
		this.informations = informations;
	}
	public RapidPost() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
