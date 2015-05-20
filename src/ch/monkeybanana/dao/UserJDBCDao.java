package ch.monkeybanana.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.monkeybanana.model.User;

/**
 * Implementiert alle Abfragen des UserDao
 * @author Dominic Pfister, Elia Perenzin UserJDBCDao.java Copyright 
 * Berufsbildungscenter MonkeyBanana 2015
 */
public class UserJDBCDao extends Database implements UserDao {
	//Variable fuer Verbindung
	private Connection con = null;
	
	public List<User> findAllUsers() throws SQLException {
		String sql = "SELECT * FROM user"; //Query
		List<User> p = new ArrayList<User>();
		
		con = getCon(); //holt alle infos zu DB verbindung 
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
			User user = new User();
			user.setUsername(rs.getString("ID_User"));
			user.setPasswort(rs.getString("Passwort"));
			p.add(user);
		}
		closeCon(); //schliesst die Verbindugn zur DB wieder
		return p;
	}

	public void registrieren(User user) throws SQLException {
		String sql = "INSERT INTO user (ID_User, Email, Passwort) VALUES (?, ?, ?)"; //Query
		
		con = getCon(); //holt alle Infos zur DB verbindung
		ps = con.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getPasswort());
		ps.executeUpdate();
		
		closeCon(); //schliesst die Verbindugn zur DB
	}
}
