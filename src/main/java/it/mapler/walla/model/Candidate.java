package it.mapler.walla.model;

import java.util.Calendar;

public class Candidate {

	private Long iduser;
	private String username;
	private String candidato;
	private String nome;
	private String cognome;
	private String cellulare;
	private String idcandidato;
	private String cv;
	private String indirizzo;
	private String provincia;
	private String regione;
	private String citta;
	private String cap;
	private String anniesperienza;
	private Calendar datanascita;
	

	public Long getIduser() {
		return iduser;
	}
	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getIdcandidato() {
		return idcandidato;
	}
	public void setIdcandidato(String idcandidato) {
		this.idcandidato = idcandidato;
	}
	public String getCv() {
		return cv;
	}
	public void setCv(String cv) {
		this.cv = cv;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
		
	public String getAnniesperienza() {
		return anniesperienza;
	}
	public void setAnniesperienza(String anniesperienza) {
		this.anniesperienza = anniesperienza;
	}
	
	public Calendar getDatanascita() {
		return datanascita;
	}
	public void setDatanascita(Calendar datanascita) {
		this.datanascita = datanascita;
	}

	public String getCandidato() {
		return candidato;
	}
	public void setCandidato(String candidato) {
		this.candidato = candidato;
	}
	
}
