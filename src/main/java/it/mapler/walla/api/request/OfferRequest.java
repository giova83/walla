package it.mapler.walla.api.request;

import java.util.Calendar;

public class OfferRequest {

	private String titolo;
	private String categoria;
	private String tipologia;
	private String citta;
	private Calendar datapubblicazione;

	private Long idristorante;
	private String nome;
	private String indirizzo;
	private String descrizione;


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
	public Long getIdristorante() {
		return idristorante;
	}
	public void setIdristorante(Long idristorante) {
		this.idristorante = idristorante;
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
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}




}


