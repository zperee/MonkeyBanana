package ch.monkeybanana.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import ch.monkeybanana.model.User;
import ch.monkeybanana.rmi.Client;
import ch.monkeybanana.view.WaitingScreen;

public class HomeListener implements ActionListener {
	private String button;
	private User u;
	private JFrame window;

	public HomeListener(User u, String button, JFrame window) {
		this.setButton(button);
		this.setU(u);
		this.setWindow(window);
	}

	public void actionPerformed(ActionEvent e) {
		if (this.getButton().equals("Verlassen")) {
			this.getWindow().dispose();
	    } else if(this.getButton().equals("Spielen")) {
			try {
				Client.getInstance().getConnect().setSlots(Client.getInstance().getConnect().getSlots() + 1);
				new WaitingScreen(getU());
				this.getWindow().setVisible(false);
			}
			catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public JFrame getWindow() {
		return window;
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}
}
