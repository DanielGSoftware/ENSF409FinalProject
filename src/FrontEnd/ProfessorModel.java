package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import SharedObjects.Assignment;
import SharedObjects.Course;
import SharedObjects.Grade;
import SharedObjects.InfoExchange;
import SharedObjects.StudentEnrollment;
import SharedObjects.User;


/**
 * Provides methods to handle reading and writing objects to the database manager.
 * Extends the main model.
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 13, 2018
 *
 */
public class ProfessorModel extends MainModel {
	
	/**
	 * Constructor which calls the super constructor
	 * @param sendObject - the ObjectOutputStream to write from
	 * @param readObject - the ObjectInputStream to read from
	 */
	public ProfessorModel(ObjectOutputStream sendObject, ObjectInputStream readObject)
	{
		super(readObject, sendObject);
	}
	
	/**
	 * Gets the list of courses the professor has from the database
	 * @param proffid - the ID of the professor
	 * @return the list of courses as a String array
	 */
	public String[] viewCourse(int proffid) 
	{
		Course course=new Course(proffid, null, -1, -1);
		String[] courselist=null;
		InfoExchange infoExchange=new InfoExchange("View Courses Proff");
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(course);
			flushAndReset(sendObject);
			infoExchange=(InfoExchange) readObject.readObject();
			courselist=infoExchange.getInfo();
		} catch (IOException e) {
			System.out.println("Error: browse course in proff model wont work");
		} catch (ClassNotFoundException e) {
			System.out.println("Error: browse course in proff model wont work");
		}
		return courselist;
	}
	
	/**
	 * Creates a course to the database
	 * @param proffid - the ID of the professor
	 * @param courseInfo - the course information
	 */
	public void createCourse(int proffid, String[] courseInfo)
	{
		Course course=new Course(proffid, courseInfo[0], Integer.parseInt(courseInfo[1]), -1);
		InfoExchange infoExchange=new InfoExchange("Create Course Proff");
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(course);
			flushAndReset(sendObject);
		} catch (IOException e) {
			System.out.print("Error: browse course in proff model wont work");
		}
	}
	
	/**
	 * Changes the active status of a course in the database
	 * @param course - the course information
	 */
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
	
	
	/**
	 * Gets a list of students in a course from the database
	 * @param courseid - the ID of the course
	 * @return the list of students in a String array
	 */
	public String[] viewStudents(int courseid)
	{
		StudentEnrollment sEnrollment=new StudentEnrollment(0, 0, courseid);
		InfoExchange infoExchange=new InfoExchange("View Students Proff");
		String[] result=null;
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(sEnrollment);
			flushAndReset(sendObject);
			infoExchange= (InfoExchange) readObject.readObject();
			result=infoExchange.getInfo();
		}
		catch (IOException e) {
			System.out.print("Error: search students in proff model wont work");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Searches for a student in a database and returns that student info
	 * @param lastname - the last name of the student
	 * @param courseid - the ID of the course to search from
	 * @return
	 */
	public String[] searchStudents(String lastname, int courseid)
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
			result[4]=studentEnrollment(Integer.parseInt(result[3]), courseid);
		}
		catch (IOException e) {
			System.out.print("Error: search students in proff model wont work");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Changes the enrollment status of a student in a course in the database
	 * @param studentid - the ID of the student
 	 * @param courseid - the ID of the course 
	 * @return The student enrollment status
	 */
	public String studentEnrollment(int studentid, int courseid)
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
	
	/**
	 * Adds an assignment to a course
	 * @param courseid - the course ID
	 * @param filename - the name of the file
	 * @param path - the path of the file
	 */
	public void addAssignment(int courseid, String filename, String path)
	{
		InfoExchange infoExchange=new InfoExchange("Upload Assignment");
		Assignment assignment=new Assignment(courseid, filename, path);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(assignment);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the assignments of a course from the database manager
	 * @param courseid - the course ID
	 * @return the list of assignments as a String array
	 */
	public String[] viewAssign(int courseid)
	{
		InfoExchange infoExchange=new InfoExchange("View Assignment Proff");
		Assignment assignment=new Assignment(courseid, null, null);
		String[] strings=null;
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(assignment);
			infoExchange= (InfoExchange) readObject.readObject();
			strings=infoExchange.getInfo();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (strings==null) {
			strings=new String[1];
			strings[0]="No student has submitted an assignment";
			return strings;
		}
		return strings;
	}
	
	/** 
	 * Sends a mass email to students
	 * @param emailInfo - the email details
	 */
	public void sendEmailToStudents(String[] emailInfo)
	{
		InfoExchange infoExchange=new InfoExchange("Send Email to all Students Enrolled in Course");
		infoExchange.setInfo(emailInfo);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Downloads all assignments from a course
	 * @param courseid - the course ID
	 */
	public void downloadAllAssignments(int courseid)
	{
		InfoExchange infoExchange=new InfoExchange("Download All Assignments into Proff Folder");
		Assignment assignment=new Assignment(courseid, null, null);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(assignment);
			flushAndReset(sendObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the grade of an assignment
	 * @param filename - the name of the assignment
	 * @param courseid - the course ID
	 * @param studentid - the student ID
	 * @param grade	- the grade to give
	 */
	public void setGradesForAssignment(String filename, int courseid, int studentid, int grade)
	{
		InfoExchange infoExchange=new InfoExchange("Mark Assignment-Proff");
		Grade gradeObject=new Grade(filename, studentid, courseid, grade);
		try {
			sendObject.writeObject(infoExchange);
			flushAndReset(sendObject);
			sendObject.writeObject(gradeObject);
			flushAndReset(sendObject);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Flushes and resets the send object
	 * @param sendObject
	 * @throws IOException
	 */
	private void flushAndReset(ObjectOutputStream sendObject) throws IOException {
		sendObject.flush();
		sendObject.reset();
	}
}
