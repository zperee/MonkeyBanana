package ch.monkeybanana.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import ch.monkeybanana.game.Banana;
import ch.monkeybanana.game.GameWindow;
import ch.monkeybanana.listener.GameListener;
import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

public class WaitingScreen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Timer slotsTimer;
	private JLabel slotLabel;
	private User u;
	private GameWindow gw;
	private boolean isModified = false;
	private int playerNr = 0;

	/**
	 * Create the frame.
	 */
	public WaitingScreen(User u) {
		slotsTimer = new Timer(100, this);
		slotsTimer.start();
		this.setU(u);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 248, 157);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWaiting = new JLabel("Waiting...");
		lblWaiting.setForeground(Color.BLACK);
		lblWaiting.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWaiting.setBounds(80, 11, 72, 50);
		contentPane.add(lblWaiting);

		try {
			slotLabel = new JLabel(Client.getInstance().getConnect().getSlots() + "/2 Slots besetzt");
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		slotLabel.setForeground(Color.GRAY);
		slotLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		slotLabel.setBounds(59, 58, 107, 50);
		contentPane.add(slotLabel);
		
		addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	            try {
	            	Client.getInstance().getConnect().logoutSpiel(getU());
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
	            e.getWindow().dispose();
	        }
	    });
	}

	public void actionPerformed(ActionEvent e) {
		try {
			slotLabel.setText(String.valueOf(Client.getInstance().getConnect()
					.getSlots()
					+ "/2 Slots besetzt"));
		}
		catch (RemoteException e1) {
			e1.printStackTrace();
		}
		try {
			if (!isModified) {
				if (Client.getInstance().getConnect().getSlots() == 1) {
					this.setPlayerNr(0);
				}
				else {
					this.setPlayerNr(1);
				} 
				setModified(true);
			}

			if (Client.getInstance().getConnect().getSlots() == 2) {
				gw = new GameWindow(this.getU(), this.getPlayerNr());
				gw.getEnt().addKeyListener((KeyListener) new GameListener(gw.getEnt().getPlayerArray().get(this.getPlayerNr())));

				this.dispose();
				slotsTimer.stop();

				new GameProzess().start();

				Client.getInstance().getConnect().setFinishedGame(false);

			}
		}
		catch (RemoteException e1) {
			e1.printStackTrace();
		}

	}
		
		public class GameProzess extends Thread {
			public void run() {
				try {
					while (!Client.getInstance().getConnect().getFinishedGame()) {
						doPlayer();
						doBanana();
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	
	public void doPlayer() {
		try {
			Client.getInstance().getConnect().tellPosition(gw.getEnt().getPlayerArray().get(this.getPlayerNr()).getX(),
			gw.getEnt().getPlayerArray().get(this.getPlayerNr()).getY(), this.getPlayerNr());

		if (this.getPlayerNr() == 0) {
			gw.getEnt().getPlayerArray().get(1).setX(Client.getInstance().getConnect().getPosition('x', 0));
			gw.getEnt().getPlayerArray().get(1).setY(Client.getInstance().getConnect().getPosition('y', 0));
		} else if  (this.getPlayerNr() == 1) {
			gw.getEnt().getPlayerArray().get(0).setX(Client.getInstance().getConnect().getPosition('x', 1));
			gw.getEnt().getPlayerArray().get(0).setY(Client.getInstance().getConnect().getPosition('y', 1));
		}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void doBanana() {
		try {
			Banana b = Client.getInstance().getConnect().getBanana(playerNr);
			if (b != null) {
				gw.getEnt().getBananenArray().add(b);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public boolean isModified() {
		return isModified;
	}

	public int getPlayerNr() {
		return playerNr;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	public void setPlayerNr(int playerNr) {
		this.playerNr = playerNr;
	}
	

}
