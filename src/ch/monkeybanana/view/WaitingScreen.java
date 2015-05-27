package ch.monkeybanana.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ch.monkeybanana.game.Banana;
import ch.monkeybanana.game.GameWindow;
import ch.monkeybanana.listener.GameListener;
import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

public class WaitingScreen extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Timer slotsTimer;
	private JLabel slotLabel, statusLabel, onlinePlayers;
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
		setBounds(100, 100, 260, 307);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\banana.png"));
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		statusLabel = new JLabel("Waiting...");
		statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statusLabel.setForeground(Color.BLACK);
		statusLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		statusLabel.setBounds(10, 11, 126, 75);
		contentPane.add(statusLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(10, 97, 126, 139);
		contentPane.add(panel);
		panel.setLayout(null);
		try {
			slotLabel = new JLabel(Client.getInstance().getConnect().getSlots() + "/2 Slots besetzt");
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		slotLabel.setBounds(10, 11, 106, 117);
		panel.add(slotLabel);
		slotLabel.setForeground(Color.GRAY);
		slotLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JButton zurueck = new JButton("Zur\u00FCck");
		zurueck.setBounds(156, 247, 89, 23);
		zurueck.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	slotsTimer.stop();
	        	dispose();
	        	try {
					Client.getInstance().getConnect().logoutSpiel();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
	        	new HomeView(u);
	        }
	    });
		contentPane.add(zurueck);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(146, 11, 98, 225);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Player");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 11, 78, 20);
		panel_1.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(10, 30, 78, 2);
		panel_1.add(separator);
		
		onlinePlayers = new JLabel("");
		onlinePlayers.setHorizontalAlignment(SwingConstants.LEFT);
		onlinePlayers.setVerticalAlignment(SwingConstants.TOP);
		onlinePlayers.setBounds(10, 42, 78, 172);
		panel_1.add(onlinePlayers);
		
		addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	            try {
	            	Client.getInstance().getConnect().logoutSpiel();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
	            e.getWindow().dispose();
	        }
	    });
	}

	public void actionPerformed(ActionEvent e) {
		checkServerStatus();
		try {
			slotLabel.setText(String.valueOf(Client.getInstance().getConnect().getSlots() + "/2 Slots besetzt"));
		}
		catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		onlinePlayers.removeAll();
		onlinePlayers.setText("<html>");
		try {
			for (String spieler : Client.getInstance().getConnect().getOnlinePlayers()) {
				onlinePlayers.setText(onlinePlayers.getText() + spieler + "<br>");
			}
		} catch (RemoteException e1) {
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
	
	/**
	 * Prueft ob der Server heruntergefahren wurde.
	 * @author Dominic Pfister, Elia Perenzin
	 */
	public void checkServerStatus() {
		try {
			if (!Client.getInstance().getConnect().isServerStatus()) {
				slotsTimer.stop();
				this.dispose();
				JOptionPane.showMessageDialog(null, "Der Server wurde heruntergefahren.", "MonkeyBanana - Server Restarted", JOptionPane.INFORMATION_MESSAGE);
				new HomeView(u);
			}
		} catch (HeadlessException | RemoteException e) {
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
