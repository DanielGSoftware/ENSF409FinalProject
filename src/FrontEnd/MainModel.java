package FrontEnd;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import com.mysql.jdbc.Buffer;

abstract class MainModel  {
	protected ObjectOutputStream sendObject;
	protected ObjectInputStream readObject;
	
	
	public MainModel(ObjectInputStream a, ObjectOutputStream b) {
		readObject=a;
		sendObject=b;
	}
	
}
