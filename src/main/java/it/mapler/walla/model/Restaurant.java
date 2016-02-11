package it.mapler.walla.model;
import java.util.Calendar;
import java.util.Date;
import com.mysql.jdbc.CacheAdapter;
import it.mapler.walla.enumeration.PROFILE;

public class Restaurant {
	
	Long idristorante;  //pk
	Long idristoratore; //fk
	String nome;
	String indirizzo;
	double latitudine;
	double longitudine;
	Long cap;
	String citta;
	String provincia;
	String regione;
	String partitaiva;
	
	
	public Long getIdristorante() {
		return idristorante;
	}
	public void setIdristorante(Long idristorante) {
		this.idristorante = idristorante;
	}
	public Long getIdristoratore() {
		return idristoratore;
	}
	public void setIdristoratore(Long idristoratore) {
		this.idristoratore = idristoratore;
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
	public Long getCap() {
		return cap;
	}
	public void setCap(Long cap) {
		this.cap = cap;
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
	public String getPartitaiva() {
		return partitaiva;
	}
	public void setPartitaiva(String partitaiva) {
		this.partitaiva = partitaiva;
	}

	
	
}
