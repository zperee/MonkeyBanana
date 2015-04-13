package ch.monkeybanana.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {
	
	//Panels
	JPanel loginNorthPanel = new JPanel(new GridLayout(2,2));
	JPanel loginCenterPanel = new JPanel(new GridLayout(3,3));	
	
	//Butons
	JButton login = new JButton("Login");
	JButton registrieren = new JButton ("Registrieren");
	
	//TextFields
	JTextField username = new JTextField(20);
	JPasswordField passwort = new JPasswordField(20);

	//Labels
	JLabel loginLabel = new JLabel("Login für MonkeyBanana");
	JLabel usernameLabel = new JLabel("Username");
	JLabel passwortLabel = new JLabel("Passwort");
	JLabel keinAccount = new JLabel("Noch kein Account hier registrieren");

	public LoginView(){
		loginNorthPanel.add(usernameLabel);
		loginNorthPanel.add(username);
		loginNorthPanel.add(passwortLabel);
		loginNorthPanel.add(passwort);
		
		loginCenterPanel.add(login);
		loginCenterPanel.add(keinAccount);
		loginCenterPanel.add(registrieren);

		this.add(loginLabel, BorderLayout.NORTH);
		this.add(loginNorthPanel, BorderLayout.CENTER);
		this.add(loginCenterPanel, BorderLayout.SOUTH);
		
		// Eigenschaften des GUI
		setSize(360, 250);
		setVisible(true);
//		setResizable(false);
		setLocale(null);

	}
}
