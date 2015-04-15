package ch.monkeybanana.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import ch.monkeybanana.listener.RegistrierenListener;

/**
 * Dies ist unser GUI für die Registration
 * @author Dominic Pfister, Elia Perenzin
 * RegistrierenView.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */

public class RegistrierenView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	 //JTextField
	protected JTextField username = new JTextField();
	protected JTextField email = new JTextField();
	
	//JPasswordField
	protected JPasswordField password = new JPasswordField();
	protected JPasswordField passwordConfirm = new JPasswordField();
	
	//JButton
	protected JButton registrierenButton = new JButton("Registrieren");
	
	//JLabel
	protected JLabel titelLabel = new JLabel("Registration");
	protected JLabel usernameLabel = new JLabel("Username");
	protected JLabel emailLabel = new JLabel("E-mail");
	protected JLabel passwordLabel = new JLabel("Passwort");
	protected JLabel passwordConfirmLabel = new JLabel("Passwort best\u00E4tigen");

	public RegistrierenView() {
		this.setTitle("Registration MonkeyBanana");
		this.setBounds(500, 400, 419, 238);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		
		titelLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		titelLabel.setBounds(48, 11, 141, 23);
		this.getContentPane().add(titelLabel);
				
		usernameLabel.setBounds(48, 45, 102, 24);
		this.getContentPane().add(usernameLabel);
				
		username.setColumns(10);
		username.setBounds(181, 47, 176, 20);
		this.add(username);
		
		emailLabel.setBounds(48, 70, 102, 24);
		this.add(emailLabel);
		
		email.setBounds(181, 72, 176, 20);
		this.add(email);
					
		passwordLabel.setBounds(48, 96, 102, 24);
		this.add(passwordLabel);
		
		password.setBounds(181, 98, 176, 20);
		this.add(password);

		passwordConfirmLabel.setBounds(48, 123, 123, 24);
		this.add(passwordConfirmLabel);

		passwordConfirm.setBounds(181, 125, 176, 20);
		this.add(passwordConfirm);
						
		registrierenButton.setBounds(251, 154, 106, 23);
		registrierenButton.addActionListener(new RegistrierenListener(username, email, password, passwordConfirm));
		this.add(registrierenButton);
	}
}