package ch.monkeybanana.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

public class HomeView extends JFrame implements ActionListener{

	private JPanel contentPane;
	private User u;
	private JFrame waitframe;

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

	public class Process implements ActionListener {
		boolean started = false;
		Timer timer = new Timer(2000, this);
		public Process() {
		}
		
		public void run() {
			while (true && !started) {
				
				getWaitframe().removeAll();
				int slots = 0;
				JLabel waitSlots = new JLabel("0 / 2", SwingConstants.CENTER);
				try {
					 slots = Client.getInstance().getConnect().getSlots();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				waitSlots.setText("<html>" + slots + " / 2 Spieler <br> Waiting...</html>");
				System.out.println("called");
				getWaitframe().add(waitSlots);
				repaint();
				
				if (slots == 2) {
					new GameClient(u);
					this.setStarted(true);
				}
			}
			
		}

		public boolean isStarted() {
			return started;
		}

		public void setStarted(boolean started) {
			this.started = started;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			setWaitframe(getWaitframe());
			getWaitframe().setResizable(false);
			getWaitframe().setVisible(true);
			getWaitframe().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getWaitframe().setSize(200, 150);
			
			JLabel waitSlots = new JLabel("0 / 2", SwingConstants.CENTER);
			int slots = 0;
			try {
				 slots = Client.getInstance().getConnect().getSlots();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			waitSlots.setText("<html>" + slots + " / 2 Spieler <br> Waiting...</html>");
			getWaitframe().add(waitSlots);
		}
	}
	public void actionPerformed(ActionEvent e) {
		
		try {
			Client.getInstance().getConnect().join(this.getU());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		
		setVisible(false); //Lässt das HomeView verschwinden
		JFrame waitFrame = new JFrame();
		this.setWaitframe(waitFrame);
		waitFrame.setResizable(false);
		waitFrame.setVisible(true);
		waitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		waitFrame.setSize(200, 150);
		
		JLabel waitSlots = new JLabel("0 / 2", SwingConstants.CENTER);
		int slots = 0;
		try {
			 slots = Client.getInstance().getConnect().getSlots();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		waitSlots.setText("<html>" + slots + " / 2 Spieler <br> Waiting...</html>");
		waitFrame.add(waitSlots);
		
		Process p = new Process();
		p.run();
		

		
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
}
