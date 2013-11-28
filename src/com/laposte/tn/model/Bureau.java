package com.laposte.tn.model;

public class Bureau {

	private String code;
	private String nom;
	private String services;
	private String horairesete;
	private String horairesnormal;
	private String horairesramadhan;
	private String jours;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getHorairesete() {
		return horairesete;
	}
	public void setHorairesete(String horairesete) {
		this.horairesete = horairesete;
	}
	public String getHorairesnormal() {
		return horairesnormal;
	}
	public void setHorairesnormal(String horairesnormal) {
		this.horairesnormal = horairesnormal;
	}
	public String getHorairesramadhan() {
		return horairesramadhan;
	}
	public void setHorairesramadhan(String horairesramadhan) {
		this.horairesramadhan = horairesramadhan;
	}
	public String getJours() {
		return jours;
	}
	public void setJours(String jours) {
		this.jours = jours;
	}
	public Bureau(String code, String nom, String services, String horairesete,
			String horairesnormal, String horairesramadhan, String jours) {
		super();
		this.code = code;
		this.nom = nom;
		this.services = services;
		this.horairesete = horairesete;
		this.horairesnormal = horairesnormal;
		this.horairesramadhan = horairesramadhan;
		this.jours = jours;
	}
	public Bureau() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Bureau [code=" + code + ", nom=" + nom + ", services="
				+ services + ", horairesete=" + horairesete
				+ ", horairesnormal=" + horairesnormal + ", horairesramadhan="
				+ horairesramadhan + ", jours=" + jours + "]";
	}
	
	
		
}
