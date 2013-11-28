package com.laposte.tn.model;

public class CCP {

	private String identifiant;
	private String adresse;
	private int status;
	private float solde;
	private int nature;
	private String rip;
	private int cle;
	
	
	
	public CCP() {
		super();
	}

	public CCP(String identifiant, String adresse, int status, float solde,
			int nature, String rip, int cle) {
		super();
		this.identifiant = identifiant;
		this.adresse = adresse;
		this.status = status;
		this.solde = solde;
		this.nature = nature;
		this.rip = rip;
		this.cle = cle;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public float getSolde() {
		return solde;
	}
	public void setSolde(float solde) {
		this.solde = solde;
	}
	public int getNature() {
		return nature;
	}
	public void setNature(int nature) {
		this.nature = nature;
	}
	public String getRip() {
		return rip;
	}
	public void setRip(String rip) {
		this.rip = rip;
	}
	public int getCle() {
		return cle;
	}
	public void setCle(int cle) {
		this.cle = cle;
	}

	@Override
	public String toString() {
		return "CCP [identifiant=" + identifiant + ", adresse=" + adresse
				+ ", status=" + status + ", solde=" + solde + ", nature="
				+ nature + ", rip=" + rip + ", cle=" + cle + "]";
	}
	
	
}
