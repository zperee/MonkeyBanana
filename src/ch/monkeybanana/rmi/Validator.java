package ch.monkeybanana.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ch.monkeybanana.game.Banana;
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
	
	public abstract void join(User user) throws RemoteException;
	
	public abstract void logoutServer(User user) throws RemoteException;
	
	public abstract void logoutSpiel(User user) throws RemoteException;
	
	public abstract int getSlots() throws RemoteException;
	
	public abstract void tellPosition(int x, int y, int ownPlayerNr) throws RemoteException;
	
	public abstract int getPosition(char XorY, int ownPlayerNr) throws RemoteException; 
	
	public abstract void tellBanana(Banana banana) throws RemoteException;
	
	public abstract boolean score(int playerNr) throws RemoteException;
	
	/**
	 * @param playerNr
	 * @return
	 * @throws RemoteException
	 */
	public abstract Banana getBanana(int playerNr) throws RemoteException;
	
	public abstract boolean isHit() throws RemoteException;
	
	public abstract void setHit(boolean isHit) throws RemoteException;
	
	public abstract int getScore(int playerNr) throws RemoteException;
	
	public abstract int[] getResult() throws RemoteException;
	
	public abstract void setScore(int playerNr, int score) throws RemoteException;
	
	public abstract int getRundenzahl() throws RemoteException;
	
	public abstract void setRundenzahl(int rundenzahl) throws RemoteException;
	
	public abstract void restartServer() throws RemoteException;
}
