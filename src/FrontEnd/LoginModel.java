package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedObjects.InfoExchange;

public class LoginModel extends MainModel
{

	public LoginModel(ObjectInputStream readObject, ObjectOutputStream sendObject) {
		super(readObject, sendObject);
	}
	
	public boolean loginAttempt(String user, String pass) {
		boolean validLogin = true;
		InfoExchange infoExchange = new InfoExchange("Login Attempt");
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			infoExchange = (InfoExchange) readObject.readObject();
			
		}
		catch (IOException e) {
			System.out.println("Error: Login Attempt couldn't send to server");
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			System.out.println("Error: Login Attempt couldn't read from server");
			e.printStackTrace();
		}
		
		return validLogin;
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException{
		sendObject.flush();
		sendObject.reset();
	}
}