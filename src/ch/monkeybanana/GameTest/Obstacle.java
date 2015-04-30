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
		protected int scale;
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
		public Obstacle(int x, int y, int type, int scale) {
			this.setType(type);
			this.setX(x);
			this.setY(y);
			this.setScale(scale);
			
			switch (type) {
			case 0:
				image =  new ImageIcon("images/dirt.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
				break;
			case 1:
				image =  new ImageIcon("images/crate.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
				break;
			case 3:
				image =  new ImageIcon("images/palm.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
				break;
			case 4:
				image =  new ImageIcon("images/Pipe_Down.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
				break;
			case 5:
				image =  new ImageIcon("images/Pipe_Up.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
				break;
			case 6:
				image =  new ImageIcon("images/Pipe_Right.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
				break;
			case 7:
				image =  new ImageIcon("images/Pipe_Left.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
				break;
			case 8:
				image =  new ImageIcon("images/cube.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
				break;
			default:
				image =  new ImageIcon("images/dirt.png");
				image.setImage(image.getImage().getScaledInstance(scale, scale, Image.SCALE_FAST));
			}
			Image scaledImage = image.getImage(); // transform it 
		    scaledImage = scaledImage.getScaledInstance(scale, scale,  java.awt.Image.SCALE_FAST); // scale it the smooth way  
		    image = new ImageIcon(scaledImage);  // transform it back
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

		public int getScale() {
			return scale;
		}

		public void setScale(int scale) {
			this.scale = scale;
		}

}