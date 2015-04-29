package ch.monkeybanana.GameTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import ch.monkeybanana.listener.GameListener;
import ch.monkeybanana.rmi.Client;

/**
 * DESC
 * 
 * @author Dominic Pfister, Elia Perenzin Entity.java Copyright
 *         Berufsbildungscenter MonkeyBanana 2015
 */

public class Entity extends JPanel implements ActionListener {

	private Timer timer;
	private Player p1;
	private Player p2;
	private int playerNr;
	
	List<Obstacle> obstacleArray = new ArrayList<Obstacle>();
	List<Banana> bananenArray = new ArrayList<Banana>();
	List<Player> playerArray = new ArrayList<Player>();
	
	private long coolDown;
	private long refreshTimer;
	
	private int playerMaxBananas;
	
	private boolean isModified;

	/**
	 * DESC
	 * 
	 * @author Dominic Pfister
	 */
	public Entity(int spielerNr) {
		
		this.setPlayerNr(spielerNr);
		setFocusable(true);
		setDoubleBuffered(true);
		
		isModified = false;
		refreshTimer = System.currentTimeMillis();
		
		p1 = new Player(48, 4 * 48, 15, 500, 1);
		p2 = new Player(200, 4 * 48, 15, 500, 2);
		playerArray.add(this.getP1());
		playerArray.add(this.getP2());
		
		playerMaxBananas = 15;
		
		/* Wartet für 100ms bis das Spieler Image neu skaliert wurde */
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		timer = new Timer(10, this);
		timer.start();
		
		generateMap(48);
	}
	
