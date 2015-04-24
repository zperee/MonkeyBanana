package ch.monkeybanana.rmi;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
	private JPanel consolepanel;
	private JFrame consoleframe;
	private JLabel slotLabel;
	
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
		
		this.setConsolepanel(new JPanel());
		this.getConsolepanel().add(this.getConsolelabel());
		
		this.getConsoleframe().add(this.getConsolepanel(), BorderLayout.WEST);
		this.getConsoleframe().setVisible(true);
		
		slotLabel = new JLabel("Slots: " + MBController.getInstance().getSlotsBesetzt());
		this.getConsoleframe().add(slotLabel);
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
	public void join(User user) throws RemoteException {
		this.getConsolelabel().setText(this.getConsolelabel().getText() + "Benutzer "+ user.getUsername() + " hat das Spiel betreten." + "<br>");
		MBController.getInstance().setSlotsBesetzt(MBController.getInstance().getSlotsBesetzt() + 1);
		System.out.println(MBController.getInstance().getSlotsBesetzt());
		slotLabel.setText("Slots: " + MBController.getInstance().getSlotsBesetzt());
		System.out.println(MBController.getInstance().getSlotsBesetzt());
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

	
	public JLabel getConsolelabel() {
		return consolelabel;
	}

	public void setConsolelabel(JLabel consolelabel) {
		this.consolelabel = consolelabel;
	}

	public JPanel getConsolepanel() {
		return consolepanel;
	}

	public void setConsolepanel(JPanel consolepanel) {
		this.consolepanel = consolepanel;
	}

	public JFrame getConsoleframe() {
		return consoleframe;
	}

	public void setConsoleframe(JFrame consoleframe) {
		this.consoleframe = consoleframe;
	}

}
