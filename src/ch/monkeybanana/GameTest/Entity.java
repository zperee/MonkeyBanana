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
	private Player player = new Player(10, 10);
	private Obstacle kist1 = new Obstacle(50, 56);
	private Obstacle kist2 = new Obstacle(50, 120);
	private Obstacle kist3 = new Obstacle(50, 152);
	private Obstacle kist4 = new Obstacle(50, 184);
	private Obstacle kist5 = new Obstacle(50, 216);
	private Obstacle kist6 = new Obstacle(114, 120);
	private Obstacle kist7 = new Obstacle(82, 184);
	private Obstacle kist8 = new Obstacle(114, 184);
	private Obstacle kist9 = new Obstacle(148, 300);
	private Obstacle kist10 = new Obstacle(300, 300);
	private Obstacle kist11 = new Obstacle(300, 300);
	private Obstacle kist12 = new Obstacle(300, 300); 
	
	
	
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
		this.getKisten().add(this.getKist5());
		this.getKisten().add(this.getKist6());
		this.getKisten().add(this.getKist7());
		this.getKisten().add(this.getKist8());
		this.getKisten().add(this.getKist9());
		this.getKisten().add(this.getKist10());
		this.getKisten().add(this.getKist11());
		this.getKisten().add(this.getKist12());

	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN);

		for (Obstacle kist1 : this.getKisten()) {

			g2d.drawImage(kist1.getImage(), kist1.getX(), kist1.getY(), this);

			// Hitbox for crate
			g2d.drawRect(kist1.getX(), kist1.getY(),
					kist1.getImage().getWidth(null), kist1.getImage()
							.getHeight(null));

		}
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
		
		//Hitbox for player
		g2d.drawRect(player.getX(), player.getY() + 15,
				player.getImage().getWidth(null), 
				player.getImage().getHeight(null) - 15);
		
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
					player.setY((int) rect.getMaxY() - 15);
				} else if (recPlayer.getMaxX() <= rect.getMinX() + 3) {
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

	public Obstacle getKist5() {
		return kist5;
	}

	public void setKist5(Obstacle kist5) {
		this.kist5 = kist5;
	}

	public Obstacle getKist6() {
		return kist6;
	}

	public void setKist6(Obstacle kist6) {
		this.kist6 = kist6;
	}

	public Obstacle getKist7() {
		return kist7;
	}

	public void setKist7(Obstacle kist7) {
		this.kist7 = kist7;
	}

	public Obstacle getKist8() {
		return kist8;
	}

	public void setKist8(Obstacle kist8) {
		this.kist8 = kist8;
	}

	public Obstacle getKist9() {
		return kist9;
	}

	public void setKist9(Obstacle kist9) {
		this.kist9 = kist9;
	}

	public Obstacle getKist10() {
		return kist10;
	}

	public void setKist10(Obstacle kist10) {
		this.kist10 = kist10;
	}

	public Obstacle getKist11() {
		return kist11;
	}

	public void setKist11(Obstacle kist11) {
		this.kist11 = kist11;
	}

	public Obstacle getKist12() {
		return kist12;
	}

	public void setKist12(Obstacle kist12) {
		this.kist12 = kist12;
	}

}