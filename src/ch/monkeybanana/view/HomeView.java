package ch.monkeybanana.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import ch.monkeybanana.game.Banana;
import ch.monkeybanana.game.GameWindow;
import ch.monkeybanana.listener.GameListener;
import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

public class HomeView extends JFrame implements ActionListener{
	private static final long serialVersionUID = 3784778276460922250L;
	private JPanel contentPane;
	private User u;
	private JFrame waitframe;
	JButton btnSpielen = new JButton("Spielen");
	
	private Timer timer = new Timer(500, this);
	private Timer slotTimer;
	
	private boolean isModified = false;
	private int playerNr = 0;
	private boolean isStarted = false;
	private GameWindow gc;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeView frame = new HomeView(new User());
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeView(User u) {
		slotTimer = new Timer(100, freeSlots);
		slotTimer.start();
		
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(470, 278, 149, 281);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Online");
		lblNewLabel_1.setIcon(new ImageIcon(HomeView.class.getResource("/images/online.png")));
		lblNewLabel_1.setBounds(10, 11, 93, 25);
		panel_1.add(lblNewLabel_1);
		
		btnSpielen.setBounds(50, 247, 89, 23);
		btnSpielen.addActionListener(this);
		panel_1.add(btnSpielen);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 278, 450, 163);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(10, 11, 97, 29);
		panel_3.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Spiele gespielt");
		lblNewLabel_3.setBounds(20, 51, 87, 14);
		panel_3.add(lblNewLabel_3);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(530, 570, 89, 23);
		contentPane.add(btnLogout);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 452, 450, 159);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(HomeView.class.getResource("/images/Banner.png")));
		lblNewLabel.setBounds(10, 11, 609, 256);
		contentPane.add(lblNewLabel);
		this.setU(u);
		
		addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            try {
	            	Client.getInstance().getConnect().logoutServer(getU());
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
	            e.getWindow().dispose();
	        }
	    });
	}

	/**
	 * ActionListener fuer den SlotTimer. Wird alle 100ms aufgerufen
	 * und geprueft ob ein Slot auf dem Server frei ist.
	 * @author Dominic Pfister
	 */
	ActionListener freeSlots = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			try {
				if (Client.getInstance().getConnect().getSlots() < 2) {
					btnSpielen.setEnabled(true);
				} else if (Client.getInstance().getConnect().getSlots() >= 2) {
					btnSpielen.setEnabled(false);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	
	public void actionPerformed(ActionEvent e) {
		int slots = 0;
		JFrame waitFrame = new JFrame();
		JLabel waitSlots = new JLabel("0 / 2", SwingConstants.CENTER);
		timer.start();
		
		waitFrame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            try {
	            	Client.getInstance().getConnect().logoutServer(getU());
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
	            e.getWindow().dispose();
	        }
	    });
		
		if (!isModified) {
			try {
				Client.getInstance().getConnect().join(this.getU());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			try {
				if (Client.getInstance().getConnect().getSlots() == 1) {
					this.setPlayerNr(0);
				} else {
					this.setPlayerNr(1);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			setVisible(false); //Lässt das HomeView verschwinden
			this.setWaitframe(waitFrame);
			waitFrame.setResizable(false);
			waitFrame.setVisible(true);
			waitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			waitFrame.setSize(200, 150);
			this.setModified(true);
		}
		
		try {
			 slots = Client.getInstance().getConnect().getSlots();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		waitSlots.setText("<html>" + slots + " / 2 Spieler <br> Waiting...</html>");
		waitFrame.add(waitSlots);
		
		if (slots == 2) {
			GameWindow gc = new GameWindow(this.getU(), this.getPlayerNr());
			gc.getEnt().addKeyListener((KeyListener) (new GameListener(gc.getEnt().getPlayerArray().get(this.getPlayerNr()))));
			waitSlots.setVisible(false);
			getWaitframe().dispose();
			timer.stop();
			this.setGc(gc);
			(new GameProzess()).start();
			this.setStarted(true);
			
			try {
				Client.getInstance().getConnect().setFinishedGame(false);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
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
			Client.getInstance().getConnect().tellPosition(gc.getEnt().getPlayerArray().get(this.getPlayerNr()).getX(),
			gc.getEnt().getPlayerArray().get(this.getPlayerNr()).getY(), this.getPlayerNr());

		if (this.getPlayerNr() == 0) {
			gc.getEnt().getPlayerArray().get(1).setX(Client.getInstance().getConnect().getPosition('x', 0));
			gc.getEnt().getPlayerArray().get(1).setY(Client.getInstance().getConnect().getPosition('y', 0));
		} else if  (this.getPlayerNr() == 1) {
			gc.getEnt().getPlayerArray().get(0).setX(Client.getInstance().getConnect().getPosition('x', 1));
			gc.getEnt().getPlayerArray().get(0).setY(Client.getInstance().getConnect().getPosition('y', 1));
		}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void doBanana() {
		try {
			Banana b = Client.getInstance().getConnect().getBanana(playerNr);
			if (b != null) {
				gc.getEnt().getBananenArray().add(b);
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

	public JFrame getWaitframe() {
		return waitframe;
	}

	public void setWaitframe(JFrame waitframe) {
		this.waitframe = waitframe;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	public int getPlayerNr() {
		return playerNr;
	}

	public void setPlayerNr(int playerNr) {
		this.playerNr = playerNr;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public GameWindow getGc() {
		return gc;
	}

	public void setGc(GameWindow gc) {
		this.gc = gc;
	}

	public JButton getBtnSpielen() {
		return btnSpielen;
	}

	public void setBtnSpielen(JButton btnSpielen) {
		this.btnSpielen = btnSpielen;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Timer getSlotTimer() {
		return slotTimer;
	}

	public void setSlotTimer(Timer slotTimer) {
		this.slotTimer = slotTimer;
	}
}
