package it.mapler.walla.model;

import it.mapler.walla.enumeration.PROFILE;

public class User {
	
	private String username;
	private String password;
	private Long idUser;
	private String email;
	private String resetPwd;
	private PROFILE profilo;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResetPwd() {
		return resetPwd;
	}
	public void setResetPwd(String resetPwd) {
		this.resetPwd = resetPwd;
	}
	public PROFILE getProfilo() {
		return profilo;
	}
	public void setProfilo(PROFILE profilo) {
		this.profilo = profilo;
	}
	
	

}
