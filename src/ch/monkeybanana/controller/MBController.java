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


	public void registrieren (User newUser){
		List<User> dbUsers = null;
		boolean userAlreadyExists = true;
		
		if(newUser.getPasswort().isEmpty()){
			System.out.println("Passwort muss ausgef\u00fcllt sein");
		}
		else{
			newUser.setPasswort(CryptUtils.base64encode(newUser.getPasswort()));
			newUser.setPasswort2(CryptUtils.base64encode(newUser.getPasswort2()));
			
			if(newUser.getPasswort().equals(newUser.getPasswort2())){
				if(newUser.getUsername().isEmpty()){
					System.out.println("Username muss ausgef\u00fcllt sein");
				}
				else{
					if(newUser.getEmail().isEmpty()){
						System.out.println("Email muss ausgef\u00fcllt sein");
					}
					else{
						try {
							dbUsers = USER_DAO.findAllUsers();
							
							for (User dbUser : dbUsers){
								if (newUser.getUsername().equals(dbUser.getUsername())){
									System.out.println("Username ist bereits vergeben");
									break;
								}
								else{
									userAlreadyExists = false;
								}
								
								if (userAlreadyExists == false){
								USER_DAO.registrieren(newUser);
								System.out.println("Sie wurden erfolgreich eingetragen");
								break;
								}
							}
							
						}
						catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			else{
				System.out.println("Passw\u00f6rter stimmen nicht \u00fcberein");
			}
		}
	}
	
}
 