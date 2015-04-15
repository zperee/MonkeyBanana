package ch.monkeybanana.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;

/**
 * Dies ist unser GUI für die Anmeldung
 * @author Dominic Pfister, Elia Perenzin
 * LoginView.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */

public class LoginView extends JFrame{

	private static final long serialVersionUID = 1L;

	//JTextField
	protected JTextField username = new JTextField();
	
	//JPasswordField
	protected JPasswordField password = new JPasswordField();
	
	//JButton
	protected JButton registrierenButton = new JButton("Registrieren");
	protected JButton loginbButton = new JButton("Login");

	//JLabel
	protected JLabel usernameLabel = new JLabel("Username");
	protected JLabel passwordLabel = new JLabel("Passwort");	
	protected JLabel titelLabel = new JLabel("MonkeyBanana Login");

	public LoginView() {
		this.setTitle("MonkeyBanana");
		this.setBounds(500, 400, 419, 238);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		
		titelLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		titelLabel.setBounds(48, 11, 141, 23);
		this.add(titelLabel);
		
		usernameLabel.setBounds(48, 45, 102, 24);
		this.add(usernameLabel);

		username.setBounds(160, 47, 197, 20);
		this.add(username);
		username.setColumns(10);
		
		passwordLabel.setBounds(48, 76, 102, 24);
		this.add(passwordLabel);
				
		password.setBounds(160, 78, 197, 20);
		this.add(password);
								
		registrierenButton.setBounds(160, 120, 106, 23);
		this.add(registrierenButton);
				
		loginbButton.setBounds(273, 120, 84, 23);
		this.add(loginbButton);			
	}
}
