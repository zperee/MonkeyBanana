package ch.monkeybanana.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;

import ch.monkeybanana.rmi.Client;

public class ScoreView extends JFrame {
	private int score1;
	private int score2;
	private String player1;
	private String player2;
	private boolean winner;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	
	public ScoreView(int score1, int score2, String user1, String user2, boolean winner) {
		this.setScore1(score1);
		this.setScore2(score2);
		this.setPlayer1(user1);
		this.setPlayer2(user2);
		this.setWinner(winner);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		if(winner){
		JLabel lblDuHastGewonnen = new JLabel("Du hast gewonnen");
		
		lblDuHastGewonnen.setForeground(Color.GREEN);
		lblDuHastGewonnen.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 21));
		lblDuHastGewonnen.setBounds(109, 28, 215, 39);
		contentPane.add(lblDuHastGewonnen);
		
		} else if (!winner) {
			JLabel lblDuHastVerloren = new JLabel("Du hast verloren");
			
			lblDuHastVerloren.setForeground(Color.RED);
			lblDuHastVerloren.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 21));
			lblDuHastVerloren.setBounds(125, 28, 215, 39);
			contentPane.add(lblDuHastVerloren);
		}
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("images/monkeyBlue.png"));
		lblNewLabel.setBounds(55, 94, 34, 67);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("images/monkeyRed.png"));
		lblNewLabel_1.setBounds(321, 95, 34, 67);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblVs = new JLabel("vs");
		lblVs.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVs.setBounds(200, 103, 27, 41);
		contentPane.add(lblVs);
		
		JLabel lblScore = new JLabel(Integer.toString(this.getScore1()));
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblScore.setBounds(55, 204, 46, 14);
		contentPane.add(lblScore);
		
		JLabel label = new JLabel(Integer.toString(this.getScore2()));
		label.setFont(new Font("Tahoma", Font.BOLD, 16));
		label.setBounds(321, 204, 46, 14);
		contentPane.add(label);
		
		JLabel lblName_1 = new JLabel(getPlayer1());
		lblName_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName_1.setBounds(55, 166, 58, 14);
		contentPane.add(lblName_1);
		
		JLabel lblName = new JLabel(getPlayer2());
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(321, 167, 46, 14);
		contentPane.add(lblName);
	}

	public int getScore1() {
		return score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

}
