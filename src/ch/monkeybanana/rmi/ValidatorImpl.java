package ch.monkeybanana.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.model.User;

/**
 * Implementierte Methoden des Interfaces Validator fuer Verbindung zu Server
 * @author Dominic Pfister, Elia Perenzin
 * ValidatorImpl.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */
public class ValidatorImpl extends UnicastRemoteObject  implements Validator {

	/**
	 * Konstrukor fuer ValidatorImpl
	 * @author Elia Perenzin
	 * @throws RemoteException
	 */
	public ValidatorImpl() throws RemoteException {
		
	}

	/**
	 * Methode fuer die Registration eines Users auf dem Server
	 * @author Elia Perenzin
	 * @param newUser {@link User}
	 * @throws RemoteException
	 */
	public synchronized void registration(User newUser) throws RemoteException {
		try {
			MBController.getUserDao().registrieren(newUser);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode fuer das Login auf dem Server
	 * @author Elia Perenzin
	 * @param newUser {@link User}
	 * @throws RemoteException
	 */
	public synchronized void login(User user) throws RemoteException {
		try {
			MBController.getUserDao().findAllUsers();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode um alle User aus der DB zulesen
	 * @author Elia Perenzin
	 * @return List<{@link User}>
	 * @throws RemoteException
	 */
	public synchronized List<User> getAllUser() throws RemoteException {
			try {
				return MBController.getUserDao().findAllUsers();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		return null;
	}
}
