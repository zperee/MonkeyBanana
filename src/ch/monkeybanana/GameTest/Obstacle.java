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
		public Obstacle(int x, int y, int type, int scale) {
			this.setType(type);
			
			switch (type) {
			case 0:
				image =  new ImageIcon("images/dirt.png").getImage();
				image = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
				break;
			case 1:
				image =  new ImageIcon("images/crate.png").getImage();
				image = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
				break;
			case 3:
				image =  new ImageIcon("images/palm.png").getImage();
				image = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
				break;
			case 4:
				image =  new ImageIcon("images/Pipe_Down.png").getImage();
				image = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
				break;
			case 5:
				image =  new ImageIcon("images/Pipe_Up.png").getImage();
				image = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
				break;
			case 6:
				image =  new ImageIcon("images/Pipe_Right.png").getImage();
				image = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
				break;
			case 7:
				image =  new ImageIcon("images/Pipe_Left.png").getImage();
				image = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
				break;
			default:
				image =  new ImageIcon("images/dirt.png").getImage();
				image = image.getScaledInstance(scale, scale, java.awt.Image.SCALE_SMOOTH);
			}
				
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