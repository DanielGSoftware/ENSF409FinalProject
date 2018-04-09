package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedObjects.Course;
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
			System.out.println("Error: loook at  course in student model wont work");
		} catch (ClassNotFoundException e) {
			System.out.println("Error: broswe course in proff model wont work");
		}
		return courselist;
	}
	
	public void sendEmailToProff(int courseid)
	{
		InfoExchange infoExchange=new InfoExchange("Send an Email to the Proff");
		//infoExchange.setInfo(the message for email) with courseid inside it
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
		}
		catch (IOException e) {
		System.out.println("Error: send email to proff for a course failed");
		}
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
}
