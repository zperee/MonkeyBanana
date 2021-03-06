package ch.monkeybanana.game;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Ein Hindernis Objekt.
 * @author Dominic Pfister, Elia Perenzin
 */
public class Obstacle implements Serializable{
	
	//Instanzvariablen
	private static final long serialVersionUID = -1575282248261096070L;
	private ImageIcon image;
	protected int x = 0, y = 0, scale, type;

	/**
	 * Erstellt ein neues Obstacle Objekt mit den Koordinaten
	 * x, y und einem Typ.
	 * @author Dominic Pfister
	 * @param x {@link int}
	 * @param y {@link int}
	 * @param type {@link int}
	 * @param scale {@link int}
	 */
	public Obstacle(int x, int y, int type, int scale) {
		this.setType(type);
		this.setX(x);
		this.setY(y);
		this.setScale(scale);
			
		switch (type) {
			case 0:
				image =  new ImageIcon(Obstacle.class.getResource("/images/banana_tree.png"));
				break;
					
			/* **BODEN** */
			case 1:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/ground_grass.png"));
				break;
			case 2:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/flower/ground_flower1.png"));
				break;
			case 3:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/flower/ground_flower2.png"));
				break;
			case 4:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/flower/ground_flower3.png"));
				break;
			case 5:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/flower/ground_flower4.png"));
				break;
			case 6:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/normal/ground1.png"));
				break;
			case 7:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/normal/ground2.png"));
				break;
			case 8:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/normal/ground3.png"));
				break;
			case 9:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/normal/ground4.png"));
				break;
			case 10:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/normal/ground5.png"));
				break;
			case 11:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/normal/ground6.png"));
				break;
			case 12:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/normal/ground7.png"));
				break;
			case 13:
				image =  new ImageIcon(Obstacle.class.getResource("/images/ground/normal/ground8.png"));
				break;
				
			/* **BORDER** */
			case 14:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/corner_bottom_left.png"));
				break;
			case 15:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/corner_bottom_right.png"));
				break;
			case 16:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/corner_top_left.png"));
				break;
			case 17:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/corner_top_right.png"));
				break;		
			case 18:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/bottom/bush_bottom1.png"));
				break;
			case 19:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/bottom/bush_bottom2.png"));
				break;
			case 20:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/bottom/bush_bottom3.png"));
				break;
			case 21:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/left/bush_left1.png"));
				break;
			case 22:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/left/bush_left2.png"));
				break;
			case 23:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/left/bush_left3.png"));
				break;				
			case 24:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/right/bush_right1.png"));
				break;
			case 25:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/right/bush_right2.png"));
				break;
			case 26:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/right/bush_right3.png"));
				break;				
			case 27:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/top/bush_top1.png"));
				break;
			case 28:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/top/bush_top2.png"));
				break;
			case 29:
				image =  new ImageIcon(Obstacle.class.getResource("/images/borderPieces/top/bush_top3.png"));
				break;
					
			/* **HINDERNISSE** */
			case 30:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/bottom1.png"));
				break;
			case 31:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/bottom2.png"));
				break;
			case 32:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/bottom3.png"));
				break;
			case 33:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/corner_bottom_left.png"));
				break;
			case 34:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/corner_bottom_right.png"));
				break;
			case 35:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/corner_top_left.png"));
				break;
			case 36:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/corner_top_right.png"));
				break;
			case 37:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/middle_left.png"));
				break;
			case 38:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/middle_right.png"));
				break;
			case 39:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/middle1.png"));
				break;
			case 40:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/middle2.png"));
				break;
			case 41:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/middle3.png"));
				break;
			case 42:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/top1.png"));
				break;
			case 43:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/top2.png"));
				break;
			case 44:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bush/top3.png"));
				break;
				
			case 45:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bushLong/bottom.png"));
				break;
			case 46:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bushLong/middle.png"));
				break;
			case 47:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/bushLong/top.png"));
				break;
				
			case 48:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/singleTree/tree1.png"));
				break;
			case 49:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/singleTree/tree2.png"));
				break;
			case 50:
				image =  new ImageIcon(Obstacle.class.getResource("/images/obstacle/singleTree/tree3.png"));
				break;
					
			/* **PIPES** */
			case 51:
				image =  new ImageIcon(Obstacle.class.getResource("/images/Pipe_Up.png"));
				break;
			case 52:
				image =  new ImageIcon(Obstacle.class.getResource("/images/Pipe_Down.png"));
				break;
			case 53:
				image =  new ImageIcon(Obstacle.class.getResource("/images/Pipe_right.png"));
				break;
			case 54:
				image =  new ImageIcon(Obstacle.class.getResource("/images/Pipe_Left.png"));
				break;
					
			/* **BRICK** */
			case 55:
				image =  new ImageIcon(Obstacle.class.getResource("/images/brick.png"));
				break;
			case 56:
				image =  new ImageIcon(Obstacle.class.getResource("/images/brick_moss.png"));
				break;
				
			/* **WATER** */
			case 57:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/deadEnd/bottom.png"));
				break;
			case 58:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/deadEnd/left.png"));
				break;
			case 59:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/deadEnd/right.png"));
				break;
			case 60:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/deadEnd/top.png"));
				break;
			case 61:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/bottom_left.png"));
				break;
			case 62:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/bottom_middle.png"));
				break;
			case 63:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/bottom_right.png"));
				break;
			case 64:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/middle_left.png"));
				break;
			case 65:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/middle_middle.png"));
				break;
			case 66:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/middle_right.png"));
				break;
			case 67:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/top_left.png"));
				break;
			case 68:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/top_middle.png"));
				break;
			case 69:
				image =  new ImageIcon(Obstacle.class.getResource("/images/water/pond/top_right.png"));
				break;
				
			default:
				image =  new ImageIcon(Obstacle.class.getResource("/images/banana_tree.png"));
		}
			Image scaledImage = image.getImage(); // Wandelt Bild um
			scaledImage = scaledImage.getScaledInstance(scale, scale,  java.awt.Image.SCALE_FAST); //Bild wird skaliert 
			image = new ImageIcon(scaledImage);  // Wandelt Bild zurück in new ImageIcon um
		}
		
		/**
		 * Gibt die Hitbox des Obstacle Objektes
		 * zurueck
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

		public void setImage(ImageIcon ImageIcon) {
			this.image = ImageIcon;
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