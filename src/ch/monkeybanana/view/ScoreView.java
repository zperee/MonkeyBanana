package ch.monkeybanana.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


	public class ScoreView extends JFrame implements ActionListener {
		
		private static final long serialVersionUID = 2002655073290070168L;
		private int score1;
		private int score2;
		private String player1;
		private String player2;
		private boolean winner;
		private JPanel contentPane;

		/**
		 * Create the frame.
		 * @author Dominic Pfister, Elia Perenzin
		 * @param score1 {@link int}
		 * @param score2 {@link int}
		 * @param user1 {@link String}
		 * @param user2 {@link String}
		 * @param winner {@link boolean}
		 * @param isForfait {@link boolean}
		 */
		
		public ScoreView(int score1, int score2, String user1, String user2, boolean winner, boolean isForfait) {
			setResizable(false);
			this.setScore1(score1);
			this.setScore2(score2);
			this.setPlayer1(user2);
			this.setPlayer2(user1);
			this.setWinner(winner);

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			setVisible(true);
			
			if (isForfait) {
				JLabel resultMsg = new JLabel("Du gewinnst forfait");
				
				resultMsg.setForeground(Color.GREEN);
				resultMsg.setFont(new Font("Tekton Pro", Font.BOLD, 32));
				resultMsg.setHorizontalAlignment(SwingConstants.CENTER);
				resultMsg.setBounds(25, 21, 389, 41);
				contentPane.add(resultMsg);
			} else if (winner) {
				JLabel resultMsg = new JLabel("Du hast gewonnen");
				
				resultMsg.setForeground(Color.GREEN);
				resultMsg.setFont(new Font("Tekton Pro", Font.BOLD, 32));
				resultMsg.setHorizontalAlignment(SwingConstants.CENTER);
				resultMsg.setBounds(25, 21, 389, 41);
				contentPane.add(resultMsg);
			
			} else if (!winner) {
				JLabel resultMsg = new JLabel("Du hast verloren");
				
				resultMsg.setForeground(Color.RED);
				resultMsg.setFont(new Font("Tekton Pro", Font.BOLD, 32));
				resultMsg.setHorizontalAlignment(SwingConstants.CENTER);
				resultMsg.setBounds(25, 21, 389, 41);
				contentPane.add(resultMsg);
			}
			
			JLabel lblVs = new JLabel("vs");
			lblVs.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblVs.setBounds(212, 94, 25, 41);
			contentPane.add(lblVs);
			
			JButton btnZumMenu = new JButton("zum Men\u00FC");
			btnZumMenu.setBounds(173, 227, 114, 23);
			contentPane.add(btnZumMenu);
			
			JPanel panel = new JPanel();
			panel.setBounds(180, 147, 90, 33);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel scorePlayer2 = new JLabel(Integer.toString(this.getScore1()));
			scorePlayer2.setHorizontalAlignment(SwingConstants.CENTER);
			scorePlayer2.setBounds(63, 18, 17, 14);
			panel.add(scorePlayer2);
			scorePlayer2.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			JLabel scorePlayer1 = new JLabel(Integer.toString(this.getScore2()));
			scorePlayer1.setHorizontalAlignment(SwingConstants.CENTER);
			scorePlayer1.setBounds(10, 18, 16, 14);
			panel.add(scorePlayer1);
			scorePlayer1.setFont(new Font("Tahoma", Font.BOLD, 16));
			
			JLabel label_1 = new JLabel("-");
			label_1.setHorizontalAlignment(SwingConstants.CENTER);
			label_1.setBounds(40, 11, 7, 25);
			panel.add(label_1);
			label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(30, 73, 89, 152);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel namePlayer1 = new JLabel(this.getPlayer2());
			namePlayer1.setHorizontalAlignment(SwingConstants.CENTER);
			namePlayer1.setBounds(0, 117, 89, 24);
			panel_1.add(namePlayer1);
			
			JLabel lblNewLabel_2 = new JLabel(new ImageIcon("images/MonkeyBlue.png"));
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setBounds(10, 11, 69, 95);
			panel_1.add(lblNewLabel_2);
			
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(325, 73, 89, 152);
			contentPane.add(panel_2);
			panel_2.setLayout(null);
			
			JLabel namePlayer2 = new JLabel(this.getPlayer1());
			namePlayer2.setHorizontalAlignment(SwingConstants.CENTER);
			namePlayer2.setBounds(0, 117, 89, 24);
			panel_2.add(namePlayer2);
			
			JLabel label_3 = new JLabel(new ImageIcon("images/MonkeyRed.png"));
			label_3.setHorizontalAlignment(SwingConstants.CENTER);
			label_3.setBounds(10, 11, 69, 95);
			panel_2.add(label_3);
		}
		
		//TODO
		public void actionPerformed(ActionEvent e) {
			new HomeView(null);
			
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
