package ch.monkeybanana.model;

import java.io.Serializable;

/**
 * Diese Klasse enthaelt alle Attribute eines Users. Sie wird beim Login und der Registration benötigt
 * @author Dominic Pfister, Elia Perenzin User.java Copyright 
 * Berufsbildungscenter MonkeyBanana 2015
 */

public class User implements Serializable{
	private static final long serialVersionUID = 9077441262243570767L;
	
	//Instanzvariablen
	private String username = null;
	private String email = null;
	private String passwort = null;
	private String passwort2 = null;
	private String version = null;
	
	/* **GETTER und SETTER** */
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
	public String getVersion() {
		return version;
	}
	
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
	public void setVersion(String version) {
		this.version = version;
	}
}
