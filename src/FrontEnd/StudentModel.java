package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedObjects.Assignment;
import SharedObjects.Grade;
import SharedObjects.InfoExchange;
import SharedObjects.StudentEnrollment;

/**
 * Provides methods to handle reading and writing objects to the database manager.
 * Extends the main model.
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 13, 2018
 *
 */
public class StudentModel extends MainModel {

	/**
	 * Constructor which calls the super constructor
	 * @param sendObject - the ObjectOutputStream to write from
	 * @param readObject - the ObjectInputStream to read from
	 */
	public StudentModel(ObjectOutputStream sendObject, ObjectInputStream readObject) {
		super(readObject, sendObject);
	}
	
	/**
	 * Gets the courses that a student is enrolled in from the database
	 * @param studentid - the student's ID
	 * @return the courses as a String array
	 */
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
	
	/**
	 * Sends an email to the professor 
	 * @param courseid - the course ID of the professor's course
	 * @param message - information of the message to send
	 */
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
	
	/**
	 * Downloads an assignment 
	 * @param filename - the name of the file
	 * @param courseid - the course ID of the course that the assignment is in
	 */
	public void downloadAssignment(String filename, int courseid)
	{
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
	
	/**
	 * Uploads an assignment
	 * @param courseid - the course ID of the course to upload in to
	 * @param filename - the name of the file
	 * @param path - the path where the file is 
	 */
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
	
	/**
	 * Gets the grade of an assignment
	 * @param filename - the name of the assignment
	 * @param courseid - the course ID of the course which the assignment is in
	 * @param studentid - the ID of the student submitting the assignment
	 * @return the grade 
	 */
	public int viewGradeForAssignment(String filename, int courseid, int studentid)
	{
		InfoExchange infoExchange=new InfoExchange("View Grades-Student");
		int assignmentGrade=0;
		Grade grade=new Grade(filename, studentid, courseid, -1);
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
	
	/**
	 * Gets the list of assignments in a course
	 * @param courseid - the ID of the course
	 * @return the assignments as a String array
	 */
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
			if (assignmentList==null) {
				assignmentList=new String[1];
				assignmentList[0]="There are currently no active assignments";
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return assignmentList;
	}
	
	/**
	 * Flushes and resets the send object
	 * @param sendObject - the ObjectOutputStream
	 * @throws IOException
	 */
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
}
