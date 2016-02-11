package it.mapler.walla.model;
import java.util.Calendar;
import java.util.Date;
import com.mysql.jdbc.CacheAdapter;
import it.mapler.walla.enumeration.PROFILE;


public class Experience {
	
	  Long idesperienza; //pk
	  Long idcandidato;  //fk
	  String esperienza;
	  String qualifica;
	  Long anni;
	  String tipologia;
	  String settore;
	  String livello;
	  
	public Long getIdesperienza() {
		return idesperienza;
	}
	public void setIdesperienza(Long idesperienza) {
		this.idesperienza = idesperienza;
	}
	public Long getIdcandidato() {
		return idcandidato;
	}
	public void setIdcandidato(Long idcandidato) {
		this.idcandidato = idcandidato;
	}
	public String getEsperienza() {
		return esperienza;
	}
	public void setEsperienza(String esperienza) {
		this.esperienza = esperienza;
	}
	public String getQualifica() {
		return qualifica;
	}
	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}
	public Long getAnni() {
		return anni;
	}
	public void setAnni(Long anni) {
		this.anni = anni;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getLivello() {
		return livello;
	}
	public void setLivello(String livello) {
		this.livello = livello;
	}
	  
	  
	  

}
