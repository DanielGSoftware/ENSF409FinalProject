package FrontEnd;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import com.mysql.jdbc.Buffer;

abstract class MainModel  {
	protected ObjectOutputStream sendObject;
	protected ObjectInputStream readObject;
	
	public MainModel(ObjectInputStream readObject, ObjectOutputStream sendObject) {
		this.readObject= readObject;
		this.sendObject=sendObject;
	}
	
}
