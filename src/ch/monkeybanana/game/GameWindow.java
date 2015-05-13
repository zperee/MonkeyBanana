package ch.monkeybanana.game;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

/**
 * Hauptklasse für den Spiel Client
 * @author Dominic Pfister, Elia Perenzin
 * GameClient.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */

public class GameWindow extends JFrame {
	private static final long serialVersionUID = -1599709363381584400L;
	private User u;
	private Gameboard ent;

	public GameWindow(User u, int playerNr) {
		this.setU(u);
        this.setEnt(new Gameboard(playerNr, u, this));
       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(15 * 48 + 7,  18 * 48 - 19);
        this.setLocationRelativeTo(null);
        this.setTitle("MonkeyBanana Game");
        this.setResizable(false);

        if (u.getUsername().equals("SYSTEM")) {
        	this.setVisible(false);
        } else {
        	this.setVisible(true);
        }
        
        this.setEnabled(true);
        this.add(this.getEnt());
        this.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            try {
	            	Client.getInstance().getConnect().logoutSpiel(getU());
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
	        }
	    });
    }
	
//	public static void main(String[] args) {
//		new GameClient();
//	}
	
	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public Gameboard getEnt() {
		return ent;
	}

	public void setEnt(Gameboard ent) {
		this.ent = ent;
	}

}