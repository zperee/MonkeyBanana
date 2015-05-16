package ch.monkeybanana.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import ch.monkeybanana.listener.GameListener;
import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;
import ch.monkeybanana.view.ScoreView;

/**
 * Klasse f�r den Inhalt des JFrames. Sendet Koordinaten
 * der Spieler und Bananen an den Server und ist f�r
 * die ganze Spiellogik zust�ndig.
 * @author Dominic Pfister, Elia Perenzin
 */

public class Gameboard extends JPanel implements ActionListener {

	//Instanzvariablen
	private static final long serialVersionUID = -8488779096979941350L;
	private Timer timer;
	private Player p1, p2;
	private int playerNr;
	private boolean run = true;

	List<Obstacle> obstacleArray = new ArrayList<Obstacle>();
	List<Banana> bananenArray = new ArrayList<Banana>();
	List<Player> playerArray = new ArrayList<Player>();

	private long coolDown, refreshTimer;
	private int playerMaxBananas;
	private boolean isModified, isRestarted;
	private User user;
	
	private JFrame frame;

	/**
	 * Erstellt ein neues Spielbrett.
	 * @author Dominic Pfister, Elia Perenzin
	 * @param spielerNr {@link int}
	 * @param u {@link User}
	 * @param frame {@link JFrame}
	 * @throws RemoteException 
	 */
	public Gameboard(int spielerNr, User u, JFrame frame) throws RemoteException {
		this.setPlayerNr(spielerNr);
		setFocusable(true);
		setDoubleBuffered(true);
		
		this.setUser(u);
		this.setFrame(frame);
		
		isModified = false;
		refreshTimer = System.currentTimeMillis();

		p1 = new Player(48, 168, 15, 500, 1, 48);
		p2 = new Player(576, 696, 15, 500, 2, 48);
		playerArray.add(this.getP1());
		playerArray.add(this.getP2());
		

		playerMaxBananas = 15;

		/* Wartet fuer 100ms bis das Spieler Image neu skaliert wurde */
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		timer = new Timer(10, this);
		timer.start();

		if (!isRestarted && !this.getUser().getUsername().equals("SYSTEM")) {
			generateMap(48);
			Client.getInstance().getConnect().setPlayer(this.getUser().getUsername(), playerNr);
		}
	}

	/**
	 * Erhoeht die Anzahl der Bananen des Spielers.
	 * @author Dominic Pfister, Elia Perenzin
	 * @param time {@link long}
	 */
	private void increaseBanana(long time) {
		int i = 0;
		if (refreshTimer + time <= System.currentTimeMillis()) {
			while (this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() < this.getPlayerMaxBananas()
					&& i < 5) {
				this.getPlayerArray().get(this.getPlayerNr()).setTotalBanana(this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() + 1);
				i++;
				this.setRefreshTimer(System.currentTimeMillis());
			}
		}
	}

