package BackEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import com.mysql.jdbc.PreparedStatement;

import SharedObjects.InfoExchange;

public class DataBaseManager implements Runnable {
	private ObjectOutputStream writeobject;
	private ObjectInputStream readobject;
	private PreparedStatement statement;
	private Connection jdbc_connection;
	public static String DATABASENAME="project";
	public static String CONNECTIONINFO = "jdbc:mysql://localhost:3306/project",  
			  LOGIN          = "root",
			  PASSWORD       = "huzaifa147";
	
	public DataBaseManager(Socket socket) throws IOException, SQLException, ClassNotFoundException {
		writeobject=new ObjectOutputStream(socket.getOutputStream());
		readobject=new ObjectInputStream(socket.getInputStream());
		Class.forName("com.mysql.jdbc.Driver");
		jdbc_connection = DriverManager.getConnection(CONNECTIONINFO, LOGIN, PASSWORD);
	}
	
	@Override
	public void run() {
		try {
			InfoExchange object=(InfoExchange) readobject.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
}	
