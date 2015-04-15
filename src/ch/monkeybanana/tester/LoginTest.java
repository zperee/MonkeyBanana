package ch.monkeybanana.tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.sun.jndi.ldap.Connection;

import ch.monkeybanana.controller.MBController;
import ch.monkeybanana.dao.Database;
import ch.monkeybanana.model.User;
import ch.monkeybanana.view.LoginView;
import ch.monkeybanana.view.RegistrierenView;

public class LoginTest {

	public static void main(String[] args) {
	new LoginView();
//new RegistrierenView();
	
	User user = new User();
	user.setEmail("dffj-sadf@hotmail.com");
	user.setPasswort("Md5(hgjh35653hjsdvxD)");
	user.setUsername("zdomaa");
	 
	MBController.getInstance().registrieren(user);

	}
}
