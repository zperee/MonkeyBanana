package ch.monkeybanana.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

public class WaitingScreen extends JFrame {

	private JPanel contentPane;
	private Timer slotsTimer;
	private JLabel slotLabel;
	private User u;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User u = new User();
					WaitingScreen frame = new WaitingScreen(u);
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
	public WaitingScreen(User u) {
		slotsTimer = new Timer(100, freeSlots);
		slotsTimer.start();
		this.setU(u);
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
	            	Client.getInstance().getConnect().logoutServer(getU());
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
	            e.getWindow().dispose();
	        }
	    });
	}

	ActionListener freeSlots = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				slotLabel.setText(String.valueOf(Client.getInstance().getConnect().getSlots() + "/2 Slots besetzt"));
			}
			catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	};

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}
	

}
