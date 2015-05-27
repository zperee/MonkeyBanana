package ch.monkeybanana.view;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ch.monkeybanana.listener.RegistrierenListener;
import ch.monkeybanana.rmi.Client;

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
		this.setTitle("MonkeyBanana - Registrieren");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/images/banana.png"));
		this.setBounds(500, 400, 419, 238);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		
		titelLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		titelLabel.setBounds(48, 11, 141, 23);
		this.getContentPane().add(titelLabel);
				
		usernameLabel.setBounds(48, 45, 102, 24);
		this.getContentPane().add(usernameLabel);
				
		username.setColumns(10);
		username.setBounds(181, 47, 200, 20);
		username.addKeyListener(new RegistrierenListener(username, email, password, passwordConfirm));
		getContentPane().add(username);
		
		emailLabel.setBounds(48, 70, 102, 24);
		getContentPane().add(emailLabel);
		
		email.setBounds(181, 72, 200, 20);
		email.addKeyListener(new RegistrierenListener(username, email, password, passwordConfirm));
		getContentPane().add(email);
					
		passwordLabel.setBounds(48, 96, 102, 24);
		getContentPane().add(passwordLabel);
		
		password.setBounds(181, 98, 200, 20);
		password.addKeyListener(new RegistrierenListener(username, email, password, passwordConfirm));
		getContentPane().add(password);

		passwordConfirmLabel.setBounds(48, 123, 123, 24);
		getContentPane().add(passwordConfirmLabel);

		passwordConfirm.setBounds(181, 125, 200, 20);
		passwordConfirm.addKeyListener(new RegistrierenListener(username, email, password, passwordConfirm));
		getContentPane().add(passwordConfirm);
						
		registrierenButton.setBounds(272, 160, 109, 23);
		registrierenButton.addActionListener(new RegistrierenListener(username, email, password, passwordConfirm));
		registrierenButton.addKeyListener(new RegistrierenListener(username, email, password, passwordConfirm));
		getContentPane().add(registrierenButton);
		
		JButton zurueckBtn = new JButton("Zur\u00FCck");
		zurueckBtn.setBounds(181, 160, 81, 23);
		zurueckBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Client.setFrame(new LoginView());
			}
		});
		getContentPane().add(zurueckBtn);
		
		this.setVisible(true);

	}
}