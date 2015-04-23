package ch.monkeybanana.GameTest;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Banana extends Obstacle {

	
	private char direction;

	/**
	 * Erstellt ein neues Banananen Objekt mit den Koordinaten
	 * x, y und einem Typ. Die Bildgrösse wird durch scale 
	 * gesetzt und dann halbiert.
	 * 
	 * @author Dominic Pfister
	 * @param x {@link int}
	 * @param y {@link int}
	 * @param type {@link int}
	 * @param direction {@link char}
	 * @param scale {@link int}
	 */
	public Banana (int x, int y, int type, char direction, int scale) {
		super(x,y,type,scale);
		scale = scale / 2;
		
		super.setImage(new ImageIcon("images/banana.png").getImage());
		super.setImage(super.getImage().getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH));
		
		this.setDirection(direction);
	}

	
	/**
	 * Gibt die Hitbox der Banane zurueck
	 * 
	 * @author Dominic Pfister
	 */
	public Rectangle bananaBounds() {
		return super.obstBounds();
	}

	/* **GETTER und SETTER** */
	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}
}