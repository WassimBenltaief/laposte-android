package com.laposte.tn.model;

public class Carte {
	
	private String type;
	private String numero;
	private String dateFinValidite;
	private String actif;
	private String nom;
	private String prenom;
	private String solde;
	private String codeInternet;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDateFinValidite() {
		return dateFinValidite;
	}
	public void setDateFinValidite(String dateFinValidite) {
		this.dateFinValidite = dateFinValidite;
	}
	public String getActif() {
		return actif;
	}
	public void setActif(String actif) {
		this.actif = actif;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getSolde() {
		return solde;
	}
	public void setSolde(String solde) {
		this.solde = solde;
	}
	public String getCodeInternet() {
		return codeInternet;
	}
	public void setCodeInternet(String codeInternet) {
		this.codeInternet = codeInternet;
	}
	public Carte(String type, String numero, String dateFinValidite,
			String actif, String nom, String prenom, String solde,
			String codeInternet) {
		super();
		this.type = type;
		this.numero = numero;
		this.dateFinValidite = dateFinValidite;
		this.actif = actif;
		this.nom = nom;
		this.prenom = prenom;
		this.solde = solde;
		this.codeInternet = codeInternet;
	}
	public Carte() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Carte [type=" + type + ", numero=" + numero
				+ ", dateFinValidite=" + dateFinValidite + ", actif=" + actif
				+ ", nom=" + nom + ", prenom=" + prenom + ", solde=" + solde
				+ ", codeInternet=" + codeInternet + "]";
	}
	
	
	

}
