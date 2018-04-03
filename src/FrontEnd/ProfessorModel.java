package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedObjects.Course;
import SharedObjects.InfoExchange;

public class ProfessorModel extends MainModel {
	
	public ProfessorModel(ObjectOutputStream sendObject, ObjectInputStream readObject)
	{
		super(readObject, sendObject);
	}
	
	public String[] browseCourse(int proffid) 
	{
		InfoExchange object=new Course(proffid, null, -1, "Browse Courses");
		String[] courselist=null;
		try {
			sendObject.writeObject(object);
			sendObject.reset();
			courselist=(String[]) readObject.readObject();
		} catch (IOException e) {
			System.out.print("Error: broswe course in proff model wont work");
		} catch (ClassNotFoundException e) {
			System.out.print("Error: broswe course in proff model wont work");
		}
		return courselist;
	}
}
