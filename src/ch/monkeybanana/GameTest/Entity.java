package ch.monkeybanana.GameTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * DESC
 * 
 * @author Dominic Pfister, Elia Perenzin Entity.java Copyright
 *         Berufsbildungscenter MonkeyBanana 2015
 */

public class Entity extends JPanel implements ActionListener {

	private Timer timer;
	private Player player;
	
	List<Obstacle> obstacleArray = new ArrayList<Obstacle>();
	List<Banana> bananenArray = new ArrayList<Banana>();
	
	private long coolDown;
	private long refreshTimer = System.currentTimeMillis();

	private int playerMaxBananas;
	
	private boolean isModified = false;

	/**
	 * DESC
	 * 
	 * @author Dominic Pfister
	 */
	public Entity() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.ORANGE);
		setDoubleBuffered(true);

		timer = new Timer(10, this);
		timer.start();
		
		player = new Player(64, 64, 15, 500, 64);
		playerMaxBananas = player.getTotalBanana();
		
		/* Wartet für 100ms bis das Spieler Image neu skaliert wurde */
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		generateMap(player.getImage().getWidth(null));
	}
	
	/**
	 * Erhöht die Anzahl der Bananen des Spielers alle
	 * (time).
	 * 
	 * @author Dominic Pfister
	 * @param time {@link long}
	 */
	private void increaseBanana(long time) {
		if (refreshTimer + time <= System.currentTimeMillis() 
			&& playerMaxBananas > player.getTotalBanana()) {
			
			player.setTotalBanana(player.getTotalBanana() + 1);
			refreshTimer = System.currentTimeMillis();
		}
	}
	
	/**
	 * Erstellt neue Bananen wenn eine der beiden
	 * Tasten gedrückt wurde
	 * 
	 * @author Dominic Pfister
	 */
	private void generateBanana() {
		int xPos = 0;
		int yPos = 0;
		int type;
		char dir = 'k';
		
		/* **LEGENDE**
		 * 
		 * type:
		 * 1 = Bananenschale
		 * 2 = geworfene Bananen
		 * 
		 * direction:
		 * u = up
		 * d = down
		 * r = right
		 * l = left
		 */

		if (player.getTotalBanana() > 0
			&& coolDown <= System.currentTimeMillis()) {
			
			if (player.isBananaPeel()) { // key == e
				type = 1;
				xPos = player.getX() + 1 + player.getImage().getWidth(null) / 4;
				yPos = player.getY() + player.getImage().getWidth(null) - player.getImage().getWidth(null) / 4;
				
				Banana banana = new Banana(xPos, yPos, type, 'k', player.getImage().getWidth(null)); //k steht für keine direction
				bananenArray.add(banana);
	
				player.setBananaPeel(false);
				coolDown = (System.currentTimeMillis() + player.getCoolDown());
				player.setTotalBanana(player.getTotalBanana() - 1);
				player.setAllowBanana(false);
				
			} else if (player.isBananaThrown()) { // key == r
				type = 2;
				
				if (player.isUp()) {
					xPos = player.getX() + player.getImage().getWidth(null) / 4;
					yPos = player.getY() + player.getImage().getWidth(null) / 4;
					dir = 'u';
				} else if (player.isDown()) {
					xPos = player.getX() + player.getImage().getWidth(null) / 4;
					yPos = player.getY() + player.getImage().getWidth(null) + player.getImage().getWidth(null) / 4;
					dir = 'd';
				} else if (player.isRight()) {
					xPos = player.getX() + player.getImage().getWidth(null) / 4;
					yPos = player.getY() + player.getImage().getWidth(null) - player.getImage().getWidth(null) / 4;
					dir = 'r';
				} else if (player.isLeft()) {
					xPos = player.getX() + player.getImage().getWidth(null) / 4;
					yPos = player.getY() + player.getImage().getWidth(null) - player.getImage().getWidth(null) / 4;
					dir = 'l';
				} 
				
				if (xPos != 0 && xPos != 0) { //Achtet darauf, dass am Anfang keine
											  //Bananen geworfen werden
					Banana banana = new Banana(xPos, yPos, type, dir, player.getImage().getWidth(null));
					bananenArray.add(banana);
					player.setBananaThrown(false);
					coolDown = (System.currentTimeMillis() + player.getCoolDown());
					
					player.setTotalBanana(player.getTotalBanana() - 1);
					player.setAllowBanana(false);
				}
			}
		}
	}

	/**
	 * Erstellt die Karte für das Spiel mit den Hindernissen
	 * 
	 * @author Dominic Pfister
	 * 
	 * @param mapSize {@link int}
	 * @param g {@link Graphics}
	 */
	private void generateMap(int mapSize) {
		/*
		 * TODO !KannZiel map einlesen (verschiedene Maps)
		 */

		/*
		 * **LEGENDE** 
		 * 0 = kein Block
		 * 1 = Block 
		 * 2 = nächste Linie
		 * 3 = Jungle Baum (rand)
		 * 4 = Pipe up
		 * 5 = Pipe down
		 * 6 = Pipe right
		 * 7 = Pipe left
		 */
		int[] map = { 3, 3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2,
					  3, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 3, 2,
					  3, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3, 2,
					  3, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 3, 2,
					  3, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 3, 2,
					  3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 3, 2,
					  7, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 6, 2,
					  3, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 3, 2,
					  3, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 3, 2,
					  3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 3, 2,
					  3, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 3, 2,
					  3, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 3, 2,
					  3, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 3, 2,
					  3, 3, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2  };
		
		int posX = 0;
		int posY = 0;

		//Generiert Karte aufgrund von int[] map
		for (int s : map) {
			Obstacle kiste = new Obstacle(posX, posY, s, player.getImage().getWidth(null));
			if (!isModified) {
				obstacleArray.add(kiste);
			}
				if (s == 1 || s == 3 || s == 0 || s == 4 || s == 5 || s == 6 || s == 7) {
					posX = posX + mapSize;					
				} else if (s == 2) {
					posX = 0;
					posY = posY + mapSize;
				}
		}
		isModified = true;
	}
	
	/**
	 * Funktion um die Objekte zu zeichnen
	 * 
	 * @author Dominic Pfister
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		/* Zeichnet die Hindernisse */
		for (Obstacle kiste : obstacleArray) {
			g.drawImage(kiste.getImage(), kiste.getX(), kiste.getY(), this);
				
			// Hitbox für Hindernis
//			g.setColor(Color.RED);				
//			g.drawRect(kiste.getX(), kiste.getY(), 
//			kiste.getImage().getWidth(null), 
//			kiste.getImage().getHeight(null));
		}
		
		/* Zeichnet die Bananen */
		for (Banana banana : bananenArray) { //Erhöht die Koordinaten für geworfene Bananen
			if (banana.getType() == 1) {
			} else if (banana.getType() == 2) {
				
				char dir = banana.getDirection(); //Erhöht Positionen der Bananen
				switch (dir) {
				case('u'):
					banana.setY(banana.getY() - Player.SPEED - 1);
					break;
				case('d'):
					banana.setY(banana.getY() + Player.SPEED + 1);
					break;
				case('r'):
					banana.setX(banana.getX() + Player.SPEED + 1);
					break;
				case('l'):
					banana.setX(banana.getX() - Player.SPEED - 1);
					break;
				}
			}
			g.drawImage(banana.getImage(), banana.getX(), banana.getY(), this);
			
			//Hitbox für Bananen
//			g.setColor(Color.ORANGE);
//			g.drawRect(banana.getX(), banana.getY(), 
//			banana.getImage().getWidth(null), 
//			banana.getImage().getHeight(null));
		}
		
		/* Zeichnet den Spieler */
		g.drawImage(player.getImage(), player.getX(), player.getY(), this);

		// Hitbox für player
