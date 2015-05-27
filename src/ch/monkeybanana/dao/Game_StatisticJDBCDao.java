package ch.monkeybanana.dao;

import java.sql.Connection;
import java.sql.SQLException;

import ch.monkeybanana.model.User;

public class Game_StatisticJDBCDao extends Database implements
		Game_StatisticDao {
	// Variable fuer Verbindung
	private Connection con = null;

	public void setResult(int score1, int score2, String player1,
			String player2, int map, int modus, String winner)
			throws SQLException {
		String sql = "INSERT INTO `monkeybanana`.`game_statistic` (`Points_User1`, `Points_User2`, `User1_ID`, `User2_ID`, `Modus_ID`, `Map_ID`, Winner_ID) VALUES (?, ?, ?, ?, ?, ?, ?)"; // Query

		con = getCon();
		ps = con.prepareStatement(sql);
		ps.setInt(1, score1);
		ps.setInt(2, score2);
		ps.setString(3, player1);
		ps.setString(4, player2);
		ps.setInt(5, map);
		ps.setInt(6, modus);
		ps.setString(7, winner);
		ps.executeUpdate();

		closeCon();
	}

	public double[] getResult(User user) throws SQLException {
		double[] result = new double[2];

		String sql = "SELECT COUNT(Winner_ID) AS Winner FROM game_statistic WHERE Winner_ID = ?;";
		con = getCon();
		ps = con.prepareStatement(sql);
		ps.setString(1, user.getUsername());

		rs = ps.executeQuery();
		while (rs.next()) {
			result[0] = rs.getInt("Winner");
			break;
		}

		String sql2 = "SELECT COUNT(*) AS Winner FROM game_statistic where User1_ID = ? OR User2_ID = ?;";
		ps = con.prepareStatement(sql2);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getUsername());

		rs = ps.executeQuery();

		while (rs.next()) {
			result[1] = rs.getInt("Winner");
			break;
		}

		return result;
	}
}