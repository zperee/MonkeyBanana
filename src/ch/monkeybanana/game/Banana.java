package ch.monkeybanana.game;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Klasse um eine Banana mit einem ImageIcon zu erstellen.
 * @author Dominic Pfister, Elia Perenzin Banana.java Copyright 
 * Berufsbildungscenter MonkeyBanana 2015
 */
public class Banana extends Obstacle implements Serializable {

	//Instanzvariablen
	private static final long serialVersionUID = 8043746736772410176L;
	private char direction;
	private int owner;
	
	/**
	 * Erstellt ein neues Banananen Objekt mit den Koordinaten
	 * x, y und einem Typ. Die Bildgroesse wird durch scale 
	 * gesetzt und dann halbiert.
	 * 
	 * @author Dominic Pfister
	 * @param x {@link int}
	 * @param y {@link int}
	 * @param type {@link int}
	 * @param direction {@link char}
	 * @param scale {@link int}
	 * @param owner {@link int}
	 */
	public Banana (int x, int y, int type, char direction, int scale, int owner) {
		super(x, y, type, scale);
		super.setImage(new ImageIcon(Obstacle.class.getResource("/images/banana.png")));
		this.setOwner(owner);
		this.setDirection(direction);
		
		Image scaledImage = super.getImage().getImage(); // transform it
	    scaledImage = scaledImage.getScaledInstance(scale, scale,  java.awt.Image.SCALE_FAST); // scale it the smooth way
	    super.setImage(new ImageIcon(scaledImage));  // transform it back
	}

	/**
	 * Gibt die Hitbox der Banane zurueck. Wird durch die
	 * Super Klasse Obstacle vererbt.
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