package ch.monkeybanana.controller;

import java.sql.SQLException;

import ch.monkeybanana.dao.Game_StatisticDao;
import ch.monkeybanana.dao.Game_StatisticJDBCDao;
import ch.monkeybanana.dao.UserDao;
import ch.monkeybanana.dao.UserJDBCDao;
import ch.monkeybanana.model.User;

/**
 * Hier ist die Kommunikation zwischen View und Datenbank implementiert Darf nur
 * von Server benutzt werden! Ist eine Singeltonklasse. Man muss zuerst immer
 * die Methode getInstance() aufrufen.
 * 
 * @author Dominic Pfister, Elia Perenzin MBController.java Copyright
 *         Berufsbildungscenter MonkeyBanana 2015
 */
public class MBController {

	// Instanzvariablen
	private static MBController instance = new MBController();
	private final static UserDao USER_DAO = new UserJDBCDao();
	private final static Game_StatisticDao GAME_STATISTIC_DAO = new Game_StatisticJDBCDao();

	private int slotsBesetzt = 0;

	/**
	 * Konstruktor der Klasse MBController nur Privat aufrufbar, weil es eine
	 * singelton Klasse ist.
	 * @author Elia Perenzin
	 */
	private MBController() {
	}

	/**
	 * Gibt die Instanz der Sigeltonklasse MBController zurueck. Diese Methode
	 * muss immer aufgerufen werden, wenn man eine weitere Methode dieser Klasse
	 * verwenden moechte.
	 * @author Elia Perenzin
	 * 
	 * @return instance {@link MBController}
	 */
	public static MBController getInstance() {
		return MBController.instance;
	}

	/**
	 * Hier wird ueberprueft ob die eingaben des Users gueltig sind und wenn
	 * dies zutrifft wird er in die DB eingetragen
	 * 
	 * @author Elia Perenzin
	 * @param newUser
	 *            {@link User}
	 */
	public void registrieren(User newUser) {
		try {
			USER_DAO.registrieren(newUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO
	 * 
	 * @author Elia Perenzin
	 * @param player1
	 * @param player2
	 * @param score1
	 * @param score2
	 * @param map
	 * @param modus
	 * @param winner
	 */
	public void setResult(int score1, int score2, String player1,
			String player2, int map, int modus, String winner) {
		try {
			GAME_STATISTIC_DAO.setResult(score1, score2, player2, player1, map,
					modus, winner);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public double[] getResult(User user) {
		try {
			return GAME_STATISTIC_DAO.getResult(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Hier wird das Login durchgefuehrt
	 * @author Elia Perenzin
	 * @param username
	 *            {@link String}
	 * @return user {@link User}
	 */
	public User login(String username) {
		try {
			return USER_DAO.login(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* **GETTER und SETTER** */
	public static UserDao getUserDao() {
		return USER_DAO;
	}

	public int getSlotsBesetzt() {
		return slotsBesetzt;
	}

	public void setSlotsBesetzt(int slotsBesetzt) {
		this.slotsBesetzt = slotsBesetzt;
	}
}