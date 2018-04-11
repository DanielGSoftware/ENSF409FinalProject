package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedObjects.Assignment;
import SharedObjects.Course;
import SharedObjects.Grade;
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
	
	public void sendEmailToProff()
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
	
	public void downloadAssignment(String filename, int courseid)
	{
		System.out.println("Downloading assignment");
		InfoExchange infoExchange=new InfoExchange("Student Downloading Assignment");
		Assignment assignment=new Assignment(courseid, filename, null);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(assignment);
			flushAndReset(sendObject);
		}
		catch (IOException e) {
		System.out.println("Error: send email to proff for a course failed");
		}
	}
	
	public void uploadAssignment(int courseid, String filename, String path)
	{
		InfoExchange infoExchange=new InfoExchange("Student Uploading");
		Assignment assignment=new Assignment(courseid, filename, path);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(assignment);
			flushAndReset(sendObject);
		}
		catch (IOException e) {
		}
	}
	
	public String viewGradeForAssignment()
	{
		InfoExchange infoExchange=new InfoExchange("Getting Grade for Student");
		//
		Grade grade=new Grade(assignName, studentid, courseid, assignmentGrade);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(assignment);
			flushAndReset(sendObject);
		}
		catch (IOException e) {
		}
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
}
