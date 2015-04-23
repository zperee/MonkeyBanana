package ch.monkeybanana.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import ch.monkeybanana.GameTest.GameClient;
import ch.monkeybanana.rmi.Client;
import ch.monkeybanana.rmi.ValidatorImpl;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame implements ActionListener{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeView frame = new HomeView();
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
	public HomeView() {
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
		
	}

	public void actionPerformed(ActionEvent e) {
		new GameClient();
		
	}
}
