package it.mapler.walla.api.request;

import java.util.Calendar;
import java.util.Date;

public class OfferRequest {
	
	private String titolo;
	private String categoria;
	private String tipologia;
	private String citta;
	private Calendar datapubblicazione;
	
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
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
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public Calendar getDatapubblicazione() {
		return datapubblicazione;
	}
	public void setDatapubblicazione(Calendar datapubblicazione) {
		this.datapubblicazione = datapubblicazione;
	}
    
    
}


