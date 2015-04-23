package ch.monkeybanana.tester;

import java.util.regex.Pattern;

import ch.monkeybanana.view.LoginView;


public class LoginTest {
	final static String input = "elia.perenzinbbcag.ch";
    final static Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    
	public static void main(String[] args) {

	new LoginView();
//NEW REGISTRIERENVIEW();
	
//	User user = new User();
//	user.setEmail("dffj-sadf@hotmail.com");
//	user.setPasswort("1234");
//	user.setUsername("zdomaa2");
//	 
//	MBController.getInstance().registrieren(user);
		
//	 
//	    if (!pattern.matcher(input).matches()) {
//	       System.out.println("Keine gültige Email eingegeben");
//	    }

	}
}
