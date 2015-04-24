package ch.monkeybanana.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameServer {

	public static void main(String[] args) {
		try {

			Registry reg = LocateRegistry.createRegistry(1257); //port
			Validator aValidator = new ValidatorImpl();

			reg.rebind("validator", aValidator);

		}
		catch (RemoteException e) {
			System.err.println("Server konnte nicht gestartert werden");
		}

	}
}
