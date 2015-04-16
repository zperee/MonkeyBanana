package ch.monkeybanana.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;


import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.model.User;
import ch.monkeybanana.view.RegistrierenView;

/**
 * Hier sind alle Actionlistener fuer das Login
 * @author Dominic Pfister, Elia Perenzin
 * LoginListener.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */
public class LoginListener implements ActionListener {

	//Instanzvariablen
	private JTextField user = null;
	private JPasswordField passwort = null;
	private String button = null;
	
	/**
	 * Konstruktor fuer LoginListner setzt den Username und das Passowrt in Instanzvariablen + kann noch der Button hinzugefuegt werden
	 * @author Elia Perenzin
	 * @param username, password, button
	 */
	public LoginListener(JTextField username, JPasswordField password, String button) {
		this.setUser(username);
		this.setPasswort(password);
		this.setButton(button);
	}

	/**
	 * ActionListener fuer den Knopf login und registrieren
	 * Erste if Schleife fuehrt das Login durch die zweite Schleife erstellt ein neues registrieren Fenster
	 * @author Elia Perenzin
	 */
	public void actionPerformed(ActionEvent e) {
//		try{
		if (button.equals("login")){
			User loginUser = new User();
			
			String user = this.getUser().getText();
			String passwort = this.getPasswort().getText();
			
			loginUser.setPasswort(passwort);
			loginUser.setUsername(user);
			
			MBController.getInstance().login(loginUser);
//		}	
		}
		else if (button.equals("registrieren")){
			new RegistrierenView();
		}
	}

	//Getter
	public JTextField getUser() {
		return user;
	}

	public JPasswordField getPasswort() {
		return passwort;
	}

	public String getButton() {
		return button;
	}

	//Setter
	public void setButton(String button) {
		this.button = button;
	}

	public void setUser(JTextField user) {
		this.user = user;
	}

	public void setPasswort(JPasswordField passwort) {
		this.passwort = passwort;
	}
}
