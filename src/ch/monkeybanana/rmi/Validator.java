package ch.monkeybanana.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ch.monkeybanana.model.User;

public interface Validator extends Remote {

	/**
	 * Abstrakte Methode fuer die Registration auf dem Server
	 * @param newUser {@link User}
	 * @throws RemoteException
	 */
	public abstract void registration(User newUser) throws RemoteException;
	
	/**
	 * Abstrakte Methode fuer das Login auf dem Server
	 * @param user {@link User}
	 * @throws RemoteException
	 */
	public abstract void login(User user) throws RemoteException;
	
	/**
	 * Abstrakte Methode um alle User aus der DB zulesen
	 * @return List <{@link User}>
	 * @throws RemoteException
	 */
	public abstract List<User> getAllUser() throws RemoteException;
}
