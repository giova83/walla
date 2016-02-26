package it.mapler.walla.api.request;

import java.util.Calendar;

public class CandidatureRequest {

	Long idcandidatura; 
	Long idcandidato;    
	Long idofferta;      
	String descrizione;
	Calendar datacandidatura;
	
	
	public Long getIdcandidatura() {
		return idcandidatura;
	}
	public void setIdcandidatura(Long idcandidatura) {
		this.idcandidatura = idcandidatura;
	}
	public Long getIdcandidato() {
		return idcandidato;
	}
	public void setIdcandidato(Long idcandidato) {
		this.idcandidato = idcandidato;
	}
	public Long getIdofferta() {
		return idofferta;
	}
	public void setIdofferta(Long idofferta) {
		this.idofferta = idofferta;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Calendar getDatacandidatura() {
		return datacandidatura;
	}
	public void setDatacandidatura(Calendar datacandidatura) {
		this.datacandidatura = datacandidatura;
	}
	
	
	
}
