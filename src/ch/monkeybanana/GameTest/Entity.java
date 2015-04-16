package ch.monkeybanana.GameTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Klasse für alle Entities
 * 
 * @author Dominic Pfister, Elia Perenzin Entity.java Copyright
 *         Berufsbildungscenter MonkeyBanana 2015
 */

public class Entity extends JPanel implements ActionListener {

	private Timer timer;
	private Player player = new Player();
	private Obstacle kist1 = new Obstacle(100, 100);
	private Obstacle kist2 = new Obstacle(300, 100);
	private Obstacle kist3 = new Obstacle(100, 300);
	private Obstacle kist4 = new Obstacle(300, 300);

	private List<Obstacle> kisten = new ArrayList<Obstacle>();
	private boolean isModified = false;

	public Entity() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.RED);
		setDoubleBuffered(true);

		timer = new Timer(10, this);
		timer.start();
		checkBounds();
		this.getKisten().add(this.getKist1());
		this.getKisten().add(this.getKist2());
		this.getKisten().add(this.getKist3());
		this.getKisten().add(this.getKist4());

	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN);

		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
		g2d.drawRect(player.getX(), player.getY(),
				player.getImage().getWidth(null),
				player.getImage().getHeight(null));

		for (Obstacle kist1 : this.getKisten()) {

			g2d.drawImage(kist1.getImage(), kist1.getX(), kist1.getY(), this);

			// Hitboxes

			g2d.drawRect(kist1.getX(), kist1.getY(),
					kist1.getImage().getWidth(null), kist1.getImage()
							.getHeight(null));

		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();

	}

	public void actionPerformed(ActionEvent e) {
		player.move();
		repaint();
		checkBounds();
	}

	public void checkBounds() {
		Rectangle recPlayer = player.playerBounds();

		for (Obstacle k : this.getKisten()) {
			Rectangle rect = k.obstBounds();

			if (recPlayer.intersects(rect) && !isModified) {

				if (recPlayer.getMaxY() <= rect.getMaxY()
						&& !(recPlayer.getMinY() >= (rect.getMaxY() - 4))
						&& !(recPlayer.getMaxX() <= rect.getMinX() + 4)
						&& !(recPlayer.getMinX() >= rect.getMaxX() - 4)) {

					player.setY((int) rect.getMinY() - player.getImage().getHeight(null));
				} else if (recPlayer.getMinY() >= (rect.getMaxY() - 4)) {
					player.setY((int) rect.getMaxY());
				} else if (recPlayer.getMaxX() <= rect.getMinX() + 4) {
					player.setX((int) rect.getMinX() - player.getImage().getWidth(null));
				} else if (recPlayer.getMinX() >= rect.getMaxX() - 4) {
					player.setX((int) rect.getMaxX());
				}
			}
		}

	}

	public Obstacle getKist1() {
		return kist1;
	}

	public void setKist1(Obstacle kist1) {
		this.kist1 = kist1;
	}

	public Obstacle getKist2() {
		return kist2;
	}

	public void setKist2(Obstacle kist2) {
		this.kist2 = kist2;
	}

	public List<Obstacle> getKisten() {
		return kisten;
	}

	public void setKisten(List<Obstacle> kisten) {
		this.kisten = kisten;
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
	}

	public Obstacle getKist3() {
		return kist3;
	}

	public void setKist3(Obstacle kist3) {
		this.kist3 = kist3;
	}

	public Obstacle getKist4() {
		return kist4;
	}

	public void setKist4(Obstacle kist4) {
		this.kist4 = kist4;
	}

}