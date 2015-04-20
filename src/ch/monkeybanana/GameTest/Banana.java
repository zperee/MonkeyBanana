package ch.monkeybanana.GameTest;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Banana {

	
	private Image image;
	
	private int x, y, type;

	/**
	 * Erstellt ein neues Banananen Objekt mit den Koordinaten
	 * x, y und einem Typ.
	 * 
	 * @author Dominic Pfister
	 * @param x {@link int}
	 * @param y {@link int}
	 * @param type {@link int}
	 */
	public Banana(int x, int y, int type) {
		image =  new ImageIcon("images/banana.png").getImage();
		this.setX(x);
		this.setY(y);
		this.setType(type);
	}

	/* **GETTER und SETTER** */
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
