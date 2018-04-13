package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedObjects.InfoExchange;
import SharedObjects.User;

/**
 * Provides methods to handle reading and writing objects to the database manager.
 * Extends the main model.
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 13, 2018
 *
 */
public class LoginModel extends MainModel
{

	/**
	 * Constructor which calls the super constructor
	 * @param readObject - the ObjectInputStream to read from
	 * @param sendObject - the ObjectOutputStream to write to
	 */
	public LoginModel(ObjectInputStream readObject, ObjectOutputStream sendObject) {
		super(readObject, sendObject);
	}
	
	/**
	 * Reads a user name and password and checks if it is in the database
	 * @param user - the user name
	 * @param pass - the password
	 * @return an InfoExchange of which tells if the user exists or not
	 */
	public String[] loginAttempt(String user, String pass) {
		InfoExchange infoExchange = new InfoExchange("Login Attempt");
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			String[] userinfo=user.split(" ");
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
	
	/**
	 * Flushes and resets the send object
	 * @param sendObject - the ObjectOutputStream
	 * @throws IOException
	 */
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException{
		sendObject.flush();
		sendObject.reset();
	}
}