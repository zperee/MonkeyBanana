package ch.monkeybanana.GameTest;
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

public class GameClient extends JFrame {
	private static final long serialVersionUID = -1599709363381584400L;
	private User u;
	private Entity ent;

	public GameClient(User u, int playerNr) {
		this.setU(u);
        this.setEnt(new Entity(playerNr));
       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(15 * 48 + 16,  18 * 48 - 10);
        this.setLocationRelativeTo(null);
        this.setTitle("MonkeyBanana Game");
        this.setResizable(true);
        
        u.setUsername("Hi");
        if (u.getUsername().equals("SYSTEM")){
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

	public Entity getEnt() {
		return ent;
	}

	public void setEnt(Entity ent) {
		this.ent = ent;
	}

}