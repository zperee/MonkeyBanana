package ch.monkeybanana.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import ch.monkeybanana.dao.UserDao;
import ch.monkeybanana.dao.UserJDBCDao;
import ch.monkeybanana.model.User;
import ch.monkeybanana.util.CryptUtils;

import com.mysql.jdbc.SQLError;

/**
 * Hier ist die Kommunikation zwischen View und Datenbank implementiert
 * @author Dominic Pfister, Elia Perenzin MBController.java Copyright
 *         Berufsbildungscenter MonkeyBanana 2015
 */
public class MBController {
	private static MBController instance = new MBController();
	private final static UserDao USER_DAO = new UserJDBCDao();

	/**
	 * Konstruktor der Klasse GMCController nur Privat
	 */
	private MBController() {
	}

	public static MBController getInstance() {
		return MBController.instance;
	}

	/**
	 * Hier wird ueberprueft ob die eingaben des Users gueltig sind und wenn
	 * dies zutrifft wird er in die DB eingetragen
	 * @author Elia Perenzin
	 * @param User
	 */
	public void registrieren(User newUser) {
		List<User> dbUsers = null;
		boolean userAlreadyExists = true;
		final Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

		if (newUser.getPasswort().isEmpty()) {
			System.out.println("Passwort muss ausgef\u00fcllt sein");
		}
		else {
			newUser.setPasswort(CryptUtils.base64encode(newUser.getPasswort()));
			newUser.setPasswort2(CryptUtils.base64encode(newUser.getPasswort2()));

			if (newUser.getPasswort().equals(newUser.getPasswort2())) {
				if (newUser.getUsername().isEmpty()) {
					System.out.println("Username muss ausgef\u00fcllt sein");
				}
				else {
					if (newUser.getEmail().isEmpty()) {
						System.out.println("Email muss ausgef\u00fcllt sein");
					}
					else {
						if (!pattern.matcher(newUser.getEmail()).matches()) {
							System.out
									.println("Keine gültige Email eingegeben");
						}
						else {
							try {
								dbUsers = USER_DAO.findAllUsers();

								for (User dbUser : dbUsers) {
									if (newUser.getUsername().equals(
											dbUser.getUsername())) {
										System.out
												.println("Username ist bereits vergeben");
										userAlreadyExists = true;
										break;
									}
									else {
										userAlreadyExists = false;
									}
								}
								if (userAlreadyExists == false) {
									USER_DAO.registrieren(newUser);
									System.out
											.println("Sie wurden erfolgreich eingetragen");
								}
							}
							catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			else {
				System.out
						.println("Passw\u00f6rter stimmen nicht \u00fcberein");
			}
		}
	}

	/**
	 * Hier wird das Login durchgefuehrt, wenn dies zutrifft gibt es einen
	 * boolean mit true zuruck
	 * @param user
	 * @return boolean login
	 */
	public boolean login(User user) {
		List<User> dbUsers = null;
		boolean login = false;

		if (user.getUsername().isEmpty()) {
			System.out.println("Bitte Username ausf\u00fcllen");
		}
		else {
			if (user.getPasswort().isEmpty()) {
				System.out.println("Bitte Passwort ausf\u00fcllen");
			}
			else {
				user.setPasswort(CryptUtils.base64encode(user.getPasswort()));

				try {
					dbUsers = USER_DAO.findAllUsers();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}

				for (User dbUser : dbUsers) {
					if (user.getUsername().equals(dbUser.getUsername())) {
						if (user.getPasswort().equals(dbUser.getPasswort())) {
							System.out
									.println("Sie haben sich erfolgreich angemeldet");
							login = true;
						}
					}
				}
				if (login == false) {
					System.out.println("Benutername oder Passwort falsch");
				}
			}
		}
		return login;
	}

}
