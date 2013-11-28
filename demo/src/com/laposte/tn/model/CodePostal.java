package com.laposte.tn.model;

public class CodePostal {
	
	
	private String code;
	private String quartier;
	private String commune;
	private String gouvernorat;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getQuartier() {
		return quartier;
	}
	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getGouvernorat() {
		return gouvernorat;
	}
	public void setGouvernorat(String gouvernorat) {
		this.gouvernorat = gouvernorat;
	}
	public CodePostal(String code, String quartier, String commune,
			String gouvernorat) {
		super();
		this.code = code;
		this.quartier = quartier;
		this.commune = commune;
		this.gouvernorat = gouvernorat;
	}
	public CodePostal() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
