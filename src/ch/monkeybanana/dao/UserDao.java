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
	 * Methode um auslesen aller User
	 * @author Elia Perenzin
	 * @return Liste mit allen User {@link User}
	 * @throws SQLException
	 */
	public abstract List<User> findAllUsers() throws SQLException;
	
	/**
	 * Methode um einen neuen User einzutragen
	 * @author Elia Perenzin
	 * @param user {@link User}
	 * @throws SQLException
	 */
	public abstract void registrieren(User user) throws SQLException;
}
