package FrontEnd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

abstract class Users {
	protected Socket socket;
	protected ObjectOutputStream sendObject;
	protected ObjectInputStream readObject;
	
	public Users() {
		
	}
}
