package ch.monkeybanana.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import ch.monkeybanana.listener.HomeListener;
import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

public class HomeView extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton_1;
	private JLabel lblServer;
	private Timer slotsTimer;
	private User u;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User u = new User();
					HomeView frame = new HomeView(u);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setBounds(100, 100, 445, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setU(u);
		
		slotsTimer = new Timer(100, freeSlotsButton);
		slotsTimer.start();

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 125);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 147, 300, 118);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSpielernameStatistik = new JLabel("Spielername Statistik");
		lblSpielernameStatistik.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSpielernameStatistik.setBounds(10, 11, 183, 14);
		panel_1.add(lblSpielernameStatistik);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 276, 300, 144);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblServerListe = new JLabel("Server Liste");
		lblServerListe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblServerListe.setBounds(10, 11, 89, 14);
		panel_2.add(lblServerListe);
		
		lblServer = new JLabel("Server 01 0/2");
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblServer.setBounds(10, 41, 110, 14);
		panel_2.add(lblServer);
		
		btnNewButton_1 = new JButton("Spielen");
		btnNewButton_1.setBounds(201, 38, 89, 23);
		btnNewButton_1.addActionListener(new HomeListener(u, "Spielen", this));
		panel_2.add(btnNewButton_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(320, 147, 103, 221);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblSpieler = new JLabel("Spieler");
		lblSpieler.setFont(new Font("Tahoma", Font.BOLD, 14));        
		lblSpieler.setBounds(10, 11, 46, 14);
		panel_3.add(lblSpieler);
		
		JButton btnNewButton = new JButton("Verlassen");
		btnNewButton.setBounds(320, 380, 104, 23);
		btnNewButton.addActionListener(new HomeListener(this.getU(), "Verlassen", this));
		contentPane.add(btnNewButton);
		
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
	
	/**
	 * ActionListener fuer den SlotTimer. Wird alle 100ms aufgerufen
	 * und geprueft ob ein Slot auf dem Server frei ist.
	 * @author Dominic Pfister
	 */
	ActionListener freeSlotsButton = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			try {
				if (Client.getInstance().getConnect().getSlots() < 2) {
					btnNewButton_1.setEnabled(true);
					lblServer.setText(String.valueOf(Client.getInstance().getConnect().getSlots() + "/2 Slots besetzt"));
				} else if (Client.getInstance().getConnect().getSlots() >= 2) {
					btnNewButton_1.setEnabled(false);
					lblServer.setText(String.valueOf(Client.getInstance().getConnect().getSlots() + "/2 Slots besetzt"));
				}
			} catch (RemoteException e1) {
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
