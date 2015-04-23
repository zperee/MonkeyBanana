package ch.monkeybanana.GameTest;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * Klasse um den Spieler zu erstellen (Bild)
 * 
 * @author Dominic Pfister, Elia Perenzin Player.java Copyright
 *         Berufsbildungscenter MonkeyBanana 2015
 */

public class Player {
	
	public static final int SPEED = 2;
	private int vX, vY;
	private boolean up, down, left, right, isBananaPeel, isBananaThrown;
	private boolean allowBanana = true;
	private int x, y;
	private Image image;
	private int totalBanana;
	private int coolDown;
	

	public int setY;

	/**
	 * Konstruktor der Klasse Player. Ein neuer Spieler wird an
	 * der x und y Positionen erstellt.
	 * 
	 * @author Dominic Pfister
	 * @param x {@link int}
	 * @param y {@link int}
	 * @param totalBanana {@link int}
	 */
	public Player(int x, int y, int totalBanana, int cooldown, int scale) {
		image =  new ImageIcon("images/monkeyBlue.png").getImage();
		image = image.getScaledInstance(scale, scale + scale / 2, java.awt.Image.SCALE_SMOOTH);
		this.setY(y + 15);
		this.setX(x);
		this.setTotalBanana(totalBanana);
		this.setCoolDown(cooldown);
	}

	/**
	 * This is the only method that the client should ever call! Instead of
	 * moving a sprite when you press a button, you should be moving a sprite
	 * while certain states hold. Also, do not animate the sprite in the event
	 * handler! Animate it in your main rendering loop!! That means that your
	 * main rendering loop will call player.go() and nothing more. This will
	 * result in smooth animation. If you rely on animating the sprite in the
	 * keypressed method, the sprite will only move when an event is fired.
	 * That's ugly!!
	 */
	public void move() {
		x += vX;
		y += vY;
	}
	
	/**
	 * Gibt die Hitbox des Spielers zurück
	 * 
	 * @author Dominic Pfister
	 */
	public Rectangle playerBounds() {
		int height = this.getImage().getHeight(null) - this.getImage().getWidth(null) / 2;
		int playerY = this.getY() + this.getImage().getWidth(null) / 2;
		return new Rectangle(this.getX(), playerY, image.getWidth(null), height);
	}


	/* **LISTENER** */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            vX = -SPEED;
            left = true;
            right = false;
            up = false;
            down = false;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            vX = SPEED;
            left = false;
            right = true;
            up = false;
            down = false;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            vY = -SPEED;
            left = false;
            right = false;
            up = true;
            down = false;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            vY = SPEED;
            left = false;
            right = false;
            up = false;
            down = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            vX = 0;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            vX = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            vY = 0;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            vY = 0;
        }
        if (key == KeyEvent.VK_E || key == KeyEvent.VK_R) {
        	allowBanana = true;
        }
    }
    
    public void keyTyped(KeyEvent e) {
    	
    	char key2 = e.getKeyChar();
    	
    	if (key2 == 'e' && allowBanana) {
    		isBananaPeel = true;
    		allowBanana = false;
    		
    	} else if (key2 == 'r' && allowBanana) {
    		isBananaThrown = true;
    		allowBanana = false;
    		
    		
    	}
    }

    /* **ALTER LISTENER** */
//	public void keyPressed(KeyEvent e) {
//		switch (e.getKeyCode()) {
//		case KeyEvent.VK_DOWN:
//			down = true;
//			break;
//		case KeyEvent.VK_UP:
//			up = true;
//			break;
//		case KeyEvent.VK_LEFT:
//			left = true;
//			break;
//		case KeyEvent.VK_RIGHT:
//			right = true;
//			break;
//		}
//		update();
//	}
//
//	public void keyReleased(KeyEvent e) {
//		switch (e.getKeyCode()) {
//		case KeyEvent.VK_DOWN:
//			down = false;
//			break;
//		case KeyEvent.VK_UP:
//			up = false;
//			break;
//		case KeyEvent.VK_LEFT:
//			left = false;
//			break;
//		case KeyEvent.VK_RIGHT:
//			right = false;
//			break;
//		}
//		update();
//	}
    
//	private void update() {
//		vX = 0;
//		vY = 0;
//
//		if (down) {
//			vY = SPEED;
//		}
//		if (right) {
//			vX = SPEED;
//		}
//		if (left) {
//			vX = -SPEED;
//		}
//		if (up) {
//			vY = -SPEED;
//		}
//	}

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

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isBananaPeel() {
		return isBananaPeel;
	}

	public void setBananaPeel(boolean isBananaPeel) {
		this.isBananaPeel = isBananaPeel;
	}

	public int getSetY() {
		return setY;
	}

	public void setSetY(int setY) {
		this.setY = setY;
	}

	public static int getSpeed() {
		return SPEED;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isBananaThrown() {
		return isBananaThrown;
	}

	public void setBananaThrown(boolean isBananaThrown) {
		this.isBananaThrown = isBananaThrown;
	}

	public int getTotalBanana() {
		return totalBanana;
	}

	public void setTotalBanana(int totalBanana) {
		this.totalBanana = totalBanana;
	}

	public int getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
	}

	public boolean isAllowBanana() {
		return allowBanana;
	}

	public void setAllowBanana(boolean allowBanana) {
		this.allowBanana = allowBanana;
	}
}