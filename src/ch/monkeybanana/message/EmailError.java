package ch.monkeybanana.message;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class EmailError extends JDialog {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmailError frame = new EmailError();
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
	public EmailError() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 276, 144);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPasswordMussAusgefllt = new JLabel("Keine g\u00FClltige Email");
		lblPasswordMussAusgefllt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPasswordMussAusgefllt.setBounds(57, 26, 189, 14);
		contentPane.add(lblPasswordMussAusgefllt);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnOk.setBounds(157, 72, 89, 23);
		contentPane.add(btnOk);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(EmailError.class.getResource("/images/cancel.png")));
		lblNewLabel.setBounds(12, 18, 35, 34);
		contentPane.add(lblNewLabel);
	}
}
