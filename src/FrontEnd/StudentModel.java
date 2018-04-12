package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
	
	//DANIELS
	public int getAssignGrade(String [] assignInfo) {
		InfoExchange infoExchange = new InfoExchange("View Grades-Student");
		// [0] is the course ID, [1] is the name, [2] would be path, it it is null
		Assignment assignment = new Assignment(Integer.parseInt(assignInfo[0]), assignInfo[1], null);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(assignment);
			flushAndReset(sendObject);
//			infoExchange = (InfoExchange) readObject.readObject();
			Grade grade = (Grade)readObject.readObject(); 
		} catch (IOException e) {
			System.out.println("Error: Could not find grade for assignemnt");
		} catch (ClassNotFoundException e) {
			System.out.println("Error: Could not create InfoExchange object");
		}
		//Where info[0] is the grade as a string
		return Integer.parseInt(infoExchange.getInfo()[0]);
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
	
	public int viewGradeForAssignment()
	{
		InfoExchange infoExchange=new InfoExchange("Getting Grade for Student");
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
	
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
}
