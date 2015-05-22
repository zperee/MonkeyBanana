package ch.monkeybanana.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ch.monkeybanana.rmi.Client;

/**
 * @author Dominic Pfister
 */
public class ServerView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField setSlots1;
	private JTextField setSlots2;
	private JLabel srv1_pl1, srv1_pl2, srv2_pl1, srv2_pl2;
	private JLabel slotLabelSrv1, slotLabelSrv2;
	private JLabel onlinePlayers;
	
	private Timer timer;

	/**
	 * Erstellt ein neues JFrame mit allen Informationen des Servers.
	 * @author Dominic Pfister
	 */
	public ServerView() {
		this.getTimer().start();
		
		setTitle("MonkeyBanana - Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 13, 403, 412);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel playersPanel = new JPanel();
		playersPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, Color.LIGHT_GRAY));
		playersPanel.setBounds(266, 13, 125, 386);
		panel.add(playersPanel);
		playersPanel.setLayout(null);
		
		JLabel spielerTitle = new JLabel("Spieler");
		spielerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		spielerTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		spielerTitle.setBounds(12, 7, 101, 25);
		playersPanel.add(spielerTitle);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 30, 101, 2);
		playersPanel.add(separator);
		
		onlinePlayers = new JLabel("");
		onlinePlayers.setVerticalAlignment(SwingConstants.TOP);
		onlinePlayers.setHorizontalAlignment(SwingConstants.LEFT);
		onlinePlayers.setBounds(12, 51, 101, 322);
		playersPanel.add(onlinePlayers);
		
		JPanel serversPanel = new JPanel();
		serversPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, Color.LIGHT_GRAY));
		serversPanel.setBounds(12, 13, 243, 386);
		panel.add(serversPanel);
		serversPanel.setLayout(null);
		
		JPanel server1Panel = new JPanel();
		server1Panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		server1Panel.setBounds(12, 45, 218, 158);
		serversPanel.add(server1Panel);
		server1Panel.setLayout(null);
		
		JLabel lblServer1 = new JLabel("Server 01");
		lblServer1.setBounds(12, 13, 102, 16);
		server1Panel.add(lblServer1);
		
		slotLabelSrv1 = new JLabel("0/2");
		slotLabelSrv1.setFont(new Font("Tahoma", Font.BOLD, 13));
		slotLabelSrv1.setBounds(137, 13, 25, 16);
		server1Panel.add(slotLabelSrv1);
		
		JPanel spielerPanelSrv1 = new JPanel();
		spielerPanelSrv1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		spielerPanelSrv1.setBounds(12, 42, 150, 69);
		server1Panel.add(spielerPanelSrv1);
		spielerPanelSrv1.setLayout(null);
		
		srv1_pl1 = new JLabel("-");
		srv1_pl1.setBounds(12, 13, 126, 16);
		spielerPanelSrv1.add(srv1_pl1);
		
		srv1_pl2 = new JLabel("-");
		srv1_pl2.setBounds(12, 40, 126, 16);
		spielerPanelSrv1.add(srv1_pl2);
		
		JButton restartSrv1 = new JButton("");
		restartSrv1.setIcon(null);
		restartSrv1.setBounds(174, 42, 32, 32);
		server1Panel.add(restartSrv1);
		
		JButton shutdownSrv1 = new JButton("");
		shutdownSrv1.setBounds(174, 113, 32, 32);
		server1Panel.add(shutdownSrv1);
		
		setSlots1 = new JTextField();
		setSlots1.setBounds(87, 123, 54, 22);
		server1Panel.add(setSlots1);
		setSlots1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Set Slots:");
		lblNewLabel_2.setBounds(12, 124, 66, 21);
		server1Panel.add(lblNewLabel_2);
		
		JButton startSrv1 = new JButton("");
		startSrv1.setBounds(174, 77, 32, 32);
		server1Panel.add(startSrv1);
		
		JLabel lblServerListe = new JLabel("Server Liste");
		lblServerListe.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerListe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblServerListe.setBounds(12, 7, 218, 25);
		serversPanel.add(lblServerListe);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(40, 30, 162, 2);
		serversPanel.add(separator_1);
		
		JPanel server2Panel = new JPanel();
		server2Panel.setLayout(null);
		server2Panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		server2Panel.setBounds(12, 216, 218, 158);
		serversPanel.add(server2Panel);
		
		JLabel lblServer2 = new JLabel("Server 02");
		lblServer2.setBounds(12, 13, 102, 16);
		server2Panel.add(lblServer2);
		
		slotLabelSrv2 = new JLabel("0/0");
		slotLabelSrv2.setFont(new Font("Tahoma", Font.BOLD, 13));
		slotLabelSrv2.setBounds(137, 13, 25, 16);
		server2Panel.add(slotLabelSrv2);
		
		JPanel spielerPanelSrv2 = new JPanel();
		spielerPanelSrv2.setLayout(null);
		spielerPanelSrv2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		spielerPanelSrv2.setBounds(12, 42, 150, 69);
		server2Panel.add(spielerPanelSrv2);
		
		srv2_pl1 = new JLabel("-");
		srv2_pl1.setBounds(12, 13, 126, 16);
		spielerPanelSrv2.add(srv2_pl1);
		
		srv2_pl2 = new JLabel("-");
		srv2_pl2.setBounds(12, 40, 126, 16);
		spielerPanelSrv2.add(srv2_pl2);
		
		JButton restartSrv2 = new JButton("");
		restartSrv2.setBounds(174, 42, 32, 32);
		server2Panel.add(restartSrv2);
		
		JButton startSrv2 = new JButton("");
		startSrv2.setBounds(174, 79, 32, 32);
		server2Panel.add(startSrv2);
		
		setSlots2 = new JTextField();
		setSlots2.setColumns(10);
		setSlots2.setBounds(87, 123, 54, 22);
		server2Panel.add(setSlots2);
		
		JLabel label_6 = new JLabel("Set Slots:");
		label_6.setBounds(12, 124, 66, 21);
		server2Panel.add(label_6);
		
		JButton shutdownSrv2 = new JButton("");
		shutdownSrv2.setBounds(174, 113, 32, 32);
		server2Panel.add(shutdownSrv2);
		
		setVisible(true);
		setEnabled(true);
		setResizable(false);
	}
	
	/**
	 * und aktualisiert die Komponenten.
	 * @author Dominic Pfister
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			slotLabelSrv1.setText(Client.getInstance().getConnect().getSlots() + "/2");
			
			onlinePlayers.removeAll();
			onlinePlayers.setText("<html>");
			for (String spieler : Client.getInstance().getConnect().getOnlinePlayers()) {
				onlinePlayers.setText(onlinePlayers.getText() + spieler + "<br>");
			}
		
			if (!(Client.getInstance().getConnect().getPlayer(0).equals("null")
					||Client.getInstance().getConnect().getPlayer(1).equals("null"))) {
				srv1_pl1.setText(Client.getInstance().getConnect().getPlayer(0));
				srv1_pl2.setText(Client.getInstance().getConnect().getPlayer(1));
			} else {
				srv1_pl1.setText("-");
				srv1_pl2.setText("-");
			}
			
		} catch (RemoteException e1) {
		}
	}

	/* **GETTER und SETTER** */
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
