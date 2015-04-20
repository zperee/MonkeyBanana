package ch.monkeybanana.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.regex.Pattern;

import ch.monkeybanana.message.EmailError;
import ch.monkeybanana.message.InvalidUsername;
import ch.monkeybanana.message.LoginError;
import ch.monkeybanana.message.PasswordEmpity;
import ch.monkeybanana.message.PasswordError;
import ch.monkeybanana.message.RegistrSuccess;
import ch.monkeybanana.message.UsernameEmpty;
import ch.monkeybanana.model.User;
import ch.monkeybanana.util.CryptUtils;
import ch.monkeybanana.view.HomeView;

public class Client {

	private static Client instance = new Client();

	private static Client client;
	private String ip;
	private int port;
	private Validator connect;

	public static Client getInstance() {
		if (instance == null){
			instance = new Client();
		}
		return Client.instance;
	}

	private Client() {

		this.setIp("localhost");
		this.setPort(1258);

		try {
			Remote remote = Naming.lookup("rmi://" + ip + ":" + port
					+ "/validator");
			Validator validator = (Validator) remote;
			this.setConnect(validator);

		}
		catch (MalformedURLException me) {
			System.err.println("rmi://" + ip + ":" + port
					+ "/validator is not a valid URL");
		}
		catch (NotBoundException nbe) {
			System.err.println("Could not find requested object on the server");
		}
		catch (RemoteException re) {
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
			new PasswordEmpity();
		}
		else {
			newUser.setPasswort(CryptUtils.base64encode(newUser.getPasswort()));
			newUser.setPasswort2(CryptUtils.base64encode(newUser.getPasswort2()));

			if (newUser.getPasswort().equals(newUser.getPasswort2())) {
				if (newUser.getUsername().isEmpty()) {
					new UsernameEmpty();
				}
				else {
					if (newUser.getEmail().isEmpty()) {
						new EmailError();
					}
					else {
						if (!pattern.matcher(newUser.getEmail()).matches()) {
							new EmailError();
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
									new InvalidUsername();
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
								new RegistrSuccess();
							}
						}
					}
				}
			}
			else {
				new PasswordError();
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
			new UsernameEmpty();
		}
		else {
			if (user.getPasswort().isEmpty()) {
				new PasswordEmpity();
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
							new HomeView();
							try {
								user2.setUsername(dbUser.getUsername());
								Client.getInstance().getConnect().login(user2);
							}
							catch (RemoteException e) {
								e.printStackTrace();
							}
							login = true;
						}
					}
				}
				if (login == false) {
					new LoginError();
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

	public int getPort() {
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

	public void setPort(int port) {
		this.port = port;
	}

	public void setConnect(Validator connect) {
		this.connect = connect;
	}
}
