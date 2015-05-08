package ch.monkeybanana.game;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Klasse um den Spieler zu erstellen (Bild)
 * 
 * @author Dominic Pfister, Elia Perenzin Player.java Copyright
 *         Berufsbildungscenter MonkeyBanana 2015
 */

public class Player extends Obstacle {

	private static final long serialVersionUID = -2981485709358783707L;
	public static final int SPEED = 3;
	private int vX, vY;
	private int totalBanana;
	private int coolDown;

	/**
	 * Konstruktor der Klasse Player. Ein neuer Spieler wird an
	 * der x und y Positionen erstellt.
	 * 
	 * @author Dominic Pfister
	 * @param x {@link int}
	 * @param y {@link int}
	 * @param totalBanana {@link int}
	 * @param cooldown {@link int}
	 * @param type {@link int}
	 * @param scale {@link int}
	 */
	public Player(int x, int y, int totalBanana, int cooldown, int type, int scale) {
		super(x, y, type, scale);
		this.setTotalBanana(totalBanana);
		this.setCoolDown(cooldown);
		
		switch (type) {
		case 1:
			super.setImage(new ImageIcon("images/monkeyRed.png"));
			break;
		case 2:
			super.setImage(new ImageIcon("images/monkeyBlue.png"));
			break;
		default:
			super.setImage(new ImageIcon("images/pokemon.png"));
			break;
		}
		Image scaledImage = super.getImage().getImage(); // transform it 
	    scaledImage = scaledImage.getScaledInstance(scale, scale + scale/2,  java.awt.Image.SCALE_FAST); // scale it the smooth way  
	    super.setImage(new ImageIcon(scaledImage));  // transform it back
	}

	public void move() {
		super.setX(x += this.getvX());
		super.setY(y += this.getvY());
	}
	
	/**
	 * Gibt die Hitbox des Spielers zurück
	 * 
	 * @author Dominic Pfister
	 */
	public Rectangle playerBounds() {
		super.obstBounds();
		return  new Rectangle(super.getX(), super.getY() + super.getImage().getImage().getWidth(null) / 2,
				super.getImage().getImage().getWidth(null), 
				super.getImage().getImage().getHeight(null) - super.getImage().getImage().getWidth(null) / 2);
	}


    /* **ALTER LISTENER** */
//	public void keyPressed(KeyEvent e) {
//		switch (e.getKeyCode()) {
//		case KeyEvent.VK_DOWN:
//			down = true;
//			break;
//		case KeyEvent.VK_UP:
//			up = true;
//			break;
//		case KeyEvent.VK_LEFT:
//			left = true;
//			break;
//		case KeyEvent.VK_RIGHT:
//			right = true;
//			break;
//		}
//		update();
//	}
//
//	public void keyReleased(KeyEvent e) {
//		switch (e.getKeyCode()) {
//		case KeyEvent.VK_DOWN:
//			down = false;
//			break;
//		case KeyEvent.VK_UP:
//			up = false;
//			break;
//		case KeyEvent.VK_LEFT:
//			left = false;
//			break;
//		case KeyEvent.VK_RIGHT:
//			right = false;
//			break;
//		}
//		update();
//	}
    
//	private void update() {
//		vX = 0;
//		vY = 0;
//
//		if (down) {
//			vY = SPEED;
//		}
//		if (right) {
//			vX = SPEED;
//		}
//		if (left) {
//			vX = -SPEED;
//		}
//		if (up) {
//			vY = -SPEED;
//		}
//	}

	/* **GETTER SETTER** */
	public int getTotalBanana() {
		return totalBanana;
	}

	public void setTotalBanana(int totalBanana) {
		this.totalBanana = totalBanana;
	}

	public int getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
	}

	public int getvX() {
		return vX;
	}

	public void setvX(int vX) {
		this.vX = vX;
	}
	
	public int getvY() {
		return vY;
	}

	public void setvY(int vY) {
		this.vY = vY;
	}
}