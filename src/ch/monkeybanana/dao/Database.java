package ch.monkeybanana.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.monkeybanana.util.CryptUtils;

/**
 * Oberklasse aller JDBCDao enthaelt alle Informationen zur DB Verbindung
 * @author Dominic Pfister, Elia Perenzin Database.java Copyright
 * Berufsbildungscenter MonkeyBanana 2015
 */
public class Database {
	
	//Instanzvariablen
	private Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;
	
	/**
	 * Verbindungsinforamtionen fuer die DB, liest alle Infos
	 * aus der Datei data.csv
	 * @return con {@link Connection}
	 * @throws SQLException
	 */
	protected Connection getCon() throws SQLException {
//		String filename = "resources/config/data.csv"; //File mit allen Informationen
//		File file = new File(filename);
		
		//Array indem alle Informationen gespeichert werden.
//		String [] database = new String[3];
		
		//Liest alle Informationen aus dem CSV File und speichert sie in eine Liste
//		try{
//			Scanner inputStream = new Scanner(file);
//			int i = 0;
//			while (inputStream.hasNext()){
//				String data = inputStream.next();
//				database[i] = data;
//				i++;
//			}
//			inputStream.close();
//		} catch(FileNotFoundException e){
//			System.out.println("File nicht gefunden: " + filename);
//		}
		
		//Setzt die Werte des Arrays in die Variablen
		String db = "jdbc:mysql://192.168.3.173:3306/monkeybanana";
		String user = "monkeybanana";
		
		//Passwort wird entschlüsselt
		String pw = CryptUtils.base64decode("YjdFeVBoODdTSHM1ZDZwTA==");

		//Connection wird gesetzt
		setCon(DriverManager.getConnection(db, user, pw));
		return con;
	}
	
	/* **SETTER** */
	private void setCon(Connection con) {
		this.con = con;
	}
	
	/**
	 * Methode fuer schliessung der DB Verbindung
	 * @throws SQLException
	 */
	protected void closeCon() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
				con = null;
			}
		}
		catch (SQLException e) {

		}
	}
}
