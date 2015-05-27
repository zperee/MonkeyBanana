package ch.monkeybanana.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

/**
 * In dieser Klasse befindenb sich sind alle Actionlistener fuer die Registration
 * @author Dominic Pfister, Elia Perenzin
 * RegistrierenListener.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */
public class RegistrierenListener implements ActionListener, KeyListener{

	//Instanzvariablen
	private JTextField user = null, email = null;
	private JPasswordField passwort1 = null, passwort2 = null;
	
	/**
	 * Konstruktor fuer LoginListner setzt alle Parameter in Instanzvariablen
	 * @author Elia Perenzin
	 * @param user, email, passwort1, passwort2
	 */
	public RegistrierenListener(JTextField user, JTextField email, JPasswordField passwort1, JPasswordField passwort2) {
		this.setUser(user);
		this.setEmail(email);
		this.setPasswort1(passwort1);
		this.setPasswort2(passwort2);
	}
	
	/**
	 * ActionListner fuer den Button registrieren, traegt einen neuen User in die DB ein
	 * @author Elia Perenzin
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		User newUser = new User();
		
		String user = this.getUser().getText();
		String passwort1 = this.getPasswort1().getText();
		String passwort2 = this.getPasswort2().getText();
		String email = this.getEmail().getText();
		
		newUser.setUsername(user);
		newUser.setPasswort(passwort1);
		newUser.setPasswort2(passwort2);
		newUser.setEmail(email);
		
		Client.getInstance().registrieren(newUser);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	@SuppressWarnings("deprecation")
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			User newUser = new User();
			
			String user = this.getUser().getText();
			String passwort1 = this.getPasswort1().getText();
			String passwort2 = this.getPasswort2().getText();
			String email = this.getEmail().getText();
			
			newUser.setUsername(user);
			newUser.setPasswort(passwort1);
			newUser.setPasswort2(passwort2);
			newUser.setEmail(email);

			Client.getInstance().registrieren(newUser); 
		}
	}

	public void keyReleased(KeyEvent e) {		
		
	}

	/* **GETTER und SETTER** */
	public JTextField getUser() {
		return user;
	}

	public JPasswordField getPasswort1() {
		return passwort1;
	}

	public JPasswordField getPasswort2() {
		return passwort2;
	}

	public JTextField getEmail() {
		return email;
	}

	public void setUser(JTextField user) {
		this.user = user;
	}

	public void setPasswort1(JPasswordField passwort1) {
		this.passwort1 = passwort1;
	}

	public void setPasswort2(JPasswordField passwort2) {
		this.passwort2 = passwort2;
	}

	public void setEmail(JTextField email) {
		this.email = email;
	}
}
