package it.mapler.walla.model;

import java.util.Calendar;

public class Login {
	
	private Calendar dataAccesso;
	private String username;
	private Calendar dataUltimaModifica;
	private String token;
	private String idToken;
	
	public Login() {
		super();
		
	}
	public Login(Calendar dataAccesso, String username,
			Calendar dataUltimaModifica, String token) {
		super();
		this.dataAccesso = dataAccesso;
		this.username = username;
		this.dataUltimaModifica = dataUltimaModifica;
		this.token = token;
	}
	public String getIdToken() {
		return idToken;
	}
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	public Calendar getDataAccesso() {
		return dataAccesso;
	}
	public void setDataAccesso(Calendar dataAccesso) {
		this.dataAccesso = dataAccesso;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Calendar getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(Calendar dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "Login [username=" + username + ", token=" + token
				+ ", idToken=" + idToken + "]";
	}
	
	
	
	

}
