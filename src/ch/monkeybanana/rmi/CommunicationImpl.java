package ch.monkeybanana.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.game.Banana;
import ch.monkeybanana.game.GameWindow;
import ch.monkeybanana.model.User;
import ch.monkeybanana.view.ServerView;

/**
 * Implementierte Methoden des Interfaces Communication fuer die 
 * Kommunikation ziwschen den Clients mit dem Server
 * @author Dominic Pfister, Elia Perenzin CommunicationImpl.java Copyright
 * Berufsbildungscenter MonkeyBanana 2015
 */
public class CommunicationImpl extends UnicastRemoteObject implements Communication {

	//Insanzvaroablen
	private static final long serialVersionUID = -6227411205842232875L;

	private GameWindow game;
	private List<Banana> bananen = new ArrayList<Banana>();
	
	private int scorePlayer1 = 0;
	private int scorePlayer2 = 0;
	private int rundenZahl = 0;
	private int slots = 0;
	
	private boolean finishedGame, isHit, serverReady;
	private boolean serverStatus = true;
	
	private String playerName1;
	private String playerName2;
	
	private String karte;
	private String version;
	
	private List<String> onlinePlayers = new ArrayList<String>();

	/**
	 * Konstrukor fuer CommunicationImpl
	 * 
	 * @author Elia Perenzin
	 * @throws RemoteException
	 */
	public CommunicationImpl() throws RemoteException {
		version = new String("1.1");
		
		this.setPlayerName1("null");
		this.setPlayerName2("null");
		
		new ServerView();
		
		User system = new User();
		system.setUsername("SYSTEM");
		this.setGame(new GameWindow(system, 0));
		
		this.setServerReady(true);
	}

	/**
	 * Methode fuer die Registration eines Users auf dem Server
	 * 
	 * @author Elia Perenzin
	 * @param newUser
	 *            {@link User}
	 * @throws RemoteException
	 */
	public void registration(User newUser) throws RemoteException {
		MBController.getInstance().registrieren(newUser);
	}

	/**
	 * Methode fuer das Login auf dem Server
	 * 
	 * @author Elia Perenzin
	 * @param newUser
	 *            {@link User}
	 * @throws RemoteException
	 */
	@Override
	public void login(String username) throws RemoteException {
		MBController.getInstance().login(username);	
	}


