package ch.monkeybanana.GameTest;
import javax.swing.JFrame;

/**
 * Hauptklasse für den Spiel Client
 * @author Dominic Pfister, Elia Perenzin
 * GameClient.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */

public class GameClient extends JFrame {
	

	public GameClient() {

        add(new Entity());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(15 * 48 + 7,  16 * 48 + 24);
        setLocationRelativeTo(null);
        setTitle("MonkeyBanana Game");
        setResizable(false);
        setVisible(true);
        setEnabled(true);
    }

    public static void main(String[] args) {
        new GameClient();
    }
}