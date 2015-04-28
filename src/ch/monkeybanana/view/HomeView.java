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

import ch.monkeybanana.GameTest.GameClient;
import ch.monkeybanana.listener.GameListener;
import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

public class HomeView extends JFrame implements ActionListener{

	private JPanel contentPane;
	private User u;
	private JFrame waitframe;
	private Timer timer = new Timer(500, this);
	private boolean isModified = false;
	private int playerNr = 0;
	
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
		
		JButton btnSpielen = new JButton("Spielen");
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

	public void actionPerformed(ActionEvent e) {
		int slots = 0;
		JFrame waitFrame = new JFrame();
		JLabel waitSlots = new JLabel("0 / 2", SwingConstants.CENTER);
		timer.start();
		
		if (!isModified) {
			System.out.println(playerNr);
			
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
			GameClient gc = new GameClient(u, this.getPlayerNr());
			gc.getEnt().addKeyListener((KeyListener) (new GameListener(gc.getEnt().getPlayerArray().get(this.getPlayerNr()))));
			waitSlots.setVisible(false);
			getWaitframe().dispose();
			timer.stop();
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
}
