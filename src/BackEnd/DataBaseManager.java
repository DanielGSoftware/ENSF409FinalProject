package BackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.IllegalFormatCodePointException;
import java.sql.Connection;

import com.mysql.jdbc.Buffer;
import com.mysql.jdbc.PreparedStatement;

import SharedObjects.Course;
import SharedObjects.InfoExchange;

public class DataBaseManager implements Runnable {
	private ObjectOutputStream writeobject;
	private ObjectInputStream readobject;
	private PreparedStatement statement;
	private Connection jdbc_connection;
	public static String CONNECTIONINFO = "jdbc:mysql://localhost:3306/project",  
			  LOGIN          = "root",
			  PASSWORD       = "huzaifa147";
	public static String COURSETABLE = "Courses";
	public static String USERTABLE = "Users";
	private String id;
	
	public DataBaseManager(Socket socket) throws IOException, SQLException, ClassNotFoundException {
		writeobject=new ObjectOutputStream(socket.getOutputStream());
		readobject=new ObjectInputStream(socket.getInputStream());
		Class.forName("com.mysql.jdbc.Driver");
		jdbc_connection = DriverManager.getConnection(CONNECTIONINFO, LOGIN, PASSWORD);
		id="5000";
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				InfoExchange infoExchange=(InfoExchange) readobject.readObject();
				System.out.println("Recieved object of infoexchange");
				String string=infoExchange.getOpcode();
				System.out.println("Now in checkOperation");
				
				if (string.equals("Browse Courses Proff")){
					Course course=(Course)readobject.readObject();
					System.out.println("course object read");
					//infoExchange=new InfoExchange(course.browseCourses(COURSETABLE, jdbc_connection, statement));
					infoExchange.setInfo(course.browseCourses(COURSETABLE, jdbc_connection, statement));
					writeobject.writeObject(infoExchange);
				}
				
				else if (string.equals("Create Course Proff"))
				{
					Course course=(Course)readobject.readObject();
					course.createCourse(COURSETABLE, jdbc_connection, statement);
				}
				
				else if (string.equals("Course Activation Status"))
				{
					Course course=(Course)readobject.readObject();
					course.courseActivationStatus(COURSETABLE, jdbc_connection, statement);
				}
				else if(string.equals("Login Attempt")) {
					
				}
				
			} 
			catch (ClassNotFoundException | IOException e) {
				System.out.println("Client has left");
				break;
			}
		}
	}
	
//	public void checkOperation(String string) throws ClassNotFoundException, IOException
//	{
//		System.out.println("Now in checkOperation");
//		if (string.equals("Browse Course Proff"))
//		{
//			Course course=(Course)readobject.readObject();
//			System.out.println("course object read");
//			InfoExchange infoExchange=new InfoExchange(course.browseCourses(COURSETABLE, jdbc_connection, statement));
//			writeobject.writeObject(infoExchange);
//		}
//		if (string.equals("Create Course Proff"))
//		{
//			Course course=(Course)readobject.readObject();
//			course.createCourse(COURSETABLE, jdbc_connection, statement, id);
//		}
//	}
	
}	
