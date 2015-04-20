package ch.monkeybanana.GameTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	List<Obstacle> obstArray = new ArrayList<Obstacle>();
	private boolean isModified = false;

	/**
	 * Konstruktor der Klasse Entity. Timer wird initialisiert und gestartet.
	 * Optionen des JPanel werden gesetzt.
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

	}
	
	public void banana() {
		
	}

	/**
	 * Erstellt die Karte für das Spiel mit den Hindernissen
	 * 
	 * @author Dominic Pfister
	 * 
	 * @param mapSize {@link int}
	 * @param g {@link Graphics}
	 */
	public void generateMap(int mapSize, Graphics g) {
		/*
		 * TODO !KannZiel map einlesen (verschiedene Maps)
		 */

		/*
		 * **LEGENDE** 
		 * 0 = kein Block 
		 * 1 = Block 
		 * 2 = nächste Linie
		 */
		int[] map = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
				 	  1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2,
					  1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2,
					  1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2,
					  1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2,
					  1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2,
					  1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2,
					  1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2,
					  1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2,
					  1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2,
					  1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2,
					  1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2,
					  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
					 };
		
		int posX = 0;
		int posY = 0;
		
		Graphics2D g2d = (Graphics2D) g;

		//Generiert Karte aufgrund von int[] map
		for (int s : map) {
			Obstacle kiste = new Obstacle(posX, posY, s);
			
			if (!isModified) {
				obstArray.add(kiste);
				kiste.setType(s);
			}
			
			if (s != 0) {
				if (s == 1) {
					posX = posX + 32;
					g2d.drawImage(kiste.getImage(), kiste.getX(), kiste.getY(), this);

					// Hitbox für Hindernis
					g2d.setColor(Color.GREEN);
					g2d.drawRect(kiste.getX(), kiste.getY(), kiste.getImage().getWidth(null), kiste.getImage().getHeight(null));
					
				} else if (s == 2) {
					posX = 0;
					posY = posY + mapSize;
				}
			}
			if (s == 0) {
				posX = posX + mapSize;
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
		Graphics2D g2d = (Graphics2D) g;

		generateMap(32, g2d);
		
		g2d.setColor(Color.GREEN);

		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this); //Zeichnet den Spieler

		// Hitbox für player
		g2d.drawRect(player.getX(), player.getY() + 15,
		player.getImage().getWidth(null),
		player.getImage().getHeight(null) - 15);

		Toolkit.getDefaultToolkit().sync();
	}

	
	public void actionPerformed(ActionEvent e) {
		player.move();
		repaint();
		checkBounds();
	}

	/**
	 * Es wird abgerufen ob der Spieler bzw. das Hindernis sich berühren
     * 
	 * @author Dominic Pfister
	 */
	public void checkBounds() {
		Rectangle recPlayer = player.playerBounds();

		for (Obstacle kiste : obstArray) {
			Rectangle recKiste = kiste.obstBounds();

			if (kiste.getType() == 1) { /* Wenn der Typ 1 ist, wird
										 * geprüft ob das Hindernis
										 * und der Spieler sich berühren */

				if (recPlayer.intersects(recKiste)) {

					if (recPlayer.getMaxY() <= recKiste.getMaxY() //TOP
							&& !(recPlayer.getMinY() >= (recKiste.getMaxY() - 4))
							&& !(recPlayer.getMaxX() <= recKiste.getMinX() + 4)
							&& !(recPlayer.getMinX() >= recKiste.getMaxX() - 4)) {

						player.setY((int) recKiste.getMinY()- player.getImage().getHeight(null));
					} else if (recPlayer.getMinY() >= (recKiste.getMaxY() - 4)) { //BOTTOM
						player.setY((int) recKiste.getMaxY() - 15);
					} else if (recPlayer.getMaxX() <= recKiste.getMinX() + 4) { //RIGHT
						player.setX((int) recKiste.getMinX() - player.getImage().getWidth(null));
					} else if (recPlayer.getMinX() >= recKiste.getMaxX() - 4) { //LEFT
						player.setX((int) recKiste.getMaxX());
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
	}
}