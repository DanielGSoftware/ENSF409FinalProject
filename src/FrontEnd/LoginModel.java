package FrontEnd;
/**
 * Huzaifa Amar and Daniel Guieb
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedObjects.InfoExchange;
import SharedObjects.User;

public class LoginModel extends MainModel
{

	public LoginModel(ObjectInputStream readObject, ObjectOutputStream sendObject) {
		super(readObject, sendObject);
	}
	
	public String[] loginAttempt(String user, String pass) {
		//boolean validLogin = true;
		InfoExchange infoExchange = new InfoExchange("Login Attempt");
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			String[] userinfo=user.split(" ");
			//System.out.println(userinfo);
			User object=new User(0, pass, null, userinfo[0], userinfo[1], null);
			sendObject.writeObject(object);
			flushAndReset(sendObject);
			infoExchange=(InfoExchange) readObject.readObject();
		}
		catch (IOException e) {
			System.out.println("Error: Login Attempt couldn't send to server");
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			System.out.println("Error: Login Attempt couldn't read from server");
			e.printStackTrace();
		}
		
		return infoExchange.getInfo();
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException{
		sendObject.flush();
		sendObject.reset();
	}
}