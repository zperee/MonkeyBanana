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

import ch.monkeybanana.GameTest.Banana;
import ch.monkeybanana.GameTest.GameClient;
import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.model.User;

/**
 * Implementierte Methoden des Interfaces Validator fuer Verbindung zu Server
 * @author Dominic Pfister, Elia Perenzin
 * ValidatorImpl.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */
public class ValidatorImpl extends UnicastRemoteObject  implements Validator {
	private JLabel consolelabel;
	private JPanel consolepanelRight;
	
	private JFrame consoleframe;
	private JLabel slotLabel;
	private GameClient game;
	private List<Banana> bananen = new ArrayList<Banana>();
	private int scorePlayer1 = 0;
	private int scorePlayer2 = 0;
	private int rundenZahl = 0;
	
	/**
	 * Konstrukor fuer ValidatorImpl
	 * @author Elia Perenzin
	 * @throws RemoteException
	 */
	public ValidatorImpl() throws RemoteException {
		this.setConsoleframe(new JFrame());
		this.getConsoleframe().setTitle("Server Console");
		this.getConsoleframe().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getConsoleframe().setSize(500, 300);
		
		this.setConsolelabel(new JLabel("<html>"));
		this.getConsolelabel().setHorizontalTextPosition(SwingConstants.LEFT);
		
		this.setConsolepanelRight(new JPanel());
		this.getConsolepanelRight().add(this.getConsolelabel());
		
		this.getConsoleframe().add(this.getConsolepanelRight(), BorderLayout.WEST);
		this.getConsoleframe().setVisible(true);
		
		slotLabel = new JLabel("Slots: " + MBController.getInstance().getSlotsBesetzt());
		this.getConsoleframe().add(slotLabel);
		User system = new User();
		system.setUsername("SYSTEM");
		this.setGame(new GameClient(system, 0));
	}

	/**
	 * Methode fuer die Registration eines Users auf dem Server
	 * @author Elia Perenzin
	 * @param newUser {@link User}
	 * @throws RemoteException
	 */
	public void registration(User newUser) throws RemoteException {
			MBController.getInstance().registrieren(newUser, this.getConsolelabel());
	}

	/**
	 * Methode fuer das Login auf dem Server
	 * @author Elia Perenzin
	 * @param newUser {@link User}
	 * @throws RemoteException
	 */
	public synchronized void login(User user) throws RemoteException {
			MBController.getInstance().login(user, this.getConsolelabel());
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
	
	
	@Override
	public void tellPosition(int x, int y, int ownPlayerNr)
			throws RemoteException {
		this.getGame().getEnt().getPlayerArray().get(ownPlayerNr).setX(x);
		this.getGame().getEnt().getPlayerArray().get(ownPlayerNr).setY(y);
		
	}

	@Override
	public int getPosition(char XorY, int ownPlayerNr) throws RemoteException {
		if (ownPlayerNr == 0){
			if (XorY == 'x'){
				return this.getGame().getEnt().getPlayerArray().get(1).getX();
			}
			else if (XorY == 'y'){
				return this.getGame().getEnt().getPlayerArray().get(1).getY();
			}
		
		
		}
		else if (ownPlayerNr == 1){
			if (XorY == 'x'){
				return this.getGame().getEnt().getPlayerArray().get(0).getX();
			}
			else if (XorY == 'y'){
				return this.getGame().getEnt().getPlayerArray().get(0).getY();
			}
		
			
		}
		
		return 0;
	}
	
	
	@Override
	public void tellBanana(Banana banana) throws RemoteException {
		this.getBananen().add(banana);
	}

	/**
	 * see {@link Validator#getBanana(int)}
	 */
	@Override
	public Banana getBanana(int playerNr) throws RemoteException {
		if (this.getBananen().size() != 0){
			
			for (Banana b : this.getBananen()){
				if (b.getOwner() != playerNr) {
					this.getBananen().remove(b);
					return b;
				}
			}
		}
		
		return null;
	}

	/**
	 * 
	 */
	@Override
	public void join(User user) throws RemoteException {
		this.getConsolelabel().setText(this.getConsolelabel().getText() + "Benutzer "+ user.getUsername() + " hat das Spiel betreten." + "<br>");
		MBController.getInstance().setSlotsBesetzt(MBController.getInstance().getSlotsBesetzt() + 1);
		
		slotLabel.setText("Slots: " + MBController.getInstance().getSlotsBesetzt());
	}
	
	@Override
	public void logoutServer(User user) throws RemoteException {
		this.getConsolelabel().setText(this.getConsolelabel().getText() + user.getUsername() + " hat den Server verlassen." + "<br>");
		MBController.getInstance().setSlotsBesetzt(MBController.getInstance().getSlotsBesetzt() - 1);
		
		slotLabel.setText("Slots: " + MBController.getInstance().getSlotsBesetzt());
	}
	
	@Override
	public void logoutSpiel(User user) throws RemoteException {
		this.getConsolelabel().setText(this.getConsolelabel().getText() + user.getUsername() + " hat das Spiel verlassen." + "<br>");
		MBController.getInstance().setSlotsBesetzt(MBController.getInstance().getSlotsBesetzt() - 1);
		
		slotLabel.setText("Slots: " + MBController.getInstance().getSlotsBesetzt());
	}

	@Override
	public int getSlots() throws RemoteException {
		return MBController.getInstance().getSlotsBesetzt();
	}

	@Override
	public void score(int playerNr) throws RemoteException {
		if(playerNr == 0){
			this.setScorePlayer1(this.getScorePlayer1() + 1);
		}
		else if (playerNr == 1){
			this.setScorePlayer2(this.getScorePlayer2() + 1);
		}
	this.setRundenZahl(this.getRundenZahl() + 1);
	System.out.println(getScorePlayer1());
	System.out.println(getScorePlayer2());
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

	public GameClient getGame() {
		return game;
	}

	public void setGame(GameClient game) {
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



}