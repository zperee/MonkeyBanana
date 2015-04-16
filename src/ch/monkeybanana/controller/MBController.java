package ch.monkeybanana.controller;

import java.sql.SQLException;
import java.util.List;

import ch.monkeybanana.dao.UserDao;
import ch.monkeybanana.dao.UserJDBCDao;
import ch.monkeybanana.model.User;
import ch.monkeybanana.util.CryptUtils;

import com.mysql.jdbc.SQLError;

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
	
	public static boolean userAlreadyExists;

	
	
}
 