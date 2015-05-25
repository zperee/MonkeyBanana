package ch.monkeybanana.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ch.monkeybanana.game.Banana;
import ch.monkeybanana.model.User;

/**
 * Interface fuer die Kommunikation zwischen den 
 * Clients und dem Server. Enthaelt alle abstrakten Methoden
 * die dazu bennoetigt werden.
 * @author Dominic Pfister, Elia Perenzin
 */
public interface Validator extends Remote {

	/**
	 * Abstrakte Methode fuer die Registration auf dem Server
	 * @param newUser {@link User}
	 * @throws RemoteException
	 * @author Elia Perenzin
	 */
	public abstract void registration(User newUser) throws RemoteException;
	
	/**
	 * Abstrakte Methode fuer das Login auf dem Server
	 * @param user {@link User}
	 * @throws RemoteException
	 * @author Elia Perenzin
	 */
	public abstract void login(String username) throws RemoteException;
	
	/**
	 * Abstrakte Methode um alle User aus der DB zulesen
	 * @return List <{@link User}>
	 * @throws RemoteException
	 * @author Elia Perenzin
	 */
	public abstract List<String> getAllUser() throws RemoteException;
	
	/**
	 * Abstrakte Methode um auf dem Server die LogOut Nachricht
	 * beim Verlassen des Spieles anzuzeigen.
	 * @param user {@link User}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void logoutSpiel() throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode. Gibt die Slot Anzahl auf
	 * dem Server zurueck.
	 * @return slots
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract int getSlots() throws RemoteException;
	
	/**
	 * Abstrakte Setter Methode. Setzt die Slot Anzahl auf
	 * dem Server.
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void setSlots(int slots) throws RemoteException;
	
	/**
	 * Abstrakte Methode. Setzt auf dem Server die Koordinaten des
	 * Spielers.
	 * @param x {@link int}
	 * @param y {@link int}
	 * @param ownPlayerNr {@link int}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void tellPosition(int x, int y, int ownPlayerNr) throws RemoteException;
	
	/**
	 * Abstrakte Methode. Gibt die Position des Spielers auf dem Server
	 * zurueck.
	 * @param XorY
	 * @param ownPlayerNr
	 * @return
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract int getPosition(char XorY, int ownPlayerNr) throws RemoteException; 
	
	/**
	 * Abstrakte Methode. Fuegt eine neue Banane in das bananenArray
	 * auf dem Server hinzu.
	 * @param banana {@link Banana}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void tellBanana(Banana banana) throws RemoteException;
	
	/**
	 * Abstrakte Methode. Gibt eine Banane aus dem bananenArray auf
	 * dem Server zurueck.
	 * @param playerNr {@link int}
	 * @return {@link Banana}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract Banana getBanana(int playerNr) throws RemoteException;
	
	/**
	 * Abstrakte Methode. Erhoeht die Punktzahl des Spielers
	 * auf dem Server.
	 * @param playerNr
	 * @return {@link boolean}
	 * @throws RemoteException
	 * @author Dominic Pfister, Elia Perenzin
	 */
	public abstract boolean score(int playerNr) throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer isHit.
	 * @return {@link boolean}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract boolean isHit() throws RemoteException;
	
	/**
	 * Abstrakte Setter Methode fuer isHit.
	 * @return {@link boolean}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void setHit(boolean isHit) throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer die Punktzahl des Spielers.
	 * @param playerNr {@link int}
	 * @return {@link int}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract int getScore(int playerNr) throws RemoteException;
	
	/**
	 * Gibt die Liste mit den beiden Spielerpunktzahlen zurueck.
	 * @return int[]
	 * @throws RemoteException
	 * @author Elia Perenzin
	 */
	public abstract int[] getResult() throws RemoteException;
	
	/**
	 * Abstrakte Setter Methode fuer Punktzahl des Spielers.
	 * @param playerNr {@link int}
	 * @param score {@link int}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void setScore(int playerNr, int score) throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer rundenZahl.
	 * @return {@link int}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract int getRundenzahl() throws RemoteException;
	
	/**
	 * Abstrakte Setter Methode fuer rundenZahl.
	 * @param rundenzahl {@link int}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void setRundenzahl(int rundenzahl) throws RemoteException;
	
	/**
	 * Abstrakte Methode. Legt einen neuen Benutzer mit dem Namen
	 * "SYSTEM" an, loescht das SpielerArray, setzt die Punktzahl der
	 * beiden Spieler zurueck, setzt die rundenZahl auf 0 zurueck und
	 * wartet auf Beginn eines neuen Spieles.
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void restartServer() throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer finishedGame.
	 * @return {@link boolean}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract boolean getFinishedGame() throws RemoteException;
	
	/**
	 * Abstrakte Setter Methode fuer finishedGame.
	 * @param finishedGame {@link int}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void setFinishedGame(boolean finishedGame) throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer den Namen eines Spielers.
	 * @param playerNr {@link int}
	 * @return {@link String}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract String getPlayer(int playerNr) throws RemoteException;
	
	/**
	 * Astrakte Setter Methode fuer den Namen eines Spielers.
	 * @param name {@link String}
	 * @param playerNr {@link int}
	 * @throws RemoteException
	 * @author Dominic Pfister
	 */
	public abstract void setPlayer(String name, int playerNr) throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer die angemeldeten Spieler.
	 * @author Dominic Pfister
	 * @return
	 * @throws RemoteException
	 */
	public abstract List<String> getOnlinePlayers() throws RemoteException;
	
	/**
	 * Fügt einen Spieler in die onlinePlayers Liste hinzu.
	 * @author Dominic Pfister
	 * @param player {@link String}
	 * @throws RemoteException
	 */
	public abstract void addOnlinePlayer(String player) throws RemoteException;
	
	/**
	 * Entfernt den Spieler aus der onlinePlayers Liste.
	 * @author Dominic Pfister
	 * @param player {@link String}
	 * @throws RemoteException
	 */
	public abstract void removeOnlinePlayer(String player) throws RemoteException;
	
	/**
	 * Abstrakte Setter Methode fuer den Boolean serverStatus.
	 * @author Dominic Pfister, Elia Perenzin
	 * @param ready
	 * @throws RemoteException
	 */
	public abstract void setServerStatus(boolean ready) throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer den Boolean serverStatus.
	 * @author Dominic Pfister, Elia Perenzin
	 * @param ready
	 * @throws RemoteException
	 */
	public abstract boolean isServerStatus() throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer den Boolean serverReady.
	 * @return boolean
	 * @author Dominic Pfister, Elia Perenzin
	 * @throws RemoteException
	 */
	public abstract boolean isServerReady() throws RemoteException;
	
	/**
	 * Abstrakte Getter Methode fuer die Version des Servers.
	 * @author Dominic Pfister
	 * @return String
	 * @throws RemoteException
	 */
	public abstract String getVersion() throws RemoteException;
}
