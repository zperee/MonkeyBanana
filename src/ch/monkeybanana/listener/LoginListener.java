package ch.monkeybanana.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;
import ch.monkeybanana.view.RegistrierenView;

/**
 * In dieser Klasse befindenb sich alle Actionlistener fuer das Login
 * @author Dominic Pfister, Elia Perenzin LoginListener.java Copyright
 * Berufsbildungscenter MonkeyBanana 2015
 */
public class LoginListener implements ActionListener, KeyListener{

	// Instanzvariablen
	private JTextField user = null;
	private JPasswordField passwort = null;
	private String button = null;
	private JFrame frame = null;

	/**
	 * Konstruktor fuer LoginListner setzt den Username und das Passowrt in
	 * Instanzvariablen + kann noch der Button hinzugefuegt werden
	 * @author Elia Perenzin
	 * @param username {@link JTextField} 
	 * @param password {@link JPasswordField}
	 * @param button {@link String}
	 * @param frame {@link JFrame}
	 */
	public LoginListener(JTextField username, JPasswordField password, String button, JFrame frame) {
		this.setUser(username);
		this.setPasswort(password);
		this.setButton(button);
		this.setFrame(frame);
	}
	
	public LoginListener(JTextField username, JPasswordField password) {
		this.setUser(username);
		this.setPasswort(password);
	}

	/**
	 * ActionListener fuer den Knopf login und registrieren Erste if Schleife
	 * fuehrt das Login durch die zweite Schleife erstellt ein neues
	 * registrieren Fenster 
	 * @author Elia Perenzin
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		
		try {
			if (button.equals("login")) {
				User loginUser = new User();
	
				String user = this.getUser().getText();
				String passwort = this.getPasswort().getText();
	
				loginUser.setPasswort(passwort);
				loginUser.setUsername(user);
				loginUser.setVersion("1.1");
	
				Client.getInstance().login(loginUser);
			} else if (button.equals("registrieren")) {
				frame.dispose();
				new RegistrierenView();
			}
		} catch (Exception e1) {
			 JOptionPane.showMessageDialog(null,
					"Es konnte keine Verbindung zum Server hergestellt werden.", "Warnung!",
					JOptionPane.ERROR_MESSAGE);
			 System.exit(0);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void keyPressed(KeyEvent e) {
		try {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				User loginUser = new User();
	
				String user = this.getUser().getText();
				String passwort = this.getPasswort().getText();
	
				loginUser.setPasswort(passwort);
				loginUser.setUsername(user);
				loginUser.setVersion("1.1");
	
				Client.getInstance().login(loginUser);
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Es konnte keine Verbindung zum Server hergestellt werden.", "Warnung!",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	/* **GETTER und SETTER** */
	public JTextField getUser() {
		return user;
	}

	public JPasswordField getPasswort() {
		return passwort;
	}

	public String getButton() {
		return button;
	}
	
	public void setButton(String button) {
		this.button = button;
	}

	public void setUser(JTextField user) {
		this.user = user;
	}

	public void setPasswort(JPasswordField passwort) {
		this.passwort = passwort;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
