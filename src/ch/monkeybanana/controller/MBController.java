package ch.monkeybanana.controller;

import java.sql.SQLException;

import javax.swing.JLabel;

import ch.monkeybanana.dao.UserDao;
import ch.monkeybanana.dao.UserJDBCDao;
import ch.monkeybanana.model.User;

/**
 * Hier ist die Kommunikation zwischen View und Datenbank implementiert
 * Darf nur von Server benutzt werden! Ist eine Singeltonklasse. Man
 * muss zuerst immer die Methode getInstance() aufrufen.
 * @author Dominic Pfister, Elia Perenzin MBController.java Copyright
 * Berufsbildungscenter MonkeyBanana 2015
 */
public class MBController {
	
	//Instanzvariablen
	private static MBController instance = new MBController();
	private final static UserDao USER_DAO = new UserJDBCDao();

	private int slotsBesetzt = 0;

	/**
	 * Konstruktor der Klasse MBController nur Privat aufrufbar,
	 * weil es eine singelton Klasse ist.
	 */
	private MBController() {
		
	}
	
	/**
	 * Gibt die Instanz der Sigeltonklasse MBController zurueck.
	 * Diese Methode muss immer aufgerufen werden, wenn man eine 
	 * weitere Methode dieser Klasse verwenden moechte.
	 * @return instance {@link MBController} 
	 */
	public static MBController getInstance() {
		return MBController.instance;
	}

	/**
	 * Hier wird ueberprueft ob die eingaben des Users gueltig sind und wenn
	 * dies zutrifft wird er in die DB eingetragen
	 * @author Elia Perenzin
	 * @param newUser {@link User}
	 */
	public void registrieren(User newUser, JLabel console) {
		try{
			USER_DAO.registrieren(newUser);
			console.setText(console.getText() + "Benutzer "+ newUser.getUsername() + " hat sich registriert." + "<br>");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * Hier wird das Login durchgefuehrt
	 * @param user {@link User}
	 * @return user {@link User}
	 */
	public User login(User user, JLabel console) {
			console.setText(console.getText() + user.getUsername() + " hat den Server betreten." + "<br/>");
			return user;
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