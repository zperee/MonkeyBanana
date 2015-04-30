package ch.monkeybanana.model;

import java.io.Serializable;

/**
 * Diese Klasse wird benoetigt um einen User zu erstellen. Sie enthaelt alle Infromationen ueber den User
 * @author Dominic Pfister, Elia Perenzin
 * User.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */

public class User implements Serializable{
	private static final long serialVersionUID = 9077441262243570767L;
	//Instanzvariablen
	private String username = null;
	private String email = null;
	private String passwort = null;
	private String passwort2 = null;
	
	//Getter
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getPasswort() {
		return passwort;
	}
	public String getPasswort2() {
		return passwort2;
	}
	
	//Setter
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public void setPasswort2(String passwort2) {
		this.passwort2 = passwort2;
	}
	
}