//		g.setColor(Color.GREEN);
//		g.drawRect(player.getX(), player.getY() + player.getImage().getWidth(null) / 2,
//		player.getImage().getWidth(null),
//		player.getImage().getHeight(null) - player.getImage().getWidth(null) / 2);
		
		
		Toolkit.getDefaultToolkit().sync();
	}

	
	public void actionPerformed(ActionEvent e) {
		player.move();
		repaint();
		checkBounds();
		checkBananaBounds();
		generateBanana();
		increaseBanana(5000);
	}

	/**
	 * Es wird abgerufen ob der Spieler bzw. das Hindernis sich berühren
     * 
	 * @author Dominic Pfister
	 */
	private void checkBounds() {
		Rectangle recPlayer = player.playerBounds();

		for (Obstacle kiste : obstacleArray) {
			Rectangle recKiste = kiste.obstBounds();

			/* **Normal kiste detection** */
			if (kiste.getType() == 1 || kiste.getType() == 3) { /* Wenn der Typ 1 oder 3 ist, wird
																 * geprüft ob das Hindernis
																 * und der Spieler sich berühren */

				if (recPlayer.intersects(recKiste)) {

					if (recPlayer.getMaxY() - 1 <= recKiste.getMaxY() //TOP
						&& !(recPlayer.getMinY() - 1 >= (recKiste.getMaxY() - 4))
						&& !(recPlayer.getMaxX() - 1 <= recKiste.getMinX() + 4)
						&& !(recPlayer.getMinX() - 1 >= recKiste.getMaxX() - 4)) {

						player.setY((int) recKiste.getMinY()- player.getImage().getHeight(null));
					} else if (recPlayer.getMinY() - 1 >= (recKiste.getMaxY() - 4)) { //BOTTOM
						player.setY((int) recKiste.getMaxY() - player.getImage().getHeight(null) / 3);
					} else if (recPlayer.getMaxX() - 1 <= recKiste.getMinX() + 4) { //RIGHT
						player.setX((int) recKiste.getMinX() - player.getImage().getWidth(null));
					} else if (recPlayer.getMinX() - 1 >= recKiste.getMaxX() - 4) { //LEFT
						player.setX((int) recKiste.getMaxX());
					}
				}
				
				/* **Pipe detection** */
				} else if (kiste.getType() == 4) { //TOP
					if (!(recPlayer.getMinY() - 1 >= recKiste.getMaxY() - 24 - player.getImage().getWidth(null) / 4)) {
						player.setY(12 * player.getImage().getWidth(null) - player.getImage().getWidth(null) / 4);
					}
				} else if (kiste.getType() == 5) { //BOTTOM
					if (!(recPlayer.getMaxY() - 1 <= recKiste.getMaxY() - 4 - player.getImage().getWidth(null) / 4)) {
						player.setY(1 * player.getImage().getWidth(null) - player.getImage().getWidth(null) / 4);					}
				} else if (kiste.getType() == 6) { //RIGHT
					if (!(recPlayer.getMaxX() - 1 <= recKiste.getMinX() + 4 + player.getImage().getWidth(null) / 4)) {
						player.setX((1 * player.getImage().getWidth(null)) - 16);
					}
				} else if (kiste.getType() == 7) { //LEFT
					if (!(recPlayer.getMinX() - 1 >= recKiste.getMaxX() - 4 - player.getImage().getWidth(null) / 4)) {
						player.setX((13 * player.getImage().getWidth(null)) + 16);
					}
				}
		}
	}
	
	private void checkBananaBounds() {
		boolean isRemoved = true;
		for (Banana banana : bananenArray) {
			Rectangle recBanana = banana.bananaBounds();
			for (Obstacle kiste : obstacleArray) {
				Rectangle recKiste = kiste.obstBounds();
				if (kiste.getType() >= 1) {
					if (recBanana.intersects(recKiste)) {
						bananenArray.remove(banana);
						isRemoved = false;
					}
				}
			}
			if (isRemoved == false) {
				break;
			}
		}
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
		
		public void keyTyped(KeyEvent e) {
			player.keyTyped(e);
		}
	}
}