	/**
	 * Erstellt eine neue Banane und sendet diese
	 * an den Server.
	 * @author Dominic Pfister
	 */
	private void generateBanana() {
		int xPos = 0;
		int yPos = 0;
		int type;
		int owner;
		char dir = 'k';

		/*
		 *  **LEGENDE**
		 * 
		 * type: 1 = Bananenschale 2 = geworfene Bananen
		 * 
		 * direction: u = up d = down r = right l = left
		 */

		if (this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() > 0
				&& coolDown <= System.currentTimeMillis()) {

			if (GameListener.isBananaPeel()) {
				type = 1;
				xPos = this.getPlayerArray().get(this.getPlayerNr()).getX() 
					   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
				yPos = this.getPlayerArray().get(this.getPlayerNr()).getY()
					   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null)
					   - this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
				owner = this.getPlayerNr();

				Banana banana = new Banana(xPos, yPos, type, 'k', 48 / 2, owner);
				bananenArray.add(banana);

				GameListener.setBananaPeel(false);
				GameListener.setAllowBanana(false);
				coolDown = (System.currentTimeMillis() + this.getPlayerArray().get(this.getPlayerNr()).getCoolDown());
				this.getPlayerArray().get(this.getPlayerNr()).setTotalBanana(this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() - 1);

				try {
					Client.getInstance().getConnect().tellBanana(banana);
				}
				catch (RemoteException e) {
					e.printStackTrace();
				}

			}
			else if (GameListener.isBananaThrown()) {
				type = 2;
				owner = this.getPlayerNr();

				if (GameListener.isUp()) {
					xPos = this.getPlayerArray().get(this.getPlayerNr()).getX()
						   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					yPos = this.getPlayerArray().get(this.getPlayerNr()).getY()
						   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					dir = 'u';
				}
				else if (GameListener.isDown()) {
					xPos = this.getPlayerArray().get(this.getPlayerNr()).getX()
						   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					yPos = this.getPlayerArray().get(this.getPlayerNr()).getY()
					       + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null)
						   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					dir = 'd';
				}
				else if (GameListener.isRight()) {
					xPos = this.getPlayerArray().get(this.getPlayerNr()).getX()
						   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					yPos = this.getPlayerArray().get(this.getPlayerNr()).getY()
						   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null)
						   - this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					dir = 'r';
				}
				else if (GameListener.isLeft()) {
					xPos = this.getPlayerArray().get(this.getPlayerNr()).getX()
						   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					yPos = this.getPlayerArray().get(this.getPlayerNr()).getY()
						   + this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null)
						   - this.getPlayerArray().get(this.getPlayerNr()).getImage().getImage().getWidth(null) / 4;
					dir = 'l';
				}

				if (xPos != 0 && xPos != 0) { // Achtet darauf, dass am Anfang
										      // keine sBananen geworfen werden
					owner = this.getPlayerNr();
					Banana banana = new Banana(xPos, yPos, type, dir, 48 / 2,owner);
					bananenArray.add(banana);
					GameListener.setAllowBanana(false);
					GameListener.setBananaThrown(false);
					coolDown = (System.currentTimeMillis()
							    + this.getPlayerArray().get(this.getPlayerNr()).getCoolDown());

					this.getPlayerArray().get(this.getPlayerNr()).setTotalBanana(this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana() - 1);

					try {
						Client.getInstance().getConnect().tellBanana(banana);
					}
					catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else if (coolDown <= System.currentTimeMillis()) {
			GameListener.setUp(false);
			GameListener.setDown(false);
			GameListener.setRight(false);
			GameListener.setLeft(false);
			GameListener.setBananaPeel(false);
			GameListener.setBananaThrown(false);
		}
	}

	/**
	 * Erstellt die Karte fuer das Spiel mit den Hindernissen
	 * @author Dominic Pfister
	 * @param mapSize {@link int}
	 */
	private void generateMap(int mapSize) {
		/*
		 * TODO !KannZiel map einlesen (verschiedene Maps)
		 */

		/*
		 * **LEGENDE** 
		 * 0 = next Line 
		 * 0-13 = Ground 
		 * 14-29 = Border
		 	* 14 = corner_bottom_left 
		 	* 15 = corner_bottom_right
		 	* 16 = corner_top_left
		 	* 17 = corner_top_right
		 	* 18-20 = bottom
		 	* 21-23 = left
		 	* 24-26 = right
		 	* 27-29 = top
		 * 51 = pipe_up
		 * 52 = pipe_down
		 * 53 = pipe_right
		 * 54 = pipe_left
		 * 55 = Brick 
		 * 56 = Brick_moss
		 * 57-69 = Water
		 */
		int[] map = { 40, 16, 29, 28, 27, 29, 27, 28, 27, 29, 28, 27, 29, 17, 40,  0,
					  40, 22, 55, 56, 55, 55, 55, 56, 55, 56, 56, 55, 55, 25, 40,  0,
					  40, 14, 19, 20, 19, 20, 18, 19, 18, 20, 18, 20, 19, 15, 40,  0,
					  16, 27, 29, 28, 27, 34, 52, 33, 28, 29, 28, 17, 41, 40, 40,  0,
					  23,  1,  1, 13, 1,  1,  1,  1,  1,   1,  1, 33, 27, 28, 17,  0,
					  21,  2, 35, 18, 19, 36,  1, 35, 18, 36,  1,  1,  1,  1, 26,  0,
					  34,  3, 33, 31, 17, 38,  1, 33, 31, 34,  1, 35, 36,  1, 33,  0,
					  54,  8, 13,  7, 26, 22,  1,  1,  1,  1,  1, 25, 22,  1, 53,  0,
					  18, 19, 36,  8, 24, 14, 36,  1, 35, 20, 18, 15, 21,  1, 35,  0,
					  40, 39, 22,  1, 33, 30, 34,  1, 33, 32, 31, 30, 34,  1, 24,  0,
					  40, 16, 34,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 26,  0,
					  40, 23,  1,  1, 35, 36,  1, 35, 18, 19, 20, 36,  1, 35, 15,  0,
					  40, 21,  1, 35, 15, 21,  1, 33, 28, 29, 17, 22,  1, 24, 40,  0,
					  40, 23,  1, 33, 17, 22,  1,  1,  1,  1, 24, 23,  1, 26, 40,  0,
					  40, 22,  1,  1, 33, 34,  1, 35, 36,  1, 33, 34,  1, 25, 40,  0,
					  40, 14, 36,  1,  1,  1,  5, 37, 38,  1,  1,  1,  1, 24, 40,  0,
					  40, 40, 14, 20, 19, 36, 51, 25, 14, 20, 18, 20, 19, 15, 40,  0  };

		/* TEMPLATE */
//		int[] map = { 40, 16, 29, 28, 27, 29, 27, 28, 27, 29, 28, 27, 29, 17, 40,  0,
//				  	  40, 22, 55, 56, 55, 55, 55, 56, 55, 56, 56, 55, 55, 25, 40,  0,
//				  	  40, 14, 19, 20, 19, 20, 18, 19, 18, 20, 18, 20, 19, 15, 40,  0,
//				  	  16, 27, 29, 28, 27, 29, 27, 27, 28, 29, 28, 29, 27, 29, 17,  0,
//				  	  23,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 25,  0,
//				  	  21,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 26,  0,
//				  	  22,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 24,  0,
//				  	  21,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 26,  0,
//				  	  23,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 25,  0,
//				  	  22,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 24,  0,
//				  	  23,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 26,  0,
//				  	  21,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 25,  0,
//				  	  23,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 26,  0,
//				  	  23,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 24,  0,
//				  	  22,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 26,  0,
//				  	  21,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 25,  0,
//				  	  14, 18, 19, 20, 19, 20, 18, 19, 18, 20, 18, 20, 19, 19, 15,  0  };

		int posX = 0;
		int posY = 0;

		// Generiert Karte aufgrund von int[] map
		for (int s : map) {
			Obstacle kiste = new Obstacle(posX, posY, s, 48);
			if (!isModified) {
				obstacleArray.add(kiste);
			}
			if (s >= 1) {
				posX = posX + mapSize;
			}
			else if (s == 0) {
				posX = 0;
				posY = posY + mapSize;
			}
		}
		isModified = true;
	}

	/**
	 * Funktion um die Objekte zu zeichnen
	 * @author Dominic Pfister
	 * @param g {@link Graphics}
	 */
	public void paint(Graphics g) {
		super.paint(g);

		/* Zeichnet die Hindernisse */
		for (Obstacle kiste : obstacleArray) {
			g.drawImage(kiste.getImage().getImage(), kiste.getX(), kiste.getY(), this);
			// Hitbox fuer Hindernis
//			 g.setColor(Color.RED);
//			 g.drawRect(kiste.getX(), kiste.getY(),
//			 kiste.getImage().getImage().getWidth(null),
//			 kiste.getImage().getImage().getHeight(null));
		}

		/* Zeichnet die Bananen */
		try {
		for (Banana banana : bananenArray) { // Erhöht die Koordinaten für
												// geworfene Bananen
			if (banana.getType() == 2) {

				char dir = banana.getDirection(); // Erhöht Positionen der
													// Bananen
				switch (dir) {
				case ('u'):
					banana.setY(banana.getY() - Player.SPEED - 2);
					break;
				case ('d'):
					banana.setY(banana.getY() + Player.SPEED + 2);
					break;
				case ('r'):
					banana.setX(banana.getX() + Player.SPEED + 2);
					break;
				case ('l'):
					banana.setX(banana.getX() - Player.SPEED - 2);
					break;
				}
			}
			
			g.drawImage(banana.getImage().getImage(), banana.getX(),
					banana.getY(), this);

			// Hitbox fuer Bananen
			// g.setColor(Color.ORANGE);
			// g.drawRect(banana.getX(), banana.getY(),
			// banana.getImage().getImage().getWidth(null),
			// banana.getImage().getImage().getHeight(null));
			
		}
		} catch (ConcurrentModificationException e){
			System.out.println("Fehler 1  //GameBoard 404");
		}

		/* Zeichnet die Spieler */
		for (Player p : playerArray) {
			g.drawImage(p.getImage().getImage(), p.getX(), p.getY(), this);
		}

		// Hitbox fuer player
//		 g.setColor(Color.GREEN);
//		 g.drawRect(p1.getX(), p1.getY() +
//		 p1.getImage().getImage().getWidth(null) / 2,
//		 p1.getImage().getImage().getWidth(null),
//		 p1.getImage().getImage().getHeight(null) -
//		 p1.getImage().getImage().getWidth(null) / 2);

		/* Zeichnet den Punktestand und die Bananen Anzahl */
		g.setFont(new Font("TimesRoman", Font.BOLD, 42));
		g.setColor(Color.BLACK);
		
		//Bananen Anzahl
		Image banana_tree = Toolkit.getDefaultToolkit().getImage("images/banana_tree.png");
		g.drawImage(banana_tree, 150, 55, this);
		g.drawString(String.valueOf(this.getPlayerArray().get(this.getPlayerNr()).getTotalBanana()), 190, 85);
		
		//Punktestand
		Image player1 = Toolkit.getDefaultToolkit().getImage("images/monkeyBlue.png");
		g.drawImage(player1, 480, 45, this);
		Image player2 = Toolkit.getDefaultToolkit().getImage("images/monkeyRed.png");
		g.drawImage(player2, 380, 45, this);
		
		try {
			int[] result = Client.getInstance().getConnect().getResult();
			g.drawString(String.valueOf(result[0]), 528, 85);
			g.drawString(String.valueOf(result[1]), 432, 85);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		

		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Zeichnet alle Objekte neu, pr�ft ob ein Objekt
	 * an einem Hindernis anst�sst und prueft ob ein
	 * Spieler bereits getroffen wurde.
	 * @author Dominic Pfister, Elia Perenzin
	 */
	public void actionPerformed(ActionEvent e) {
		for (Player p : playerArray) {
			p.move();
		}
		repaint();
		checkBounds(15, 18);
		checkBananaBounds(15, 18);
		generateBanana();
		increaseBanana(10000);
		checkBananaHit();
		if (!this.getUser().getUsername().equals("SYSTEM")) {
			restartGame();
		}
	}

	/**
	 * Es wird abgerufen ob der Spieler bzw. das Hindernis sich beruehren. Die
	 * Spieldfeldlaenge wird als Paramter mitgegeben.
	 * @author Dominic Pfister
	 * @param feldBreite {@link int}
	 * @param feldHoehe {@link int}
	 */
	private void checkBounds(int feldBreite, int feldHoehe) {
		for (Player p : this.getPlayerArray()) {
			Rectangle recPlayer = p.playerBounds();
			try {
				for (Obstacle kiste : obstacleArray) {
					Rectangle recKiste = kiste.obstBounds();

					/* **Normal Hindernis detection** */
					if (kiste.getType() >= 14 && kiste.getType() <= 50 || kiste.getType() >= 57 && kiste.getType() <= 69) { 
						if (recPlayer.intersects(recKiste)) {
							if (recPlayer.getMaxY() - 1 <= recKiste.getMaxY() // TOP
								&& !(recPlayer.getMinY() - 1 >= (recKiste.getMaxY() - 4))
								&& !(recPlayer.getMaxX() - 1 <= recKiste.getMinX() + 4)
								&& !(recPlayer.getMinX() - 1 >= recKiste.getMaxX() - 4)) {
								
								p.setY((int) recKiste.getMinY() - p.getImage().getImage().getHeight(null));
							}
							else if (recPlayer.getMinY() - 1 >= (recKiste.getMaxY() - 4)) { // BOTTOM
								p.setY((int) recKiste.getMaxY()
						        - p.getImage().getImage().getHeight(null) / 3);
							}
							else if (recPlayer.getMaxX() - 1 <= recKiste.getMinX() + 4) { // RIGHT
								p.setX((int) recKiste.getMinX()
								- p.getImage().getImage().getWidth(null));
							}
							else if (recPlayer.getMinX() - 1 >= recKiste.getMaxX() - 4) { // LEFT
								p.setX((int) recKiste.getMaxX());
							}
						}
					}
					
					/* **Pipe detection** */
					else if (kiste.getType() == 51 && !user.getUsername().equals("SYSTEM")) { // TOP
						if (!(recPlayer.getMinY() + 84 <= recKiste.getMaxY())) {
							p.setY(kiste.getY() - 609);
						}
					}
					else if (kiste.getType() == 52 && !user.getUsername().equals("SYSTEM")) { // BOTTOM
						if (!(recPlayer.getMinY() + 9 >= recKiste.getMaxY())) {
							p.setY(kiste.getY() + 563);
						}
					}
					else if (kiste.getType() == 53 && !user.getUsername().equals("SYSTEM")) { // RIGHT
						if (!(recPlayer.getMaxX()  - 12 <= recKiste.getMinX())) {
							p.setX(kiste.getX() - 635);
						}
					}
					else if (kiste.getType() == 54 && !user.getUsername().equals("SYSTEM")) { // LEFT
						if (!(recPlayer.getMinX() + 12 >= recKiste.getMaxX())) {
							p.setX(kiste.getX() + 635);
						}
					}
				}
			}
			catch (ConcurrentModificationException e) {
			}
		}
	}

	/**
	 * Es wird abgerufen ob die Banane bzw. das Hindernis sich beruehren. Die
	 * Spieldfeldlaenge wird als Paramter mitgegeben.
	 * @author Dominic Pfister
	 * @param feldBreite {@link int}
	 * @param feldHoehe {@link int}
	 */
	private void checkBananaBounds(int feldBreite, int feldHoehe) {
		boolean isRemoved = true;
		try {
		for (Banana banana : bananenArray) {
			Rectangle recBanana = banana.bananaBounds();
			for (Obstacle kiste : obstacleArray) {
				Rectangle recKiste = kiste.obstBounds();
				
				/* Normales Hindernis detection */
				if (kiste.getType() >= 14 && kiste.getType() <= 50 || kiste.getType() >= 57 && kiste.getType() <= 69) {
					if (recBanana.intersects(recKiste)) {
						bananenArray.remove(banana);
						isRemoved = false;
					}
					
				/* **Pipe detection** */
				} else if (kiste.getType() >= 51 && kiste.getType() <= 54) {
					if (kiste.getType() == 51) { // TOP
						if (!(recBanana.getMinY() + 65 <= recKiste.getMaxY())) {
							banana.setY(kiste.getType() + 127);
						}
					}
					else if (kiste.getType() == 52) { // BOTTOM
						if (!(recBanana.getMaxY() - 15 >= recKiste.getMaxY())) {
							banana.setY(kiste.getY() + 612);
						}
					}
					else if (kiste.getType() == 53) { // RIGHT
						if (!(recBanana.getMaxX() - 10 <= recKiste.getMinX())) {
							banana.setX(kiste.getX() - 636);
						}
					}
					else if (kiste.getType() == 54) { // LEFT
						if (!(recBanana.getMinX() + 12 >= recKiste.getMaxX())) {
							banana.setX(kiste.getX() + 660);
						}
					}
				}
			}
			if (isRemoved == false) {
				break;
			}
		}
		} catch (ConcurrentModificationException e) {
			System.out.println("Fehler 2  //GameBoard 597");
		}
	}

	/**
	 * Funktion um zu pruefen ob einer der beiden
	 * Spieler von einer gegnerischen Bananen
	 * getroffen wurde.
	 * @author Dominic Pfister, Elia Perenzin
	 */
	private void checkBananaHit() {
		Rectangle recPlayer;
		if (this.isRun()) {
			for (Banana b : bananenArray) {
				
				if (b.getOwner() != playerNr) {
					Rectangle recBanana = b.bananaBounds();
					if (playerNr == 0) {
						recPlayer = p1.playerBounds();
					}
					else {
						recPlayer = p2.playerBounds();
					}

					if (recPlayer.intersects(recBanana)) {
						this.setRun(false);
						try {
							Client.getInstance().getConnect().score(playerNr);
							Client.getInstance().getConnect().setHit(true);
							if (!this.getUser().getUsername().equals("SYSTEM")) {
								Client.getInstance().getConnect().setRundenzahl(Client.getInstance().getConnect().getRundenzahl() + 1);
							}
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * Setzt das Spielfeld wieder auf die Ausgangslage zurueck.
	 * Die Spieler werden zurueck teleportiert und die Bananen
	 * auf die max Anzahl zurueck gesetzt.
	 * @author Dominic Pfister, Elia Perenzin
	 */
	private void restartGame() {
		try {
			if (Client.getInstance().getConnect().isHit()
				&& Client.getInstance().getConnect().getRundenzahl() <= 5) {
				this.getBananenArray().clear();
				
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//Zuruecksetzen der Spieler
				p1.setX(48);
				p1.setY(168);
				p1.setTotalBanana(15);
				p2.setX(576);
				p2.setY(696);
				p2.setTotalBanana(15);
				
				this.setRun(true);
				Client.getInstance().getConnect().setHit(false);
				
			} else if (Client.getInstance().getConnect().getRundenzahl() > 4) {
				boolean winner;
				int player2;
				
				timer.stop();
				
				if (this.getPlayerNr() == 0) {
					player2 = 1;
				} else {
					player2 = 0;
				}
				if (Client.getInstance().getConnect().getScore(this.getPlayerNr()) < 
					Client.getInstance().getConnect().getScore(player2)) {
					winner = true;
				} else {
					winner = false;
				}
				int[] score = Client.getInstance().getConnect().getResult();
				String pl1 = Client.getInstance().getConnect().getPlayer(0);
				String pl2 = Client.getInstance().getConnect().getPlayer(1);
				
				new ScoreView(score[0], score[1], pl1, pl2, winner);
				
				Client.getInstance().getConnect().setSlots(Client.getInstance().getConnect().getSlots() - 1);
				
				if (this.getPlayerNr() == 0) {
					Client.getInstance().getConnect().restartServer();
				}
				
				this.getFrame().dispose();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/* **GETTER und SETTER** */
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

	public boolean isRestarted() {
		return isRestarted;
	}

	public void setRestarted(boolean isRestarted) {
		this.isRestarted = isRestarted;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}