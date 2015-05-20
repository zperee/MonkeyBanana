package ch.monkeybanana.dao;

import java.sql.Connection;
import java.sql.SQLException;

import ch.monkeybanana.game.Player;

public class Game_StatisticJDBCDao extends Database implements Game_StatisticDao {
	//Variable fuer Verbindung
	private Connection con = null;
		
	public void setResult(int score1, int score2, String player1, String player2, int map, int modus) throws SQLException {
		String sql = "INSERT INTO `monkeybanana`.`game_statistic` (`Points_User1`, `Points_User2`, `User1_ID`, `User2_ID`, `Modus_ID`, `Map_ID`) VALUES (?, ?, ?, ?, ?, ?)"; //Query
		
		con = getCon();
		ps = con.prepareStatement(sql);
		ps.setInt(1, score1);
		ps.setInt(2, score2);
		ps.setString(3, player1);
		ps.setString(4, player2);
		ps.setInt(5, map);
		ps.setInt(6, modus);
		ps.executeUpdate();
		
		closeCon();
	}

	
	public void getResult(Player player) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
