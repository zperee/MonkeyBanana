package ch.monkeybanana.GameTest;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Obstacle implements Serializable{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -1575282248261096070L;

		private ImageIcon image;
		
		protected int x = 0;

		protected int y = 0;

		private int type;

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
			this.setType(type);
			
			switch (type) {
			case 0:
				image =  new ImageIcon("images/dirt.png");
				break;
			case 1:
				image =  new ImageIcon("images/crate.png");
				break;
			case 3:
				image =  new ImageIcon("images/palm.png");
				break;
			case 4:
				image =  new ImageIcon("images/Pipe_Down.png");
				break;
			case 5:
				image =  new ImageIcon("images/Pipe_Up.png");
				break;
			case 6:
				image =  new ImageIcon("images/Pipe_Right.png");
				break;
			case 7:
				image =  new ImageIcon("images/Pipe_Left.png");
				break;
			case 8:
				image =  new ImageIcon("images/cube.png");
				break;
			default:
				image =  new ImageIcon("images/dirt.png");
			}
				
			this.getImage().getImage().getScaledInstance(48, 48, Image.SCALE_FAST);
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
			return new Rectangle(this.getX(), this.getY(), image.getImage().getWidth(null), image.getImage().getHeight(null));
		}
		
		
		/* **GETTER SETTER** */
		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
		public ImageIcon getImage() {
			return image;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setImage(ImageIcon imageIcon) {
			this.image = imageIcon;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

}