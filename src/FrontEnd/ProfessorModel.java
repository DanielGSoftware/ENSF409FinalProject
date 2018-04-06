package FrontEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import SharedObjects.Course;
import SharedObjects.InfoExchange;
import SharedObjects.StudentEnrollment;
import SharedObjects.User;

public class ProfessorModel extends MainModel {
	
	public ProfessorModel(ObjectOutputStream sendObject, ObjectInputStream readObject)
	{
		super(readObject, sendObject);
	}
	
	public String[] browseCourse(int proffid) 
	{
		Course course=new Course(proffid, null, -1, -1);
		String[] courselist=null;
		InfoExchange infoExchange=new InfoExchange("Browse Courses Proff");
		try {
			System.out.println("writing info exchange to database");
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			System.out.println("writing  coursse to database");
			sendObject.writeObject(course);
			flushAndReset(sendObject);
			infoExchange=(InfoExchange) readObject.readObject();
			System.out.println("object of infoExchange recieved");
			courselist=infoExchange.getInfo();
		} catch (IOException e) {
			System.out.println("Error: gdgdfgdfgdfgbroswe course in proff model wont work");
		} catch (ClassNotFoundException e) {
			System.out.println("Error: broswe course in proff model wont work");
		}
		return courselist;
	}
	
	public void createCourse(int proffid, String[] strings)
	{
		Course course=new Course(proffid, strings[0], Integer.parseInt(strings[1]), -1);
		InfoExchange infoExchange=new InfoExchange("Create Course Proff");
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(course);
			flushAndReset(sendObject);
		} catch (IOException e) {
			System.out.print("Error: broswe course in proff model wont work");
		}
	}
	
	public void courseActive(String[] course) 
	{
		String coursename=course[1];
		String active=course[2];
		String courseid=course[0];
		Course c=null;
		if (active.equals("Currently Active to Students")) {
			c=new Course(0, coursename, 0, Integer.parseInt(courseid));
		}
		else {
			c=new Course(0, coursename, 1, Integer.parseInt(courseid));
		}
		InfoExchange infoExchange=new InfoExchange("Course Activation Status");
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(c);
			flushAndReset(sendObject);
		}
		catch (IOException e) {
			System.out.print("Error: Course Activation Status wont work");
		}
	}
	
//	public String[] SearchStudents(int studentid)
//	{
//		StudentEnrollment se=new StudentEnrollment(0, studentid, 0);
//		InfoExchange infoExchange=new InfoExchange("Search Students Proff");
//		try {
//			sendObject.writeObject(infoExchange);
//			flushAndReset(sendObject);
//			sendObject.writeObject(se);
//			flushAndReset(sendObject);
//			infoExchange= (InfoExchange) readObject.readObject();
//		} catch (IOException e) {
//			System.out.print("Error: search enn in proff model wont work");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return infoExchange.getInfo();
//	}
	
	public String[] SearchStudents(String lastname, int courseid)
	{
		User user=new User(0, null, null, null, lastname, "S");
		InfoExchange infoExchange=new InfoExchange("Search Students Proff");
		String[] result=new String[5];
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(user);
			flushAndReset(sendObject);
			infoExchange= (InfoExchange) readObject.readObject();
			result=infoExchange.getInfo();
			result[4]=StudentEnrollment(Integer.parseInt(result[3]), courseid);
		}
		catch (IOException e) {
			System.out.print("Error: search students in proff model wont work");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String StudentEnrollment(int studentid, int courseid)
	{
		StudentEnrollment se=new StudentEnrollment(0, studentid, courseid);
		InfoExchange infoExchange=new InfoExchange("Student Enrollment Proff");
		String string=null;
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(se);
			flushAndReset(sendObject);
			infoExchange= (InfoExchange) readObject.readObject();
			string=infoExchange.getInfo()[0];
		} catch (IOException | ClassNotFoundException e) {
			System.out.print("Error: search enn in proff model wont work");
		} 
		return string;
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
}
