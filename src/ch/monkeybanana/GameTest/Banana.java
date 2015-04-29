package ch.monkeybanana.GameTest;

import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Banana extends Obstacle implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8043746736772410176L;

	private char direction;

	private int owner;
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
	public Banana (int x, int y, int type, char direction, int owner) {
		super(x,y,type);
		
		super.setImage(new ImageIcon("images/banana.png"));
		
		this.setOwner(owner);
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


	public int getOwner() {
		return owner;
	}


	public void setOwner(int owner) {
		this.owner = owner;
	}
}