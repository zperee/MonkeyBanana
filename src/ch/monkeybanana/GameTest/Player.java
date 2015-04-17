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
//	private boolean up, down, left, right;
	private int x, y;
	private Image image;

	public int setY;

	public Player(int x, int y) {
		image =  new ImageIcon("images/pokemon.png").getImage();
		this.setY(y + 15);
		this.setX(x);
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
		System.out.println(y);
	}
	
	public Rectangle playerBounds() {
		int height = this.getImage().getHeight(null) - 15;
		int playerY = this.getY() + 15;
		return new Rectangle(this.getX(), playerY, image.getWidth(null), height);
	}


	/* **LISTENER** */
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            vX = -SPEED;
        }

        if (key == KeyEvent.VK_RIGHT) {
            vX = SPEED;
        }

        if (key == KeyEvent.VK_UP) {
            vY = -SPEED;
        }

        if (key == KeyEvent.VK_DOWN) {
            vY = SPEED;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            vX = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            vX = 0;
        }

        if (key == KeyEvent.VK_UP) {
            vY = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            vY = 0;
        }
    }

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
}