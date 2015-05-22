package ch.monkeybanana.rmi;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.game.Banana;
import ch.monkeybanana.game.GameWindow;
import ch.monkeybanana.model.User;
import ch.monkeybanana.view.ServerView;

/**
 * Implementierte Methoden des Interfaces Validator fuer die 
 * Kommunikation ziwschen den Clients mit dem Server
 * @author Dominic Pfister, Elia Perenzin ValidatorImpl.java Copyright
 * Berufsbildungscenter MonkeyBanana 2015
 */
public class ValidatorImpl extends UnicastRemoteObject implements Validator {

	//Insanzvaroablen
	private static final long serialVersionUID = -6227411205842232875L;
	private JLabel consolelabel;
	private JPanel consolepanelRight;

	private JFrame consoleframe;
	private JLabel slotLabel;
	private GameWindow game;
	private List<Banana> bananen = new ArrayList<Banana>();
	
	private int scorePlayer1 = 0;
	private int scorePlayer2 = 0;
	private int rundenZahl = 0;
	private int slots = 0;
	
	private boolean finishedGame, isHit, serverReady;
	
	private String playerName1;
	private String playerName2;
	
	private List<String> onlinePlayers = new ArrayList<String>();

	/**
	 * Konstrukor fuer ValidatorImpl
	 * 
	 * @author Elia Perenzin
	 * @throws RemoteException
	 */
	public ValidatorImpl() throws RemoteException {
		this.setPlayerName1("null");
		this.setPlayerName2("null");
		
		this.setConsoleframe(new ServerView());
		this.getConsoleframe().setTitle("Server Console");
		this.getConsoleframe().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.getConsoleframe().setSize(500, 300);

		this.setConsolelabel(new JLabel("<html>"));
		this.getConsolelabel().setHorizontalTextPosition(SwingConstants.LEFT);

		this.setConsolepanelRight(new JPanel());
		this.getConsolepanelRight().add(this.getConsolelabel());

		this.getConsoleframe().add(this.getConsolepanelRight(), BorderLayout.WEST);
		this.getConsoleframe().setVisible(true);

		slotLabel = new JLabel("Slots: " + this.getSlots());
		this.getConsoleframe().add(slotLabel);
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
		MBController.getInstance().registrieren(newUser, this.getConsolelabel());
	}

	/**
	 * Methode fuer das Login auf dem Server
	 * 
	 * @author Elia Perenzin
	 * @param newUser
	 *            {@link User}
	 * @throws RemoteException
	 */
	public synchronized void login(User user) throws RemoteException {
		MBController.getInstance().login(user, this.getConsolelabel());
	}

	/**
	 * Methode um alle User aus der DB zulesen
	 * 
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
	 * see {@link Validator#getBanana(int)}
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
				System.out.println("Fehler 3  //ValidatorImpl 176");
			}
		
		}
		return null;
	}

	@Override
	public void join(User user) throws RemoteException {
		this.getConsolelabel().setText(this.getConsolelabel().getText() + 
				"Benutzer " + user.getUsername() + " hat das Spiel betreten." + "<br>");
		
		this.setSlots(this.getSlots() + 1);
		slotLabel.setText("Slots: " + this.getSlots());
	}

	@Override
	public void logoutServer(User user) throws RemoteException {
		this.getConsolelabel().setText(this.getConsolelabel().getText() + user.getUsername() + 
				" hat den Server verlassen." + "<br>");
		slotLabel.setText("Slots: " + this.getSlots());
	}

	@Override
	public void logoutSpiel(User user) throws RemoteException {
		this.getConsolelabel().setText(this.getConsolelabel().getText() + user.getUsername() + 
				" hat das Spiel verlassen." + "<br>");
		this.setSlots(this.getSlots() - 1);

		slotLabel.setText("Slots: " + this.getSlots());
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
	public boolean getServerReady() throws RemoteException {
		return this.serverReady;
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

	
	public JLabel getConsolelabel() {
		return consolelabel;
	}

	public void setConsolelabel(JLabel consolelabel) {
		this.consolelabel = consolelabel;
	}

	public JPanel getConsolepanelRight() {
		return consolepanelRight;
	}

	public void setConsolepanelRight(JPanel consolepanelRight) {
		this.consolepanelRight = consolepanelRight;
	}

	public JFrame getConsoleframe() {
		return consoleframe;
	}

	public void setConsoleframe(JFrame consoleframe) {
		this.consoleframe = consoleframe;
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

	public boolean isServerReady() {
		return serverReady;
	}

	public void setServerReady(boolean serverReady) {
		this.serverReady = serverReady;
	}

	public void setOnlinePlayers(List<String> onlinePlayers) {
		this.onlinePlayers = onlinePlayers;
	}
}