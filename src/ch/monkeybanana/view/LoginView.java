package ch.monkeybanana.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ch.monkeybanana.listener.LoginListener;
import javax.swing.SwingConstants;

/**
 * Dies ist unser GUI fuer die Anmeldung
 * @author Dominic Pfister, Elia Perenzin 
 * LoginView.java Copyright
 * Berufsbildungscenter MonkeyBanana 2015
 */

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;

	// JTextField
	protected JTextField username = new JTextField();

	// JPasswordField
	protected JPasswordField password = new JPasswordField();

	// JButton
	protected JButton registrierenButton = new JButton("Registrieren");
	protected JButton loginbButton = new JButton("Login");

	// JLabel
	protected JLabel usernameLabel = new JLabel("Username");
	protected JLabel passwordLabel = new JLabel("Passwort");
	protected JLabel titelLabel = new JLabel("MonkeyBanana Login");
	private final JLabel versionLabel = new JLabel("1.0");
	private final JLabel lblNewLabel = new JLabel("Version:");

	public LoginView() {
		this.setTitle("MonkeyBanana - Login");
		this.setBounds(500, 400, 419, 238);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setResizable(false);

		titelLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		titelLabel.setBounds(48, 11, 141, 23);
		getContentPane().add(titelLabel);

		usernameLabel.setBounds(48, 45, 102, 24);
		getContentPane().add(usernameLabel);

		username.setBounds(160, 47, 197, 20);
		username.setColumns(10);
		username.addKeyListener(new LoginListener(username, password));
		getContentPane().add(username);

		passwordLabel.setBounds(48, 76, 102, 24);
		getContentPane().add(passwordLabel);

		password.setBounds(160, 78, 197, 20);
		password.addKeyListener(new LoginListener(username, password));
		getContentPane().add(password);

		registrierenButton.setBounds(160, 120, 106, 23);
		registrierenButton.addActionListener(new LoginListener(username, password, "registrieren"));
		getContentPane().add(registrierenButton);

		loginbButton.setBounds(273, 120, 84, 23);
		loginbButton.addActionListener(new LoginListener(username, password, "login"));
		loginbButton.addKeyListener(new LoginListener(username, password));
		getContentPane().add(loginbButton);
		versionLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		versionLabel.setBounds(365, 174, 36, 16);
		
		getContentPane().add(versionLabel);
		lblNewLabel.setBounds(322, 174, 56, 16);
		
		getContentPane().add(lblNewLabel);
		
		this.setVisible(true);
	}
	
}
