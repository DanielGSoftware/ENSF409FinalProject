package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedObjects.InfoExchange;
import SharedObjects.StudentEnrollment;

public class StudentModel extends MainModel {

	public StudentModel(ObjectOutputStream sendObject, ObjectInputStream readObject) {
		super(readObject, sendObject);
	}
	
	public String[] getCourseList(int studentid)
	{
		InfoExchange infoExchange=new InfoExchange("Get List of Courses Student");
		String[] courselist=null;
		StudentEnrollment studentEnrollment=new StudentEnrollment(0, studentid, 0);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(studentEnrollment);
			flushAndReset(sendObject);
			infoExchange= (InfoExchange) readObject.readObject();
			courselist=infoExchange.getInfo();
		} catch (IOException e) {
			System.out.println("Error: gdgdfgdfgdfgbroswe course in proff model wont work");
		} catch (ClassNotFoundException e) {
			System.out.println("Error: broswe course in proff model wont work");
		}
		return courselist;
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
}
