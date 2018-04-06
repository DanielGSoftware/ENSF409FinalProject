package BackEnd;
/**
 * Huzaifa Amar and Daniel Guieb
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.Connection;

import SharedObjects.Assignment;
import SharedObjects.Course;
import SharedObjects.InfoExchange;
import SharedObjects.StudentEnrollment;
import SharedObjects.User;

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
	public static String STUDENTENROLLMENTTABLE = "Student_Enrollment";
	public static String ASSIGNMENTTABLE = "Assignment_Table";
	private int id;
	
	public DataBaseManager(Socket socket) throws IOException, SQLException, ClassNotFoundException {
		writeobject=new ObjectOutputStream(socket.getOutputStream());
		readobject=new ObjectInputStream(socket.getInputStream());
		Class.forName("com.mysql.jdbc.Driver");
		jdbc_connection = DriverManager.getConnection(CONNECTIONINFO, LOGIN, PASSWORD);
		id=3000;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				InfoExchange infoExchange=(InfoExchange) readobject.readObject();
				System.out.println("Recieved object of infoexchange");
				String string=infoExchange.getOpcode();
				System.out.println("Now in checkOperation");
				
				if (string.equals("Login Attempt"))
				{
					System.out.println("in login attempt databasemangaer");
					User object= (User) readobject.readObject();
					String[] strings=object.findUser(USERTABLE, jdbc_connection, statement);
					infoExchange.setInfo(strings);
					writeobject.writeObject(infoExchange);
				}
				
				else if (string.equals("Browse Courses Proff")){
					Course course=(Course)readobject.readObject();
					System.out.println("course object read");
					//infoExchange=new InfoExchange(course.browseCourses(COURSETABLE, jdbc_connection, statement));
					infoExchange.setInfo(course.browseCourses(COURSETABLE, jdbc_connection, statement));
					writeobject.writeObject(infoExchange);
				}
				
				else if (string.equals("Create Course Proff"))
				{
					Course course=(Course)readobject.readObject();
					course.createCourse(COURSETABLE, jdbc_connection, statement, id);
				}
				
				else if (string.equals("Course Activation Status"))
				{
					Course course=(Course)readobject.readObject();
					course.courseActivationStatus(COURSETABLE, jdbc_connection, statement);
				}
				
				else if(string.equals("Login Attempt")) {
					User user = (User)readobject.readObject(); 
				}
				
//				else if (string.equals("Search Students Proff"))
//				{
//					StudentEnrollment object= (StudentEnrollment) readobject.readObject();
//					if (object.browseStudentsEnrolled(USERTABLE, jdbc_connection, statement)) {
//						User user=new User(object.getStudentId(), null, null, null, null, "S");
//						String[] strings=user.findUser(USERTABLE, jdbc_connection, statement);
//						infoExchange.setInfo(strings);
//						writeobject.writeObject(infoExchange);
//					}
//					else {
//						System.out.println("student not found");
//					}	
//				}
				
				else if (string.equals("Search Students Proff"))
				{
					User user = (User)readobject.readObject(); 
					String[] strings=new String[5];
					strings=user.searchStudents(USERTABLE, jdbc_connection, statement);
					System.out.println(strings[0]);
					infoExchange.setInfo(strings);
					writeobject.writeObject(infoExchange);
				}
				
				else if (string.equals("Student Enrollment Proff"))
				{
					StudentEnrollment object= (StudentEnrollment) readobject.readObject();
					String[] strings=object.browseStudentsEnrolled(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					infoExchange.setInfo(strings);
					writeobject.writeObject(infoExchange);
				}
				
				else if (string.equals("View Students Proff"))
				{
					StudentEnrollment studentEnrollment= (StudentEnrollment) readobject.readObject();
					int[] studentsid=studentEnrollment.viewStudents(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					ArrayList<String> list=new ArrayList<String>();
					for (int i=0; i<studentsid.length; i++) {
						User user=new User(studentsid[i], null, null, null, null, "S");
						String[] strings=user.searchStudentsUserId(USERTABLE, jdbc_connection, statement);
						//System.out.println(strings[2]);
						list.add(strings[2]);
					}
					
					String[] temp=new String[list.size()];
					for (int i=0; i<list.size(); i++) {
						temp[i]=list.get(i);
					}
					infoExchange.setInfo(temp);
					writeobject.writeObject(infoExchange);
				}
				
				else if (string.equals("Browse Assignment Proff"))
				{
					Assignment assignment= (Assignment) readobject.readObject();
					String[] result=assignment.searchAssignment(ASSIGNMENTTABLE, jdbc_connection, statement);
					infoExchange.setInfo(result);
					writeobject.writeObject(infoExchange);
				}
				
				else if (string.equals("Upload Assignment Proff"))
				{
					Assignment assignment = (Assignment) readobject.readObject();
					assignment.addAssignments(ASSIGNMENTTABLE, jdbc_connection, statement, id);
				}
				
				id+=10;
			} 
			catch (ClassNotFoundException | IOException e) {
				System.out.println("Client has left");
				break;
			}
		}
	}
}	
