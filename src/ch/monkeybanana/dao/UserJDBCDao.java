package ch.monkeybanana.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.monkeybanana.model.User;

/**
 * Implementiert alle Abfragen des UserDao
 * @author Dominic Pfister, Elia Perenzin
 * UserJDBCDao.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */
public class UserJDBCDao extends Database implements UserDao {
	//Variable fuer Verbindung
	private Connection con = null;
	
	/**
	 * Auslesen aller User aus der DB
	 * @author Elia Perenzin
	 * @return Liste aller User
	 * @throws SQLException
	 */
	public List<User> findAllUsers() throws SQLException {
		String sql = "SELECT * FROM USER";
		List<User> p = new ArrayList<User>();
		
		con = getCon();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
			User user = new User();
			user.setUsername(rs.getString("ID_User"));
			user.setPasswort(rs.getString("Passwort"));
			p.add(user);
		}
		closeCon();
		return p;
	}

	/**
	 * Eintragen eines neuen Users in DB
	 * @author Elia Perenzin
	 * @param user
	 * @throws SQLException
	 */
	public void registrieren(User user) throws SQLException {
		String sql = "INSERT INTO USER (ID_User, Email, Passwort) VALUES (?, ?, ?)";
		con = getCon();
		ps = con.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getPasswort());
		ps.executeUpdate();
		closeCon();
	}

}
