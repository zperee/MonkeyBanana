package ch.monkeybanana.GameTest;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Obstacle {

		private int x = 250;
		private int y = 250;
		private Image image;

		public Obstacle(int x, int y) {
			image =  new ImageIcon("images/crate.png").getImage();
			this.setX(x);
			this.setY(y);
		}
		
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

}