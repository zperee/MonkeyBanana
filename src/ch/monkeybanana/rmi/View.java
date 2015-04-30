package ch.monkeybanana.rmi;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {

	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public View() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
		
		JPanel serverMsgs = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) serverMsgs.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		scrollPane.setViewportView(serverMsgs);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Slots");
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 46, 14);
		panel_3.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 177, 2);
		panel_3.add(separator);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 49, 177, 470);
		panel_3.add(scrollPane_1);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		scrollPane_1.setColumnHeaderView(panel_2);
		
		JLabel lblConnectedPlayers = new JLabel("Connected Players");
		lblConnectedPlayers.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		panel_2.add(lblConnectedPlayers);
		
		JPanel conPlayers = new JPanel();
		scrollPane_1.setViewportView(conPlayers);
	}
}
