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
		Course course=new Course(proffid, null, -1);
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
			System.out.print("Error: broswe course in proff model wont work");
		} catch (ClassNotFoundException e) {
			System.out.print("Error: broswe course in proff model wont work");
		}
		return courselist;
	}
}
