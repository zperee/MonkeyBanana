package ch.monkeybanana.util;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Encoder;

/**
 * Diese Klasse wird benoetigt um das Passwort des Users zu verschluesseln und zu entschluesseln
 * @author Dominic Pfister, Elia Perenzin
 * CryptUtils.java
 * Copyright Berufsbildungscenter MonkeyBanana 2015
 */

public class CryptUtils {
	
	//Instanzvariablen
	private static final String DEFAULT_CODING = "UTF-8";
	private static BASE64Encoder enc = new BASE64Encoder();
//	private static BASE64Decoder dec = new BASE64Decoder();
	
	/**
	 * Hier wird das Passwort des Users verschlüsselt
	 * @author Elia Perenzin
	 * @param String mit Passwort unverschluesselt
	 * @return String mit Passwort verschluesselt
	 * @throws Exception
	 */
	public static String base64encode (String passwort) {
		try{
			//Passwort wird codiert
			return enc.encode(passwort.getBytes(DEFAULT_CODING));
		}
		catch (UnsupportedEncodingException e){
			return null;
		}
	}
	
	/**
	 * Hier wird das Passwort des User zurueck verschluesselt
	 * @author Elia Perenzin
	 * @param String mit Passwort verschluesselt
	 * @return String mit Passwort unverschluesselt
	 * @throws Exception
	 */
//	public static String base64decode (String passwort) {
//		try{
//			//Passwort wird decodiert
//			return new String (dec.decodeBuffer(passwort), DEFAULT_CODING);
//		}
//		catch (IOException e){
//			return null;
//		}
//	}
}
