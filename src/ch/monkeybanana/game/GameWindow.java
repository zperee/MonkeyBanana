package ch.monkeybanana.game;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import ch.monkeybanana.model.User;

/**
 * Hauptklasse fuer den Spiel Client. Erzeugt das JFrame und
 * ein neues Spielbrett
 * 
 * @param u {@link User}
 * @param playerNr {@link int}
 * @author Dominic Pfister, Elia Perenzin
 * GameClient.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */

public class GameWindow extends JFrame {
	
	//Instanzvariablen
	private static final long serialVersionUID = -1599709363381584400L;
	private User u;
	private Gameboard ent;

	public GameWindow(User u, int playerNr) {
		this.setU(u);

		/* Nur fuer Test ohne DB */
//		try {
//			if (u.getUsername().equals("SYSTEM")) {
//			}
//		} catch (NullPointerException e) {
//			u.setUsername("Gast");
//		}
		
        try {
			this.setEnt(new Gameboard(playerNr, u, this));
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
       
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
    }

	/* **GETTER und SETTER** */
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