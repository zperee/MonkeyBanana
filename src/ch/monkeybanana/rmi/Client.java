package ch.monkeybanana.rmi;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.monkeybanana.model.User;
import ch.monkeybanana.util.CryptUtils;
import ch.monkeybanana.view.HomeView;
import ch.monkeybanana.view.LoginView;

public class Client {

	private static Client instance = new Client();

	private static Client client;
	private String ip;
	private String port;
	private Validator connect;
	
	private static JFrame frame = new JFrame();
	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return Client.instance;
	}
	
	public static void main(String[] args) {
		start();
	}
	
	public static void start() { 
		setFrame(new LoginView());
	}
	
	private Client() {

		String filename = "Serverdata.csv"; // File mit allen Informationen
		File file = new File(filename);
		String [] server = new String[2];
		
		try {
			Scanner inputStream = new Scanner(file);
			int i = 0;
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				server[i] = data;
				i++;
			}
			inputStream.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File nicht gefunden");
		}
		

		try {
			this.setIp(server[0]);
			this.setPort(server[1]);
			Remote remote = Naming.lookup("rmi://" + server[0] + ":"
					+ server[1] + "/validator");
			Validator validator = (Validator) remote;
			this.setConnect(validator);

		} catch (MalformedURLException me) {
			System.err.println("rmi://" + ip + ":" + port
					+ "/validator is not a valid URL");
		} catch (NotBoundException nbe) {
			System.err.println("Could not find requested object on the server");
		} catch (RemoteException re) {
			System.err.println(re.getMessage());
		}

	}

	/**
	 * Hier wird ueberprueft ob die eingaben des Users gueltig sind und wenn
	 * dies zutrifft wird er in die DB eingetragen
	 * @author Elia Perenzin
	 * @param newUser
	 *            {@link User}
	 */
	public void registrieren(User newUser) {
		List<User> dbUsers = null;
		boolean userAlreadyExists = true;
		final Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

		if (newUser.getPasswort().isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Passwort muss ausgef\u00fcllt sein",
                    "Warnung!",
                    JOptionPane.ERROR_MESSAGE);
		}
		else {
			newUser.setPasswort(CryptUtils.base64encode(newUser.getPasswort()));
			newUser.setPasswort2(CryptUtils.base64encode(newUser.getPasswort2()));

			if (newUser.getPasswort().equals(newUser.getPasswort2())) {
				if (newUser.getUsername().isEmpty()) {
					JOptionPane.showMessageDialog(null,
                            "Username muss ausgef\u00fcllt sein!",
                            "Warnung!",
                            JOptionPane.ERROR_MESSAGE);
				}
				else {
					if (newUser.getEmail().isEmpty()) {
						JOptionPane.showMessageDialog(null,
	                            "Email muss ausgef\u00fcllt sein!",
	                            "Warnung!",
	                            JOptionPane.ERROR_MESSAGE);
					}
					else {
						if (!pattern.matcher(newUser.getEmail()).matches()) {
							JOptionPane.showMessageDialog(null,
		                            "Email ist ung\u00fcltig!",
		                            "Warnung!",
		                            JOptionPane.ERROR_MESSAGE);
						}
						else {

							try {
								dbUsers = Client.getInstance().getConnect()
										.getAllUser();
							}
							catch (RemoteException e1) {
								e1.printStackTrace();
							}

							for (User dbUser : dbUsers) {
								if (newUser.getUsername().equals(
										dbUser.getUsername())) {
									JOptionPane.showMessageDialog(null,
											"Username ist bereits vergeben",
					                        "Warnung!",
					                        JOptionPane.ERROR_MESSAGE);
									userAlreadyExists = true;
									break;
								}
								else {
									userAlreadyExists = false;
								}
							}
							if (userAlreadyExists == false) {
								try {
									Client.getInstance().getConnect()
											.registration(newUser);
								}
								catch (RemoteException e) {
									e.printStackTrace();
								}
								JOptionPane.showMessageDialog(null,
				                        "Sie wurden erfolgreich registriert",
				                        "Registration!",
				                        JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null,
                        "Passw\u00f6rter stimmen nicht \u00fcberein",
                        "Warnung!",
                        JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Hier wird ueberprueft ob die die Kombination aus Username und Passwort uebereinstimmen
	 * @param user {@link User}
	 * @return login Boolean
	 */
	public void login(User user) {
		List<User> dbUsers = null;
		User user2 = new User();
		boolean login = false;

		if (user.getUsername().isEmpty()) {
			JOptionPane.showMessageDialog(null,
                    "Username muss ausgef\u00fcllt sein",
                    "Warnung!",
                    JOptionPane.ERROR_MESSAGE);
		}
		else {
			if (user.getPasswort().isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Passwort muss ausgef\u00fcllt sein",
                        "Warnung!",
                        JOptionPane.ERROR_MESSAGE);
			}
			else {
				user.setPasswort(CryptUtils.base64encode(user.getPasswort()));

				try {
					dbUsers = Client.getInstance().getConnect().getAllUser();
				}
				catch (RemoteException e) {
					e.printStackTrace();
				}

				for (User dbUser : dbUsers) {
					if (user.getUsername().equals(dbUser.getUsername())) {
						if (user.getPasswort().equals(dbUser.getPasswort())) {
							
							try {
								user2.setUsername(dbUser.getUsername());
								Client.getInstance().getConnect().login(user2);
							}
							catch (RemoteException e) {
								e.printStackTrace();
							}
							login = true;
							getFrame().dispose();
							new HomeView(user2);
						}
					}
				}
				if (login == false) {
					JOptionPane.showMessageDialog(null,
							"Passwort und Username stimmen nicht \u00fcberein",
	                        "Warnung!",
	                        JOptionPane.ERROR_MESSAGE); 
				}
			}
		}
	}

	// Getter
	public static Client getClient() {
		return client;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public Validator getConnect() {
		return connect;
	}

	// Setter
	public static void setClient(Client client) {
		Client.client = client;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setConnect(Validator connect) {
		this.connect = connect;
	}

	public JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		Client.frame = frame;
	}
}
