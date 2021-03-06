package ch.monkeybanana.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.listener.HomeListener;
import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton spielenBtnSrv1;
	private JLabel onlineSpieler, slotLabelSrv1;
	private Timer slotsTimer, spielerTimer;
	private User u;

	/**
	 * Create the frame.
	 * @author Dominic Pfister, Elia Perenzin
	 */
	public HomeView(User u) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setBounds(100, 100, 445, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(HomeView.class.getResource("/images/banana.png")));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("MonkeyBanana - Menu");
		this.setU(u);
		
		ImageIcon banner = new ImageIcon(HomeView.class.getResource("/images/banner.gif"));
		Image scaledImage = banner.getImage(); // Wandelt Bild um
		scaledImage = scaledImage.getScaledInstance(414, 125,  java.awt.Image.SCALE_FAST); //Bild wird skaliert 
		banner = new ImageIcon(scaledImage);  // Wandelt Bild zurück in new ImageIcon um
		
		slotsTimer = new Timer(100, freeSlotListener);
		slotsTimer.start();
		
		this.setSpielerTime(new Timer(100, onlineListener));
		this.getSpielerTime().start();

		JPanel bannerPnl = new JPanel();
		bannerPnl.setBorder(null);
		bannerPnl.setBounds(10, 11, 414, 125);
		contentPane.add(bannerPnl);
		bannerPnl.setLayout(null);
		
		JLabel bannerLbl = new JLabel(banner);
		bannerLbl.setBounds(0, 0, 414, 125);
		bannerPnl.add(bannerLbl);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 147, 300, 126);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		double[] result = MBController.getInstance().getResult(u);
		
		JLabel lblSpielernameStatistik = new JLabel(u.getUsername() + ": Statistik");
		lblSpielernameStatistik.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSpielernameStatistik.setBounds(10, 11, 183, 14);
		panel_1.add(lblSpielernameStatistik);
		
		JLabel lblGespielteSpiele = new JLabel("Gespielte Spiele: " + (int) result[1]);
		lblGespielteSpiele.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGespielteSpiele.setBounds(10, 38, 129, 37);
		panel_1.add(lblGespielteSpiele);
		
		JLabel lblGewonneneSpiele = new JLabel("Gewonnene Spiele: " + (int) result[0]);
		lblGewonneneSpiele.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGewonneneSpiele.setBounds(149, 31, 141, 50);
		panel_1.add(lblGewonneneSpiele);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 101, 280, 14);
		
		double winLose = 0;
		if(!(result[0] == 0) || !(result[1] == 0)) {
			winLose = 100 / result[1] * result[0];
			Color bColor = new Color(0x1FE813);
			Color aColor = new Color(0xFF1624);
			progressBar.setBackground(aColor);
			progressBar.setForeground(bColor);
		}
		
		progressBar.setBorder(null);
		progressBar.setValue((int) winLose);
		panel_1.add(progressBar);
		
		JLabel lblGewonnenverloren = new JLabel("Gewonnen/Verloren: ");
		lblGewonnenverloren.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGewonnenverloren.setBounds(10, 73, 129, 25);
		panel_1.add(lblGewonnenverloren);
		
		JLabel lblNewLabel_1 = new JLabel(Math.round(winLose) + "%");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(149, 73, 35, 25);
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 284, 300, 149);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblServerListe = new JLabel("Server Liste");
		lblServerListe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblServerListe.setBounds(10, 11, 89, 14);
		panel_2.add(lblServerListe);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_4.setBounds(10, 36, 280, 41);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		spielenBtnSrv1 = new JButton("Spielen");
		spielenBtnSrv1.setBounds(181, 9, 89, 23);
		spielenBtnSrv1.setFocusable(false);
		spielenBtnSrv1.addActionListener(new HomeListener(u, "Spielen", this));
		panel_4.add(spielenBtnSrv1);
		
		JLabel lblServer = new JLabel("Server 01");
		lblServer.setBounds(10, 14, 110, 14);
		panel_4.add(lblServer);
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		slotLabelSrv1 = new JLabel("0/2");
		slotLabelSrv1.setBounds(130, 14, 46, 14);
		panel_4.add(slotLabelSrv1);
		slotLabelSrv1.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_5.setBounds(10, 88, 280, 41);
		panel_2.add(panel_5);
		
		JButton spielenBtnSrv2 = new JButton("Spielen");
		spielenBtnSrv2.setBounds(181, 9, 89, 23);
		spielenBtnSrv2.setEnabled(false);
		panel_5.add(spielenBtnSrv2);
		
		JLabel lblServer_1 = new JLabel("Server 02");
		lblServer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblServer_1.setBounds(10, 14, 110, 14);
		panel_5.add(lblServer_1);
		
		JLabel slotLabelSrv2 = new JLabel("0/0");
		slotLabelSrv2.setHorizontalAlignment(SwingConstants.RIGHT);
		slotLabelSrv2.setBounds(130, 14, 46, 14);
		panel_5.add(slotLabelSrv2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(320, 147, 103, 221);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblSpieler = new JLabel("Spieler");
		lblSpieler.setFont(new Font("Tahoma", Font.BOLD, 14));        
		lblSpieler.setBounds(10, 11, 83, 24);
		panel_3.add(lblSpieler);
		
		onlineSpieler = new JLabel("<html>");
		onlineSpieler.setVerticalAlignment(SwingConstants.TOP);
		onlineSpieler.setBounds(10, 46, 83, 164);
		panel_3.add(onlineSpieler);
	
		JButton verlassenBtn = new JButton("Abmelden");
		verlassenBtn.setBounds(320, 388, 104, 23);
		verlassenBtn.setFocusable(false);
		verlassenBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmationPane = JOptionPane.showConfirmDialog(null, "M\u00f6chten Sie sich wirklich abmelden?", "MonkeyBanana - Abmelden", JOptionPane.YES_NO_OPTION);
		        if (confirmationPane == JOptionPane.YES_OPTION) {
					try {
						Client.getInstance().getConnect().removeOnlinePlayer(u.getUsername());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					dispose();
					Client.start();
		        }
		        else {
		        }
			}
		});
		contentPane.add(verlassenBtn);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(172, 179, 200, 50);
		contentPane.add(lblNewLabel);
	}
	
	/**
	 * ActionListener fuer den SlotTimer. Wird alle 100ms aufgerufen
	 * und geprueft ob ein Slot auf dem Server frei ist.
	 * @author Dominic Pfister
	 */
	ActionListener freeSlotListener = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			try {
				if (Client.getInstance().getConnect().getSlots() < 2 && Client.getInstance().getConnect().isServerStatus()) {
					spielenBtnSrv1.setEnabled(true);
					slotLabelSrv1.setText(String.valueOf(Client.getInstance().getConnect().getSlots() + "/2"));
				} else if (Client.getInstance().getConnect().getSlots() >= 2 || !Client.getInstance().getConnect().isServerStatus()) {
					spielenBtnSrv1.setEnabled(false);
					slotLabelSrv1.setText(String.valueOf(Client.getInstance().getConnect().getSlots() + "/2"));
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * ActionListener für den spielerTimer. Wird alle 100ms aufgerufen
	 * und das JLabel mit den angemeldeten Spielern aktualisiert.
	 * @author Dominic Pfister
	 */
	ActionListener onlineListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			onlineSpieler.removeAll();
			onlineSpieler.setText("<html>");
			try {
				for (String spieler : Client.getInstance().getConnect().getOnlinePlayers()) {
					onlineSpieler.setText(onlineSpieler.getText() + spieler + "<br>");
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

	public Timer getSpielerTime() {
		return spielerTimer;
	}

	public void setSpielerTime(Timer spielerTime) {
		this.spielerTimer = spielerTime;
	}
}
