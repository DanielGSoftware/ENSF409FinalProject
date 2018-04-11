package SharedObjects;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
