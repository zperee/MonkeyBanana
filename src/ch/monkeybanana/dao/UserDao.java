package ch.monkeybanana.dao;

import java.sql.SQLException;
import java.util.List;

import ch.monkeybanana.model.User;

/**
 * Interface fuer die Tabelle User
 * @author Dominic Pfister, Elia Perenzin UserDao.java Copyright 
 * Berufsbildungscenter MonkeyBanana 2015
 */
public interface UserDao {

	/**
	 * Methode um alle Benutzernamen aus der DB zu lesen.
	 * @author Elia Perenzin, Dominic Pfister
	 * @return Liste mit allen Usernamen {@link String}
	 * @throws SQLException
	 */
	public abstract List<String> findAllUsernames() throws SQLException;
	
	/**
	 * Methode um alle Benutzer aus der DB zu lesen. Für jeden
	 * User wird ein neues User Objekt erstellt.
	 * @author Elia Perenzin, Dominic Pfister
	 * @return Liste mit allen Usernamen {@link User}
	 * @param username {@link String}
	 * @throws SQLException
	 */
	public abstract User login(String username) throws SQLException;
	
	/**
	 * Methode um einen neuen User einzutragen
	 * @author Elia Perenzin
	 * @param user {@link User}
	 * @throws SQLException
	 */
	public abstract void registrieren(User user) throws SQLException;
}