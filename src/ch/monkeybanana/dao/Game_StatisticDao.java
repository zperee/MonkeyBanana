package ch.monkeybanana.dao;

import java.sql.SQLException;

import ch.monkeybanana.game.Player;
import ch.monkeybanana.model.User;

public interface Game_StatisticDao {
	
	/**
	 * Hier wird das Resultat eines Spiels in die DB 
	 * eingetragen.
	 * @author Elia Perenzin
	 * @param player1 {@link Player}
	 * @param player2 {@link Player}
	 * @param score1 {@link int}
	 * @param score2 {@link int}
	 * @param map {@link int}
	 * @param modus {@link int}
	 * @throws SQLException
	 */
	public abstract void setResult (int score1, int score2, String player1, String player2, int map, int modus, String winner) throws SQLException;
	
	/**
	 * Hier werden die Resultate eines Players aus der DB
	 * gelesen. 
	 * @param user {@link User}
	 * @throws SQLException
	 */
	public abstract double[] getResult(User user) throws SQLException;
}
