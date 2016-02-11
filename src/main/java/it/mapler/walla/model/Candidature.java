package it.mapler.walla.model;
import java.util.Calendar;
import java.util.Date;
import com.mysql.jdbc.CacheAdapter;
import it.mapler.walla.enumeration.PROFILE;


public class Candidature {
	
	Long idcandidatura;  //pk
	Long idcandidato;    //fk
	Long idofferta;      //fk
	String descrizione;
	
	
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
	
	
	

}
