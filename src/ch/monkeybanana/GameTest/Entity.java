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
	private Player player = new Player(8 * 32, 4 * 32 + 2);
	List<Obstacle> obstacleArray = new ArrayList<Obstacle>();
	List<Banana> bananenArray = new ArrayList<Banana>();
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
		generateMap(32);
	}
	
	/**
	 * Erstellt neue Bananen wenn eine der beiden
	 * Tasten gedr¸ckt wurde
	 * 
	 * @author Dominic Pfister
	 */
	public void generateBanana() {
		int xPos = 0;
		int yPos = 0;
		int type = 1;
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

		if (player.isBananaPeel()) { // key == e
			xPos = player.getX() + 1;
			yPos = player.getY() + 15; //+15 wegen verkleinerten Hitbox
			
			Banana banana = new Banana(xPos, yPos, type, 'k'); //k steht f¸r keine direction
			bananenArray.add(banana);

			player.setBananaPeel(false);
			
		} else if (player.isBananaThrown()) { // key == r
			type = 2;
			
			if (player.isUp()) {
				xPos = player.getX();
				yPos = player.getY();
				dir = 'u';
			} else if (player.isDown()) {
				xPos = player.getX();
				yPos = player.getY() + 15; //+15 wegen verkleinerten Hitbox
				dir = 'd';
			} else if (player.isRight()) {
				xPos = player.getX();
				yPos = player.getY() + 15; //+15 wegen verkleinerten Hitbox
				dir = 'r';
			} else if (player.isLeft()) {
				xPos = player.getX();
				yPos = player.getY() + 15; //+15 wegen verkleinerten Hitbox
				dir = 'l';
			} 
			
			if (xPos != 0 && xPos != 0) { //Achtet darauf, dass am Anfang keine
										  //Bananen geworfen werden
				Banana banana = new Banana(xPos, yPos, type, dir);
				bananenArray.add(banana);
				player.setBananaThrown(false);
			}
		}
	}

	/**
	 * Erstellt die Karte f¸r das Spiel mit den Hindernissen
	 * 
	 * @author Dominic Pfister
	 * 
	 * @param mapSize {@link int}
	 * @param g {@link Graphics}
	 */
	public void generateMap(int mapSize) {
		/*
		 * TODO !KannZiel map einlesen (verschiedene Maps)
		 */

		/*
		 * **LEGENDE** 
		 * 0 = kein Block
		 * 1 = Block 
		 * 2 = n‰chste Linie
		 * 3 = Jungle Baum (rand)
		 */
		int[] map = { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2,
					  3, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 3, 2,
					  3, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3, 2,
					  3, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 3, 2,
					  3, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 3, 2,
					  3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 3, 2,
					  3, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 3, 2,
					  3, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 3, 2,
					  3, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 3, 2,
					  3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 3, 2,
					  3, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 3, 2,
					  3, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 3, 2,
					  3, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 3, 2,
					  3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2  };
		
		int posX = 0;
		int posY = 0;

		//Generiert Karte aufgrund von int[] map
		for (int s : map) {
			Obstacle kiste = new Obstacle(posX, posY, s);
			if (!isModified) {
				obstacleArray.add(kiste);
			}
				if (s == 1 || s == 3 || s == 0) {
					posX = posX + 32;					
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
			
			// Hitbox f¸r Hindernis
//			g.setColor(Color.RED);
//			g.drawRect(kiste.getX(), kiste.getY(), 
//			kiste.getImage().getWidth(null), 
//			kiste.getImage().getHeight(null));
		}
		
		/* Zeichnet die Bananen */
		for (Banana banana : bananenArray) { //Erhˆht die Koordinaten f¸r geworfene Bananen
			if (banana.getType() == 1) {
			} else if (banana.getType() == 2) {
				
				char dir = banana.getDirection(); //Erhˆht Positionen der Bananen
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
			
			//Hitbox f¸r Bananen
//			g.setColor(Color.ORANGE);
//			g.drawRect(banana.getX(), banana.getY(), 
//			banana.getImage().getWidth(null), 
//			banana.getImage().getHeight(null));
		}
		
		/* Zeichnet den Spieler */
		g.drawImage(player.getImage(), player.getX(), player.getY(), this);

		// Hitbox f¸r player
//		g.setColor(Color.GREEN);
//		g.drawRect(player.getX(), player.getY() + 15,
//		player.getImage().getWidth(null),
//		player.getImage().getHeight(null) - 15);
		
		Toolkit.getDefaultToolkit().sync();
	}

	
	public void actionPerformed(ActionEvent e) {
		player.move();
		repaint();
		checkBounds();
		checkBananaBounds();
		generateBanana();
	}

	/**
	 * Es wird abgerufen ob der Spieler bzw. das Hindernis sich ber√ºhren
     * 
	 * @author Dominic Pfister
	 */
	public void checkBounds() {
		Rectangle recPlayer = player.playerBounds();

		for (Obstacle kiste : obstacleArray) {
			Rectangle recKiste = kiste.obstBounds();

			if (kiste.getType() >= 1) { /* Wenn der Typ 1 ist, wird
										 * gepr√ºft ob das Hindernis
										 * und der Spieler sich ber√ºhren */

				if (recPlayer.intersects(recKiste)) {

					if (recPlayer.getMaxY() - 1 <= recKiste.getMaxY() //TOP
							&& !(recPlayer.getMinY() - 1 >= (recKiste.getMaxY() - 4))
							&& !(recPlayer.getMaxX() - 1 <= recKiste.getMinX() + 4)
							&& !(recPlayer.getMinX() - 1 >= recKiste.getMaxX() - 4)) {

						player.setY((int) recKiste.getMinY()- player.getImage().getHeight(null));
					} else if (recPlayer.getMinY() - 1 >= (recKiste.getMaxY() - 4)) { //BOTTOM
						player.setY((int) recKiste.getMaxY() - 15);
					} else if (recPlayer.getMaxX() - 1 <= recKiste.getMinX() + 4) { //RIGHT
						player.setX((int) recKiste.getMinX() - player.getImage().getWidth(null));
					} else if (recPlayer.getMinX() - 1>= recKiste.getMaxX() - 4) { //LEFT
						player.setX((int) recKiste.getMaxX());
					}
				}
			}
		}
	}
	
	public void checkBananaBounds() {
		for (Banana banana : bananenArray) {
			Rectangle recBanana = banana.bananaBounds();
			
			for (Obstacle kiste : obstacleArray) {
				Rectangle recKiste = kiste.obstBounds();
				
				if (kiste.getType() >= 1) {
					if (recBanana.intersects(recKiste)) {
						/* 
						 * TODO Entfernen wirft ConcurrentModificationException weil 
						 * Objekt entfernt wurde, w‰hrend es noch benutzt wurde
						 */
//						bananenArray.remove(banana);
						System.out.println("getroffen");
						
					}
				}
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