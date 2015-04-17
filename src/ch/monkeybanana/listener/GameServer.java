package ch.monkeybanana.listener;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ch.monkeybanana.rmi.Validator;
import ch.monkeybanana.rmi.ValidatorImpl;

public class GameServer {

	public static void main(String[] args) {
		try {

			Registry reg = LocateRegistry.createRegistry(1251);
			Validator aValidator = new ValidatorImpl();

			reg.rebind("validator", aValidator);

		}
		catch (RemoteException e) {
			System.err.println("Server konnte nicht gestartert werden");
		}

	}
}
