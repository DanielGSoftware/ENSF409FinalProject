package SharedObjects;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class that connects to and performs operations involving the grade table in databases
 * @author Huzaifa Amar and Daniel Guieb
 *
 */
public class Grade implements Serializable {

	/**
	 * name of assignment
	 */
	private String assignName;
	/**
	 * id of the assignment being marked
	 */
	private int studentId;
	/**
	 * student id of assignment being marked
	 */
	private int courseId;
	/**
	 * grade for an assignment
	 */
	private int assignmentGrade;
	/**
	 * serializable id of the class
	 */
	private static final long serialVersionUID = 3;
	
	/**
	 * 
	 * @param assignName name of assignment to be graded
	 * @param studentid id of student for corresponding 
	 * @param courseid course id of assignment being 
	 * @param assignmentGrade grade for assignment
	 */
	public Grade(String assignName, int studentid, int courseid, int assignmentGrade)
	{
		this.assignName=assignName;
		this.studentId=studentid;
		this.courseId=courseid;
		this.assignmentGrade=assignmentGrade;
	}
	
	/**
	 * inserting a grade for an assignment
	 * @param table Grade table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @param gradeid grade of assignment to be marked
	 */
	public void proffInsertingGrade(String gradetable, Connection jdbc_connection, PreparedStatement statement, int gradeid)
	{
		String sql = "INSERT INTO " + gradetable +
				" VALUES (?,?,?,?,?);";
		
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, gradeid);
			statement.setString(2, assignName);
			statement.setInt(3, studentId);
			statement.setInt(4, courseId);
			statement.setInt(5, assignmentGrade);
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * View student grades for a specific course
	 * @param table Grade table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return string array of info
	 */
	public String[] viewStudentGrade(String gradetable, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +gradetable+ " WHERE STUDENT_ID = ? AND COURSE_ID = ?";
		ResultSet object=null;
		String[] assignmentGrade=new String[1];
		System.out.println("Tryna get student grades");
		System.out.println(assignName);
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			object=statement.executeQuery();
			if (object.next()) {
				System.out.println("resultset object for viewing students grades has picked something up");
				assignmentGrade[0]=""+object.getInt("ASSIGNMENT_GRADE");
				System.out.println(assignmentGrade[0]);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return assignmentGrade;
	}
}
