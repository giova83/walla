package it.mapler.walla.model;
import java.util.Calendar;
import java.util.Date;
import com.mysql.jdbc.CacheAdapter;
import it.mapler.walla.enumeration.PROFILE;


public class Service {
	
	Long idservizio;  //pk
	Long idazienda;  //fk
	String descrizione;
	String categoria;
	String tipologia;
	String titolo;
	Calendar datapubblicazione;
	double costo;  //0.0d
 	double quantita;
	boolean servizio_attivo;
	
	
	public Long getIdservizio() {
		return idservizio;
	}
	public void setIdservizio(Long idservizio) {
		this.idservizio = idservizio;
	}
	public Long getIdazienda() {
		return idazienda;
	}
	public void setIdazienda(Long idazienda) {
		this.idazienda = idazienda;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public Calendar getDatapubblicazione() {
		return datapubblicazione;
	}
	public void setDatapubblicazione(Calendar datapubblicazione) {
		this.datapubblicazione = datapubblicazione;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public double getQuantita() {
		return quantita;
	}
	public void setQuantita(double quantita) {
		this.quantita = quantita;
	}
	public boolean isServizio_attivo() {
		return servizio_attivo;
	}
	public void setServizio_attivo(boolean servizio_attivo) {
		this.servizio_attivo = servizio_attivo;
	}

}
