package ch.monkeybanana.rmi;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.model.User;
import ch.monkeybanana.util.CryptUtils;
import ch.monkeybanana.view.HomeView;
import ch.monkeybanana.view.LoginView;

/**
 * Wird benutzt um sich als Client am Server anzumelden.
 * Starterklasse
 * @author Dominic Pfister, Elia Perenzin
 */
public class Client {
	
	//Instanzvariablen
	private static Client instance = new Client();
	private String ip, port;
	private Communication connect;
	private static JFrame frame = new JFrame();

	/**
	 * Gibt die Instanz eines Clients zurueck.
	 * @author Dominic Pfister, Elia Perenzin
	 */
	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return Client.instance;
	}

	/**
	 * Startet einen neuen Client.
	 * @author Dominic Pfister, Elia Perenzin
	 */
	public static void main(String[] args) {
		Client.start();
	}

	/**
	 * Erzeugt ein neues LoginView fuer
	 * den Client.
	 * @author Dominic Pfister
	 */
	public static void start() {
		setFrame(new LoginView());
	}
	
	/**
	 * Konstruktor der Klasse Client.
	 * @author Dominic Pfister, Elia Perenzin
	 */
	private Client() {
		String filename = "serverdata.csv"; // File mit allen Informationen
		File file = new File(filename);
		
		//Array indem alle Informationen gespeichert werden.
		String[] server = new String[2];

		//Liest alle Informationen aus dem CSV File und speichert sie in eine Liste
		try {
			Scanner inputStream = new Scanner(file);
			int i = 0;
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				server[i] = data;
				i++;
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("File wurde nicht gefunden: " + filename);
		}

		try {
			this.setIp(server[0]);
			this.setPort(server[1]);
			
			Remote remote = Naming.lookup("rmi://" + server[0] + ":" + server[1] + "/validator");
			Communication validator = (Communication) remote;
			this.setConnect(validator);

		} catch (MalformedURLException me) {
			System.err.println("Es konnte kein Server gefunden werden.");
		} catch (NotBoundException nbe) {
			System.err.println("Das Objekt konnte nicht auf dem Server gefunden werden.");
		} catch (RemoteException re) {
			System.err.println(re.getMessage());
		}
	}

	/**
	 * Ueberprueft ob die eingaben des Users gueltig sind und wenn
	 * dies zutrifft wird er in die DB eingetragen.
	 * @author Elia Perenzin
	 * @param newUser {@link User}
	 */
	public void registrieren(User newUser) {
		final Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

		/*
		 * Verschluesselt die 2 Passwoerter des
		 * Benutzers mit Base64 und weist diese
		 * dem Benutzer zu.
		 */
		newUser.setPasswort(CryptUtils.base64encode(newUser.getPasswort()));
		newUser.setPasswort2(CryptUtils.base64encode(newUser.getPasswort2()));
		
		if (newUser.getUsername().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Username muss ausgef\u00fcllt sein!", "Warnung!",
					JOptionPane.ERROR_MESSAGE);
		} else if (newUser.getEmail().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Email muss ausgef\u00fcllt sein!", "Warnung!",
					JOptionPane.ERROR_MESSAGE);
		} else if (!pattern.matcher(newUser.getEmail()).matches()) {
			JOptionPane.showMessageDialog(null,
					"Email ist ung\u00fcltig!", "Warnung!",
					JOptionPane.ERROR_MESSAGE);
		} else if (newUser.getPasswort().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Passwort muss ausgef\u00fcllt sein", "Warnung!",
					JOptionPane.ERROR_MESSAGE);
		} else if (!(newUser.getPasswort().equals(newUser.getPasswort2()))) {
			JOptionPane.showMessageDialog(null,
					"Passw\u00f6rter stimmen nicht \u00fcberein",
					"Warnung!", JOptionPane.ERROR_MESSAGE);
		} else
			try {
				if (Client.getInstance().getConnect().getAllUser().contains(newUser.getUsername())) {
					JOptionPane.showMessageDialog(null,
							"Username ist bereits vergeben", "Warnung!",
							JOptionPane.ERROR_MESSAGE);
				} else {
					Client.getInstance().getConnect().registration(newUser);
					JOptionPane.showMessageDialog(null,
							"Sie wurden erfolgreich registriert",
							"Registration!", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | RemoteException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Ueberprueft ob die die Kombination aus Username und Passwort
	 * uebereinstimmen.
	 * beigelegt
	 * @param user {@link User}
	 * @author Elia Perenzin
	 */
	public void login(User user) {

		if (user.getUsername().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Username muss ausgef\u00fcllt sein", "Warnung!",
					JOptionPane.ERROR_MESSAGE);
		} else if (user.getPasswort().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Passwort muss ausgef\u00fcllt sein", "Warnung!",
					JOptionPane.ERROR_MESSAGE);
		} else
			try {
				if (Client.getInstance().getConnect().getOnlinePlayers().contains(user.getUsername())) {
					JOptionPane.showMessageDialog(null,
							"Der Benutzer "  + user.getUsername() + " ist bereits angemeldet!", "Warnung!",
							JOptionPane.ERROR_MESSAGE);
				} else {
					user.setPasswort(CryptUtils.base64encode(user.getPasswort()));
					if (!(MBController.getInstance().login(user.getUsername()).getPasswort().equals(user.getPasswort()))) {
						JOptionPane.showMessageDialog(null,
							"Passwort und Username stimmen nicht \u00fcberein",
							"Warnung!", JOptionPane.ERROR_MESSAGE);
					} else if (!user.getVersion().equals(Client.getInstance().getConnect().getVersion())) {
						JOptionPane.showMessageDialog(null,
								"Bitte aktualisiere die Anwendung auf die neuste Version (" 
								+ Client.getInstance().getConnect().getVersion() + ")", "Warnung!", JOptionPane.ERROR_MESSAGE);
					} else {
						/*
						 * Entfernt den Spieler aus der onlinePlayers Liste, wenn dieser
						 * die Anwendung schliesst.
						 */
						Runtime.getRuntime().addShutdownHook(new Thread() {
							public void run() {
								try {
									Client.getInstance().getConnect().removeOnlinePlayer(user.getUsername());
								} catch (RemoteException e) {
									e.printStackTrace();
								}
							}
						});
						
						/*
						 * Fuegt den neuen Spieler in die onlinePlayers
						 * Liste hinzu.
						 */
						Client.getInstance().getConnect().addOnlinePlayer(user.getUsername());
						
						Client.getFrame().dispose();
						new HomeView(user);
					}
				}
			} catch (HeadlessException | RemoteException e) {
				e.printStackTrace();
			}
	}

	/* **GETTER und SETTER** */
	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public Communication getConnect() {
		return connect;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setConnect(Communication connect) {
		this.connect = connect;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		Client.frame = frame;
	}
}
