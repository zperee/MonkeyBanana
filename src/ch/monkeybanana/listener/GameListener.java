package ch.monkeybanana.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ch.monkeybanana.game.Player;

public class GameListener implements KeyListener{

	private static boolean up, down, left, right, isBananaPeel, isBananaThrown;
	private static boolean allowBanana = true;
	private Player player;
	/* **LISTENER** */
	public GameListener(Player p){
		this.setPlayer(p);
	}
	
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			this.getPlayer().setvX(-Player.SPEED);
			left = true;
			right = false;
			up = false;
			down = false;
		}

		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			this.getPlayer().setvX(Player.SPEED);
			left = false;
			right = true;
			up = false;
			down = false;
		}

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			this.getPlayer().setvY(-Player.SPEED);
			left = false;
			right = false;
			up = true;
			down = false;
		}

		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			this.getPlayer().setvY(Player.SPEED);
			left = false;
			right = false;
			up = false;
			down = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			player.setvX(0);
		}

		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			player.setvX(0);
		}

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			player.setvY(0);
		}

		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			player.setvY(0);
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
	

	public static boolean isUp() {
		return up;
	}

	public static void setUp(boolean up) {
		GameListener.up = up;
	}

	public static boolean isDown() {
		return down;
	}

	public static void setDown(boolean down) {
		GameListener.down = down;
	}

	public static boolean isLeft() {
		return left;
	}

	public static void setLeft(boolean left) {
		GameListener.left = left;
	}

	public static boolean isRight() {
		return right;
	}

	public static void setRight(boolean right) {
		GameListener.right = right;
	}

	public static boolean isBananaPeel() {
		return isBananaPeel;
	}

	public static void setBananaPeel(boolean isBananaPeel) {
		GameListener.isBananaPeel = isBananaPeel;
	}

	public static boolean isBananaThrown() {
		return isBananaThrown;
	}

	public static void setBananaThrown(boolean isBananaThrown) {
		GameListener.isBananaThrown = isBananaThrown;
	}

	public static boolean isAllowBanana() {
		return allowBanana;
	}

	public static void setAllowBanana(boolean allowBanana) {
		GameListener.allowBanana = allowBanana;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


}
