package FrontEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import SharedObjects.Course;
import SharedObjects.InfoExchange;

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
			sendObject.flush();
			sendObject.reset();
			System.out.println("writing  coursse to database");
			sendObject.writeObject(course);
			sendObject.flush();
			sendObject.reset();
			infoExchange=(InfoExchange) readObject.readObject();
			System.out.println("object of infoexhcnade recieved");
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
		Course course=new Course(proffid, strings[1], Integer.parseInt(strings[2]), Integer.parseInt(strings[0]));
		InfoExchange infoExchange=new InfoExchange("Create Course Proff");
		try {
			sendObject.writeObject(infoExchange);
			sendObject.flush();
			sendObject.reset();
			sendObject.writeObject(course);
			sendObject.flush();
			sendObject.reset();
		} catch (IOException e) {
			System.out.print("Error: broswe course in proff model wont work");
		}
	}
	
	public void courseActive(String[] course) 
	{
		//Course course=new Course (0, coursename[], -1);
		String coursename=course[0];
		String active=course[1];
		//String courseid=course[0];
		Course c=null;
		if (active.equals("Currently Active to Students")) {
			c=new Course(0, coursename, 0, 1130);
		}
		else {
			c=new Course(0, coursename, 1, 1130);
		}
		InfoExchange infoExchange=new InfoExchange("Course Activation Status");
		try {
			sendObject.writeObject(infoExchange);
			sendObject.flush();
			sendObject.reset();
			sendObject.writeObject(c);
			sendObject.flush();
			sendObject.reset();
		}
		catch (IOException e) {
			System.out.print("Error: course activation status wont work");

		}
	}
}
