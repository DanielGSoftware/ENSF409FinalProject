package SharedObjects;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Grade implements Serializable {

	private String assignName;
	private int studentId;
	private int courseId;
	private int assignmentGrade;
	private static final long serialVersionUID = 3;
	
	public Grade(String assignName, int studentid, int courseid, int assignmentGrade)
	{
		this.assignName=assignName;
		this.studentId=studentid;
		this.courseId=courseid;
		this.assignmentGrade=assignmentGrade;
	}
	
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
	
	public String[] viewStudentGrade(String gradetable, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +gradetable+ " WHERE ASSIGNMENT_NAME = ? AND STUDENT_ID = ? AND COURSE_ID = ?";
		ResultSet object=null;
		String[] assignmentGrade=new String[1];
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setString(1, assignName);
			statement.setInt(2, studentId);
			statement.setInt(3, courseId);
			object=statement.executeQuery();
			if (object.next()) {
				assignmentGrade[0]=""+object.getInt("ASSIGNMENT_GRADE");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return assignmentGrade;
	}
}
