package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.omg.CORBA.PUBLIC_MEMBER;

import SharedObjects.Assignment;
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
			System.out.println("Error: look at  course in student model wont work");
		} catch (ClassNotFoundException e) {
			System.out.println("Error: browse course in student model wont work");
		}
		return courselist;
	}
	
	public void sendEmailToProff(int courseid, String[] message)
	{
		InfoExchange infoExchange=new InfoExchange("Send an Email to the Proff");
		String[] info=new String[4];
		info[0]=""+courseid;
		info[1]=message[0];
		info[2]=message[1];
		info[3]=message[2];
		infoExchange.setInfo(info);
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
		InfoExchange infoExchange=new InfoExchange("Upload Assignment");
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
	
	public int viewGradeForAssignment()
	{
		InfoExchange infoExchange=new InfoExchange("View Grades-Student");
		String filename="Assignment1.txt";
		int courseid=1070;
		int studentid=1000;
		int assignmentGrade=0;
		Grade grade=new Grade(filename, courseid, studentid, -1);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(grade);
			flushAndReset(sendObject);
			infoExchange=(InfoExchange) readObject.readObject();
			String[] temp=infoExchange.getInfo();
			assignmentGrade=Integer.parseInt(temp[0]);
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return assignmentGrade;
	}
	
	public String[] getAssignmentList(int courseid)
	{
		InfoExchange infoExchange=new InfoExchange("Get list of Assignments Student");
		Assignment assignment=new Assignment(courseid, null, null);
		String[] assignmentList=null;
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(assignment);
			flushAndReset(sendObject);
			infoExchange=(InfoExchange) readObject.readObject();
			assignmentList=infoExchange.getInfo();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return assignmentList;
	}
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
}