	/**
	 * Methode um alle User aus der DB zulesen
	 * 
	 * @author Elia Perenzin
	 * @return List<{@link User}>
	 * @throws RemoteException
	 */
	public synchronized List<String> getAllUser() throws RemoteException {
		try {
			return MBController.getUserDao().findAllUsernames();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public synchronized void tellPosition(int x, int y, int ownPlayerNr) throws RemoteException {
		this.setServerReady(false);
		try {
			this.getGame().getEnt().getPlayerArray().get(ownPlayerNr).setX(x);
			this.getGame().getEnt().getPlayerArray().get(ownPlayerNr).setY(y);
		} catch (IndexOutOfBoundsException e) {
			this.setFinishedGame(true);
		}
	}

	@Override
	public synchronized int getPosition(char XorY, int ownPlayerNr) throws RemoteException {
		try {
		if (ownPlayerNr == 0) {
			if (XorY == 'x') {
				return this.getGame().getEnt().getPlayerArray().get(1).getX();
			}
			else if (XorY == 'y') {
				return this.getGame().getEnt().getPlayerArray().get(1).getY();
			}

		}
		else if (ownPlayerNr == 1) {
			if (XorY == 'x') {
				return this.getGame().getEnt().getPlayerArray().get(0).getX();
			}
			else if (XorY == 'y') {
				return this.getGame().getEnt().getPlayerArray().get(0).getY();
			}

		}
		} catch (IndexOutOfBoundsException e) {
			this.setFinishedGame(true);
		}

		return 0;
	}

	@Override
	public synchronized void tellBanana(Banana banana) throws RemoteException {
		this.getBananen().add(banana);
	}

	/**
	 * see {@link Communication#getBanana(int)}
	 */
	@Override
	public synchronized Banana getBanana(int playerNr) throws RemoteException {
		if (this.getBananen().size() != 0) {
			
			try {
			for (Banana b : this.getBananen()) {
				if (b.getOwner() != playerNr) {
					this.getBananen().remove(b);
					return b;
				}

			}	
			} catch (Exception e) {
				System.err.println("Fehler 3  //CommunicationImpl 168");
			}
		
		}
		return null;
	}

	@Override
	public int getSlots() throws RemoteException {
		return this.slots;
	}
	
	@Override
	public void setSlots(int slots) {
		this.slots = slots;
	}

	@Override
	public boolean score(int playerNr) throws RemoteException {
		
		if (playerNr == 0) {
			this.setScorePlayer1(this.getScorePlayer1() + 1);
		}
		else if (playerNr == 1) {
			this.setScorePlayer2(this.getScorePlayer2() + 1);
		}
		this.setHit(true);


		return isHit;
	}
	
	@Override
	public void setHit(boolean isHit) throws RemoteException {
		this.isHit = isHit;
	}

	@Override
	public boolean isHit() throws RemoteException {
		return isHit;
	}

	@Override
	public int getScore(int playerNr) throws RemoteException {
		int score = 0;
		
		if (playerNr == 0) {
			score = this.getScorePlayer1();
		} else if (playerNr == 1) {
			score = this.getScorePlayer2();
		}
		return score;
		
	}
	
	@Override
	public int[] getResult() throws RemoteException {
		int [] score = new int[2];
		
		score[0] = this.getScore(0);
		score[1] = this.getScore(1);
	return score;
	}

	@Override
	public void setScore(int playerNr, int score) throws RemoteException {
		if (playerNr == 0) {
			this.setScorePlayer1(score);
		} else if (playerNr == 1) {
			this.setScorePlayer2(score);
		}
	}
	
	@Override
	public int getRundenzahl() throws RemoteException {
		return this.rundenZahl;
	}
	
	@Override
	public void setRundenzahl(int rundenzahl) throws RemoteException {
		this.rundenZahl = rundenzahl;
	}
	
	@Override
	public void restartServer() throws RemoteException {
		User system = new User();
		system.setUsername("SYSTEM");
		
		this.getGame().getEnt().getTimer().stop();
		this.getGame().getEnt().getPlayerArray().clear();
		this.setGame(new GameWindow(system, 0));
		
		this.karte = "null";
		
		this.setSlots(0);
		this.setPlayerName1("null");
		this.setPlayerName2("null");
		
		this.setScorePlayer1(0);
		this.setScorePlayer2(0);
		this.setRundenzahl(0);
		
		this.setServerReady(true);
	}
	
	@Override
	public boolean getFinishedGame() throws RemoteException {
		return this.finishedGame;
	}

	@Override
	public void setFinishedGame(boolean finishedGame) throws RemoteException {
		this.finishedGame = finishedGame;
	}
	
	@Override
	public String getPlayer(int playerNr) throws RemoteException {

		if (playerNr == 0) {
			return this.playerName1;
		} else if (playerNr == 1) {
			return this.playerName2;
		}
		return null;
	}

	@Override
	public void setPlayer(String name, int playerNr) throws RemoteException {

		if (playerNr == 0) {
			this.playerName1 = name;
		} else if (playerNr == 1) {
			this.playerName2 = name;
		}
		
	}

	@Override
	public void logoutSpiel() throws RemoteException {
		this.setSlots(this.getSlots() - 1);
	}
	
	@Override
	public List<String> getOnlinePlayers() throws RemoteException {
		return this.onlinePlayers;
	}
	
	@Override
	public void addOnlinePlayer(String player) throws RemoteException {
		this.onlinePlayers.add(player);
		
	}
	
	@Override
	public void removeOnlinePlayer(String player) throws RemoteException {
		this.onlinePlayers.remove(player);
	}

	@Override
	public boolean isServerReady() throws RemoteException {
		return this.serverReady;
	}
	
	@Override
	public void setServerStatus(boolean status) throws RemoteException {
		this.serverStatus = status;
	}

	@Override
	public boolean isServerStatus() throws RemoteException {
		return this.serverStatus;
	}
	
	@Override
	public String getVersion() throws RemoteException {
		return version;
	}
	
	@Override
	public void setKarte(String karte) throws RemoteException {
		this.karte = karte;
	}

	@Override
	public String getKarte() throws RemoteException {
		return this.karte;
	}

	public GameWindow getGame() {
		return game;
	}

	public void setGame(GameWindow game) {
		this.game = game;
	}

	public List<Banana> getBananen() {
		return bananen;
	}

	public void setBananen(List<Banana> bananen) {
		this.bananen = bananen;
	}

	public int getScorePlayer1() {
		return scorePlayer1;
	}

	public int getScorePlayer2() {
		return scorePlayer2;
	}

	public void setScorePlayer1(int scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}

	public void setScorePlayer2(int scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}

	public int getRundenZahl() {
		return rundenZahl;
	}

	public void setRundenZahl(int rundenZahl) {
		this.rundenZahl = rundenZahl;
	}

	public boolean isFinishedGame() {
		return finishedGame;
	}

	public String getPlayerName1() {
		return playerName1;
	}

	public void setPlayerName1(String playerName1) {
		this.playerName1 = playerName1;
	}

	public String getPlayerName2() {
		return playerName2;
	}

	public void setPlayerName2(String playerName2) {
		this.playerName2 = playerName2;
	}

	public void setOnlinePlayers(List<String> onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}
	
	private void setServerReady(boolean ready) {
		this.serverReady = ready;
	}
}