	/**
	 * Erhöht die Anzahl der Bananen des Spielers alle
	 * (time).
	 * @author Dominic Pfister, Elia Perenzin
	 * @param time {@link long}
	 */
	private void increaseBanana(long time) {
		int i = 0;
		if (refreshTimer + time <= System.currentTimeMillis() ) {
			while (this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() < this.getPlayerMaxBananas() && i < 5)	{	
				this.getPlayerArray().get(this.getPlayerNr()).setTotalBanana(this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() + 1);
				i++;
				this.setRefreshTimer(System.currentTimeMillis());
			}
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
		int owner;
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

		if (this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() > 0
			&& coolDown <= System.currentTimeMillis()) {
			
			if (GameListener.isBananaPeel()) { // key == e
				type = 1;
				xPos = this.getPlayerArray().get(this.getPlayerNr()).getX() + 1 + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
				yPos = this.getPlayerArray().get(this.getPlayerNr()).getY() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null)
						- this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
				owner = this.getPlayerNr();
				
				Banana banana = new Banana(xPos, yPos, type, 'k', owner); //k steht für keine direction
				bananenArray.add(banana);
	
				GameListener.setBananaPeel(false);
				coolDown = (System.currentTimeMillis() + this.getPlayerArray().get(this.getPlayerNr()).getCoolDown());
				this.getPlayerArray().get(this.getPlayerNr()).setTotalBanana(this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() - 1);
				GameListener.setAllowBanana(false);
				
				try {
					Client.getInstance().getConnect().tellBanana(banana);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
				
			} else if (GameListener.isBananaThrown()) { // key == r
				type = 2;
				owner = this.getPlayerNr();
				
				if (GameListener.isUp()) {
					xPos = this.getPlayerArray().get(this.getPlayerNr()).getX() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					yPos = this.getPlayerArray().get(this.getPlayerNr()).getY() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					dir = 'u';
				} else if (GameListener.isDown()) {
					xPos = this.getPlayerArray().get(this.getPlayerNr()).getX() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					yPos = this.getPlayerArray().get(this.getPlayerNr()).getY() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) +
							this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					dir = 'd';
				} else if (GameListener.isRight()) {
					xPos = this.getPlayerArray().get(this.getPlayerNr()).getX() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					yPos = this.getPlayerArray().get(this.getPlayerNr()).getY() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) - 
							this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					dir = 'r';
				} else if (GameListener.isLeft()) {
					xPos = this.getPlayerArray().get(this.getPlayerNr()).getX() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					yPos = this.getPlayerArray().get(this.getPlayerNr()).getY() + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) - 
							this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					dir = 'l';
				}
				
				if (xPos != 0 && xPos != 0) { //Achtet darauf, dass am Anfang keine
											  //Bananen geworfen werden
					owner = this.getPlayerNr();
					Banana banana = new Banana(xPos, yPos, type, dir, owner);
					bananenArray.add(banana);
					GameListener.setBananaThrown(false);
					coolDown = (System.currentTimeMillis() + this.getPlayerArray().get(this.getPlayerNr()).getCoolDown());
					
					this.getPlayerArray().get(this.getPlayerNr()).setTotalBanana(this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() - 1);
					GameListener.setAllowBanana(false);
					
					try {
						Client.getInstance().getConnect().tellBanana(banana);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		} else if (coolDown <= System.currentTimeMillis()) {
			GameListener.setUp(false);
			GameListener.setDown(false);
			GameListener.setRight(false);
			GameListener.setLeft(false);
			GameListener.setBananaPeel(false);
			GameListener.setBananaThrown(false);
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
		int[] map = {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2,
					 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
					 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
					 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
				     3, 3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2,
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
    				 3, 3, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2 };
		
		/* TEMPLATE */
//		int[] map = { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2,
//				  3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2  };
		
		int posX = 0;
		int posY = 0;

		//Generiert Karte aufgrund von int[] map
		for (int s : map) {
			Obstacle kiste = new Obstacle(posX, posY, s);
			if (!isModified) {
				obstacleArray.add(kiste);
			}
				if (s == 1 || s == 3 || s == 0 || s == 4 || s == 5 || s == 6 || s == 7 || s == 8) {
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
			g.drawImage(kiste.getImage().getImage(), kiste.getX(), kiste.getY(), this);
			// Hitbox für Hindernis
//			g.setColor(Color.RED);
//			g.drawRect(kiste.getX(), kiste.getY(),
//			kiste.getImage().getImage().getWidth(null),
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
			g.drawImage(banana.getImage().getImage(), banana.getX(), banana.getY(), this);
			
			//Hitbox für Bananen
//			g.setColor(Color.ORANGE);
//			g.drawRect(banana.getX(), banana.getY(), 
//			banana.getImage().getImage().getWidth(null), 
//			banana.getImage().getHeight(null));
		}
		
		/* Zeichnet den Spieler */
		for (Player p : playerArray) {
			g.drawImage(p.getImage().getImage(), p.getX(), p.getY(), this);
		}

		// Hitbox für player
//		g.setColor(Color.GREEN);
//		g.drawRect(player.getX(), player.getY() + player.getImage().getImage().getWidth(null) / 2,
//		player.getImage().getImage().getWidth(null),
//		player.getImage().getHeight(null) - player.getImage().getImage().getWidth(null) / 2);
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		g.setColor(Color.RED);
		g.drawString(String.valueOf(this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana()), 100, 75);
		
		
		Toolkit.getDefaultToolkit().sync();
	}

	
	public void actionPerformed(ActionEvent e) {
		for (Player p : playerArray) {
			p.move();
		}
		
		repaint();
		checkBounds(15, 18);
		checkBananaBounds(16, 18);
		generateBanana();
		increaseBanana(10000);
	}

	/**
	 * Es wird abgerufen ob der Spieler bzw. das Hindernis sich berühren.
	 * Die Spieldfeldlänge wird als Paramter mitgegeben.
     * 
	 * @author Dominic Pfister
	 * @param feldBreite {@link int}
	 * @param feldHöhe {@link int}
	 */
	private void checkBounds(int feldBreite, int feldHöhe) {
		for (Player p : this.getPlayerArray()) {
		Rectangle recPlayer = p.playerBounds();

		try {
			
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

						p.setY((int) recKiste.getMinY()- p.getImage().getImage().getHeight(null));
					} else if (recPlayer.getMinY() - 1 >= (recKiste.getMaxY() - 4)) { //BOTTOM
						p.setY((int) recKiste.getMaxY() - p.getImage().getImage().getHeight(null) / 3);
					} else if (recPlayer.getMaxX() - 1 <= recKiste.getMinX() + 4) { //RIGHT
						p.setX((int) recKiste.getMinX() -p.getImage().getImage().getWidth(null));
					} else if (recPlayer.getMinX() - 1 >= recKiste.getMaxX() - 4) { //LEFT
						p.setX((int) recKiste.getMaxX());
					}
				}
				
				/* **Pipe detection** */
				} else if (kiste.getType() == 4) { //TOP
					if (!(recPlayer.getMinY() - 1 >= recKiste.getMaxY() - (p.getImage().getImage().getWidth(null) * 0.5))) {
						p.setY((feldHöhe - 2) * p.getImage().getImage().getWidth(null) - p.getImage().getImage().getWidth(null) / 4);
					}
				} else if (kiste.getType() == 5) { //BOTTOM
					if (!(recPlayer.getMaxY() - 1 <= recKiste.getMaxY() - (p.getImage().getImage().getWidth(null) * 0.75))) {
						p.setY(5 * p.getImage().getImage().getWidth(null) - (int) (p.getImage().getImage().getWidth(null) * 0.9));					
					}
				} else if (kiste.getType() == 6) { //RIGHT
					if (!(recPlayer.getMaxX() - 1 <= recKiste.getMinX() + (p.getImage().getImage().getWidth(null) * 0.25))) {
						p.setX((1 * p.getImage().getImage().getWidth(null)) - (int) (p.getImage().getImage().getWidth(null) * 0.25) + 1);
					}
				} else if (kiste.getType() == 7) { //LEFT
					if (!(recPlayer.getMinX() - 1 >= recKiste.getMaxX() - (p.getImage().getImage().getWidth(null) * 0.25))) {
						p.setX((feldBreite - 2) * p.getImage().getImage().getWidth(null) + (int) (p.getImage().getImage().getWidth(null) * 0.25));
					}
				}
		}
		} catch (ConcurrentModificationException e) {
		}
		}
	}
	
	/**
	 * Es wird abgerufen ob die Banane bzw. das Hindernis sich berühren.
	 * Die Spieldfeldlänge wird als Paramter mitgegeben.
     * 
	 * @author Dominic Pfister
	 * @param feldBreite {@link int}
	 * @param feldHöhe {@link int}
	 */
	private void checkBananaBounds(int feldBreite, int feldHöhe) {
		boolean isRemoved = true;
		for (Banana banana : bananenArray) {
			Rectangle recBanana = banana.bananaBounds();
			for (Obstacle kiste : obstacleArray) {
				Rectangle recKiste = kiste.obstBounds();
				if (kiste.getType() == 1 || kiste.getType() == 3) {
					if (recBanana.intersects(recKiste)) {
						bananenArray.remove(banana);
						isRemoved = false;
					}
					
					/* **Pipe detection** */
				} else if (kiste.getType() >= 4 && kiste.getType() <= 7) {
					if (kiste.getType() == 4) { //TOP
						if (!(recBanana.getMinY() - 1 >= recKiste.getMaxY() - (banana.getImage().getImage().getWidth(null) * 0.5))) {
							banana.setY((feldHöhe - 2) * this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null)
							+ (int) (banana.getImage().getImage().getWidth(null) * 1.5));
						}
					} else if (kiste.getType() == 5) { //BOTTOM
						if (!(recBanana.getMaxY() - 1 <= recKiste.getMaxY() - (banana.getImage().getImage().getWidth(null) * 1.6))) {
							banana.setY(5 * this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null)
							- (int) (banana.getImage().getImage().getWidth(null) * 0.62));					
						}
					} else if (kiste.getType() == 6) { //RIGHT
						if (!(recBanana.getMaxX() - 1 <= recKiste.getMinX() + (banana.getImage().getImage().getWidth(null) * 0.45))) {
							banana.setX((1 * this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null))
							- (int) (this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) * 0.25) + 1);
						}
					} else if (kiste.getType() == 7) { //LEFT
						if (!(recBanana.getMinX() - 1 >= recKiste.getMaxX() - (banana.getImage().getImage().getWidth(null) * 0.4))) {
							banana.setX((feldBreite - 2) * this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null)
							- (int) (this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) * 0.21));
						}
					}
				}
			}
			if (isRemoved == false) {
				break;
			}
		}
	}

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public List<Player> getPlayerArray() {
		return playerArray;
	}

	public void setPlayerArray(List<Player> playerArray) {
		this.playerArray = playerArray;
	}

	public int getPlayerNr() {
		return playerNr;
	}

	public void setPlayerNr(int playerNr) {
		this.playerNr = playerNr;
	}

	public List<Obstacle> getObstacleArray() {
		return obstacleArray;
	}

	public void setObstacleArray(List<Obstacle> obstacleArray) {
		this.obstacleArray = obstacleArray;
	}

	public List<Banana> getBananenArray() {
		return bananenArray;
	}

	public void setBananenArray(List<Banana> bananenArray) {
		this.bananenArray = bananenArray;
	}

	public long getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(long coolDown) {
		this.coolDown = coolDown;
	}

	public long getRefreshTimer() {
		return refreshTimer;
	}

	public void setRefreshTimer(long refreshTimer) {
		this.refreshTimer = refreshTimer;
	}

	public int getPlayerMaxBananas() {
		return playerMaxBananas;
	}

	public void setPlayerMaxBananas(int playerMaxBananas) {
		this.playerMaxBananas = playerMaxBananas;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
}