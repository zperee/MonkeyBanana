package ch.monkeybanana.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class HomeView2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeView2 frame = new HomeView2();
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
	public HomeView2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		panel_2.setBounds(10, 276, 300, 144);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblServerListe = new JLabel("Server Liste");
		lblServerListe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblServerListe.setBounds(10, 11, 89, 14);
		panel_2.add(lblServerListe);
		
		JLabel lblServer = new JLabel("Server 01 0/2");
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblServer.setBounds(10, 41, 89, 14);
		panel_2.add(lblServer);
		
		JButton btnNewButton_1 = new JButton("Spielen");
		btnNewButton_1.setBounds(222, 38, 68, 23);
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
		btnNewButton.setBounds(320, 380, 87, 23);
		contentPane.add(btnNewButton);
	}
}
