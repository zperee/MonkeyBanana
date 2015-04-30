package ch.monkeybanana.rmi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

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
	private static final long serialVersionUID = -5995871726568830289L;
	private JPanel consolepanelRight;
	private JPanel contentPane;
	private JFrame frame;
	private JLabel slotLabel;
	private GameClient game;
	private List<Banana> bananen = new ArrayList<Banana>();
	
	/**
	 * Konstrukor fuer ValidatorImpl
	 * @author Elia Perenzin
	 * @throws RemoteException
	 */
	public ValidatorImpl() throws RemoteException {
		frame = new JFrame();
		frame.setTitle("MonkeyBanana - Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 420, 578);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
		
		JPanel serverMsgs = new JPanel();
		scrollPane.setViewportView(serverMsgs);
		
		this.setSlotLabel(new JLabel());
		slotLabel.setText("Server inititiallized!");
		System.out.println(this.getSlotLabel().getText());
		slotLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		serverMsgs.add(slotLabel);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Slots");
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 46, 14);
		panel_3.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 177, 2);
		panel_3.add(separator);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 49, 177, 470);
		panel_3.add(scrollPane_1);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		scrollPane_1.setColumnHeaderView(panel_2);
		
		JLabel lblConnectedPlayers = new JLabel("Connected Players");
		lblConnectedPlayers.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		panel_2.add(lblConnectedPlayers);
		
		JPanel conPlayers = new JPanel();
		scrollPane_1.setViewportView(conPlayers);
		
		frame.add(this.getContentPane());
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
		}
		
		frame.setVisible(true);
	}

	/**
	 * Methode fuer die Registration eines Users auf dem Server
	 * @author Elia Perenzin
	 * @param newUser {@link User}
	 * @throws RemoteException
	 */
	public void registration(User newUser) throws RemoteException {
			MBController.getInstance().registrieren(newUser, this.getSlotLabel());
	}

	/**
	 * Methode fuer das Login auf dem Server
	 * @author Elia Perenzin
	 * @param newUser {@link User}
	 * @throws RemoteException
	 */
	public synchronized void login(User user) throws RemoteException {
		MBController.getInstance().login(user, this.getSlotLabel());
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
		this.getSlotLabel().setText(this.getSlotLabel().getText() + "Benutzer "+ user.getUsername() + " hat das Spiel betreten." + "<br>");
		MBController.getInstance().setSlotsBesetzt(MBController.getInstance().getSlotsBesetzt() + 1);
		
		slotLabel.setText("Slots: " + MBController.getInstance().getSlotsBesetzt());
	}
	
	@Override
	public void logoutServer(User user) throws RemoteException {
		this.getSlotLabel().setText(this.getSlotLabel().getText() + user.getUsername() + " hat den Server verlassen." + "<br>");
		MBController.getInstance().setSlotsBesetzt(MBController.getInstance().getSlotsBesetzt() - 1);
		
		slotLabel.setText("Slots: " + MBController.getInstance().getSlotsBesetzt());
	}
	
	@Override
	public void logoutSpiel(User user) throws RemoteException {
		this.getSlotLabel().setText(this.getSlotLabel().getText() + user.getUsername() + " hat das Spiel verlassen." + "<br>");
		MBController.getInstance().setSlotsBesetzt(MBController.getInstance().getSlotsBesetzt() - 1);
		
		slotLabel.setText("Slots: " + MBController.getInstance().getSlotsBesetzt());
	}

	@Override
	public int getSlots() throws RemoteException {
		return MBController.getInstance().getSlotsBesetzt();
	}

	public JPanel getConsolepanelRight() {
		return consolepanelRight;
	}

	public void setConsolepanelRight(JPanel consolepanelRight) {
		this.consolepanelRight = consolepanelRight;
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

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JLabel getSlotLabel() {
		return slotLabel;
	}

	public void setSlotLabel(JLabel slotLabel) {
		this.slotLabel = slotLabel;
	}

}