package it.mapler.walla.model;

import java.util.Calendar;
import java.util.Date;

import com.mysql.jdbc.CacheAdapter;

import it.mapler.walla.enumeration.PROFILE;

public class Offer {
	
	private Long idofferta;
	private Long idristorante;
	private String nome;
	private String indirizzo;
	private String citta;
	private String descrizione;
	private String categoria;
	private String tipologia;
	private String titolo;
	private Calendar datapubblicazione;
	
	public Offer() {
		super();
		
	}
	
	public Long getIdofferta() {
		return idofferta;
	}
	public void setIdofferta(Long idofferta) {
		this.idofferta = idofferta;
	}
	public Long getIdristorante() {
		return idristorante;
	}
	public void setIdristorante(Long idristorante) {
		this.idristorante = idristorante;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}
	

	
	

}
