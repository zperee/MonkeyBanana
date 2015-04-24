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
	
	private User u;

	public GameClient(User u) {
		this.setU(u);
        JFrame frame = new JFrame();

        frame.add(new Entity());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(15 * 48 + 16,  19 * 48 - 10);
        frame.setLocationRelativeTo(null);
        frame.setTitle("MonkeyBanana Game");
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setEnabled(true);
        
        frame.addWindowListener(new WindowAdapter() {
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

}