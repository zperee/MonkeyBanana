package ch.monkeybanana.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

/**
 * Dies ist die Klasse des Gameservers.
 * @author Dominic Pfister, Elia Perenzin GameServer.java Copyright
 * Berufsbildungscenter MonkeyBanana 2015
 */
public class GameServer {

	public static void main(String[] args) {
		try {
			Registry reg = LocateRegistry.createRegistry(1258); // Port auf dem der Server laeuft
			Communication aValidator = new CommunicationImpl();

			reg.rebind("validator", aValidator);

		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Server konnte nicht gestartet werden!",
					"Warnung!", JOptionPane.ERROR_MESSAGE);
		}
	}
}