package it.mapler.walla.model;
import java.util.Calendar;
import java.util.Date;
import com.mysql.jdbc.CacheAdapter;
import it.mapler.walla.enumeration.PROFILE;


public class Company {

	
	Long idazienda;  //pk
	Long idfornitore;//fk
	String nome;
	String cellulare;
	String categoria;
	String indirizzo;
	String citta;
	String provincia;
	String regione;
	String cap;
	double latitudine;
	double longitudine;
	String partitaiva;
	
	
	public Long getIdazienda() {
		return idazienda;
	}
	public void setIdazienda(Long idazienda) {
		this.idazienda = idazienda;
	}
	public Long getIdfornitore() {
		return idfornitore;
	}
	public void setIdfornitore(Long idfornitore) {
		this.idfornitore = idfornitore;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
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
	
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public double getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}
	public double getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}
	public String getPartitaiva() {
		return partitaiva;
	}
	public void setPartitaiva(String partitaiva) {
		this.partitaiva = partitaiva;
	}
	  
	
}
