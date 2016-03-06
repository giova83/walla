package it.mapler.walla.model;
import java.util.Calendar;
import java.util.Date;
import com.mysql.jdbc.CacheAdapter;
import it.mapler.walla.enumeration.PROFILE;


public class Product {
	
	  Long idprodotto; //pk
	  Long idazienda; //fk
	  String titolo;
	  String tipologia;
	  String categoria;
	  String descrizione;
	  Calendar datapubblicazione;
	  String foto;
	  double prezzoofferta;
	  String provenienza;
	  
	  public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public double getPrezzoofferta() {
		return prezzoofferta;
	}
	public void setPrezzoofferta(double prezzoofferta) {
		this.prezzoofferta = prezzoofferta;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	double costo;
	  double quantita;
	  boolean prodotto_attivo;
	  
	  
	public Long getIdprodotto() {
		return idprodotto;
	}
	public void setIdprodotto(Long idprodotto) {
		this.idprodotto = idprodotto;
	}
	public Long getIdazienda() {
		return idazienda;
	}
	public void setIdazienda(Long idazienda) {
		this.idazienda = idazienda;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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
	public boolean isProdotto_attivo() {
		return prodotto_attivo;
	}
	public void setProdotto_attivo(boolean prodotto_attivo) {
		this.prodotto_attivo = prodotto_attivo;
	}

	  
}
