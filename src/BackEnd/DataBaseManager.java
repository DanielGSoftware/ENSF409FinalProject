package BackEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import SharedObjects.Assignment;
import SharedObjects.Course;
import SharedObjects.Grade;
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
	public static String GRADETABLE = "Grade_Table";
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
					User user= (User) readobject.readObject();
					String[] listOfUsers=user.findUser(USERTABLE, jdbc_connection, statement);
					infoExchange.setInfo(listOfUsers);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("View Courses Proff")){
					Course course=(Course)readobject.readObject();
					System.out.println("course object read");
					//infoExchange=new InfoExchange(course.browseCourses(COURSETABLE, jdbc_connection, statement));
					infoExchange.setInfo(course.browseCourses(COURSETABLE, jdbc_connection, statement));
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
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
				
				else if (string.equals("Search Students Proff"))
				{
					User user = (User)readobject.readObject(); 
					String[] listOfStudents=new String[5];
					listOfStudents=user.searchStudents(USERTABLE, jdbc_connection, statement);
					System.out.println(listOfStudents[0]);
					infoExchange.setInfo(listOfStudents);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("Student Enrollment Proff"))
				{
					StudentEnrollment enrollments= (StudentEnrollment) readobject.readObject();
					String[] listOfEnrollments=enrollments.viewStudentsEnrolled(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					infoExchange.setInfo(listOfEnrollments);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("View Students Proff"))
				{
					StudentEnrollment enrollments= (StudentEnrollment) readobject.readObject();
					int[] studentIDs=enrollments.viewStudents(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					ArrayList<String> list=new ArrayList<String>();
					for (int i=0; i<studentIDs.length; i++) {
						User user=new User(studentIDs[i], null, null, null, null, "S");
						String[] listOfStudents=user.searchStudentsUserId(USERTABLE, jdbc_connection, statement);
						//System.out.println(strings[2]);
						list.add(listOfStudents[2]);
					}
					
					String[] temp=new String[list.size()];
					for (int i=0; i<list.size(); i++) {
						temp[i]=list.get(i);
					}
					infoExchange.setInfo(temp);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("View Assignment Proff"))
				{
					Assignment assignment= (Assignment) readobject.readObject();
					String[] result=assignment.searchAssignment(ASSIGNMENTTABLE, jdbc_connection, statement);
					infoExchange.setInfo(result);
					System.out.println(result[0]);
					System.out.println(result[1]);
					System.out.println(result[2]);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("Download All Assignments into Proff Folder"))
				{
					Assignment assignment= (Assignment) readobject.readObject();
					String[] fileinfo=assignment.downloadAssignmentsProff(ASSIGNMENTTABLE, jdbc_connection, statement);
					FileHandler fileHandler=null;
					for (int i=0; i<fileinfo.length; i++) {
						String[] filenameandpath=fileinfo[i].split(";");
						fileHandler=new FileHandler(filenameandpath[0], filenameandpath[1]);
						fileHandler.downloadAssignmentsToProff();
					}
				}
				
				else if (string.equals("Upload Assignment"))
				{
					Assignment assignment = (Assignment) readobject.readObject();
					id+=10;
					assignment.addAssignments(ASSIGNMENTTABLE, jdbc_connection, statement, id);
				}
				
				else if (string.equals("Student Downloading Assignment"))
				{
					System.out.println("Download assignmenets and studd in database manger");
					Assignment assignment=(Assignment) readobject.readObject();
					String[] fileinfo=assignment.downloadAssignmentStudent(ASSIGNMENTTABLE, jdbc_connection, statement);
					FileHandler fileHandler=new FileHandler(fileinfo[0], fileinfo[1]);
					fileHandler.downloadAssignmentToStudent();
				}
				
				else if (string.equals("Get List of Courses Student"))
				{
					StudentEnrollment studentEnrollment=(StudentEnrollment) readobject.readObject();
					int[] courses_student=studentEnrollment.viewCoursesForStudent(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					Course course=null;
					ArrayList<String> listofcourses=new ArrayList<String>();
					for (int i=0; i<courses_student.length; i++) {
						course=new Course(-1, null, -1, courses_student[i]);
						String coursename=course.courseofStudent(COURSETABLE, jdbc_connection, statement);
						if (coursename!=null)
							listofcourses.add(coursename);
					}
					String[] result=new String[listofcourses.size()];
					for (int i=0; i<listofcourses.size(); i++) {
						System.out.println(listofcourses.get(i));
						result[i]=listofcourses.get(i);
					}
					infoExchange.setInfo(result);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("Send an Email to the Proff"))
				{
					String[] email=infoExchange.getInfo();
					User user=new User(Integer.parseInt(email[1]), null, null, null, null, "S"); //userid pulled from joptionpane
					String[] emailinfostudent=user.getEmailInfoStudent(USERTABLE, jdbc_connection, statement);
					Course course=new Course(-1, null, 1, Integer.parseInt(email[0])); //course id from infoexchange string array
					int proffid=course.getProffID(COURSETABLE, jdbc_connection, statement);
					user=new User(proffid, null, null, null, null, "P");
					String[] proffemail=user.getEmailInfoProff(USERTABLE, jdbc_connection, statement);
					EmailHandler emailHandler=new EmailHandler(emailinfostudent[0], emailinfostudent[1], proffemail[0], email[2], email[3]);
					emailHandler.createEmail();
				}
				
				else if (string.equals("Send Email to all Students Enrolled in Course"))
				{
					String[] emailInfo=infoExchange.getInfo();
					StudentEnrollment studentEnrollment=new StudentEnrollment(-1, -1, Integer.parseInt(emailInfo[0]));
					int[] studentids=studentEnrollment.viewStudents(STUDENTENROLLMENTTABLE, jdbc_connection, statement);
					User user=new User(Integer.parseInt(emailInfo[3]), null, null, null, null, "P");
					String[] emailinfoproff=user.getEmailInfoProff(USERTABLE, jdbc_connection, statement);
					for (int i=0; i<studentids.length; i++) {
						user=new User(studentids[i], null, null, null, null, "S");
						String[] emailofstudent=user.getEmailInfoStudent(USERTABLE, jdbc_connection, statement);
						EmailHandler emailHandler=new EmailHandler(emailinfoproff[0], emailinfoproff[1], emailofstudent[0], emailInfo[1], emailInfo[2]);
						emailHandler.createEmail();
					}
				}
				
				else if (string.equals("Mark Assignment-Proff"))
				{
					Grade grade=(Grade) readobject.readObject();
					grade.proffInsertingGrade(GRADETABLE, jdbc_connection, statement, id);
				}
				
				else if (string.equals("View Grades-Student"))
				{
					Grade grade=(Grade) readobject.readObject();
					String[] assignmentGrade=grade.viewStudentGrade(GRADETABLE, jdbc_connection, statement);
					infoExchange.setInfo(assignmentGrade);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				else if (string.equals("Get list of Assignments Student"))
				{
					Assignment assignment=(Assignment) readobject.readObject();
					String[] assignmentList=assignment.getAssignmentList(ASSIGNMENTTABLE, jdbc_connection, statement);
					infoExchange.setInfo(assignmentList);
					writeobject.writeObject(infoExchange);
					flushAndReset(writeobject);
				}
				
				id+=10;
			} 
			catch (ClassNotFoundException | IOException e) {
				System.out.println("Client has left");
				break;
			}
		}
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
	
}	
