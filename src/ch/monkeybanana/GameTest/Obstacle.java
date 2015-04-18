package ch.monkeybanana.GameTest;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Obstacle {
	
		private Image image;
		
		private int x, y, type;

		/**
		 * Erstellt ein neues Obstacle Objekt mit den Koordinaten
		 * x, y und einem Typ.
		 * 
		 * @author Dominic Pfister
		 * @param x {@link int}
		 * @param y {@link int}
		 * @param type {@link int}
		 */
		public Obstacle(int x, int y, int type) {
			image =  new ImageIcon("images/crate.png").getImage();
			this.setX(x);
			this.setY(y);
		}
		
		/**
		 * Gibt die Hitbox des Obstacle Objektes
		 * zurück
		 * 
		 * @author Dominic Pfister
		 */
		public Rectangle obstBounds() {
			return new Rectangle(this.getX(), this.getY(), image.getWidth(null), image.getHeight(null));
		}
		
		
		/* **GETTER SETTER** */
		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
		public Image getImage() {
			return image;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setImage(Image image) {
			this.image = image;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

}