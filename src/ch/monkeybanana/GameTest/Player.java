package ch.monkeybanana.GameTest;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Klasse um den Spieler zu erstellen (Bild)
 * 
 * @author Dominic Pfister, Elia Perenzin Player.java Copyright
 *         Berufsbildungscenter MonkeyBanana 2015
 */

public class Player extends Obstacle {
	
	public static final int SPEED = 2;
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
	 */
	public Player(int x, int y, int totalBanana, int cooldown, int scale, int type) {
		super(x,y,type,scale);
		
		switch (type) {
		case 1:
			super.setImage(new ImageIcon("images/monkeyRed.png").getImage());
			break;
		case 2:
			super.setImage(new ImageIcon("images/monkeyBlue.png").getImage());
			break;
		default:
			super.setImage(new ImageIcon("images/pokemon.png").getImage());
			break;
		}
		
		super.setImage(super.getImage().getScaledInstance(scale, scale + scale / 2, java.awt.Image.SCALE_SMOOTH));
		
		this.setTotalBanana(totalBanana);
		this.setCoolDown(cooldown);
	}

	/**
	 * This is the only method that the client should ever call! Instead of
	 * moving a sprite when you press a button, you should be moving a sprite
	 * while certain states hold. Also, do not animate the sprite in the event
	 * handler! Animate it in your main rendering loop!! That means that your
	 * main rendering loop will call player.go() and nothing more. This will
	 * result in smooth animation. If you rely on animating the sprite in the
	 * keypressed method, the sprite will only move when an event is fired.
	 * That's ugly!!
	 */
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
		return  new Rectangle(super.getX(), super.getY() + super.getImage().getWidth(null) / 2,
				super.getImage().getWidth(null), 
				super.getImage().getHeight(null) - super.getImage().getWidth(null) / 2);
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