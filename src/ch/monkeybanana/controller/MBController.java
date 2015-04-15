package ch.monkeybanana.controller;

import java.sql.SQLException;

import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.dao.UserDao;
import ch.monkeybanana.dao.UserJDBCDao;
import ch.monkeybanana.model.User;

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
	
	public void registrieren(User user){
		
			try {
				USER_DAO.registrieren(user);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
}
