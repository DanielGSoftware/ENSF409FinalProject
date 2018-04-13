package SharedObjects;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Contains information to interect with studentent enrollment table in database
 * @author Huzaifa Amar and Daniel Guieb
 *
 */
public class StudentEnrollment implements Serializable{
	
	/**
	 * student id in table
	 */
	private int studentid;
	/**
	 * course id in table
	 */
	private int courseid;
	/**
	 * enrollment id in table
	 */
	private int enrollmentid;
	/**
	 * serializable id to allow transfer over socket
	 */
	private static final long serialVersionUID = 5;

	/**
	 * Construct student enrollment object with passed parameters
	 * @param enrollmentid of course
	 * @param studentid of student
	 * @param courseid of course 
	 */
	public StudentEnrollment(int enrollmentid, int studentid, int courseid) {
		this.enrollmentid=enrollmentid;
		this.studentid=studentid;
		this.courseid=courseid;
	}
	
	public int getStudentId()
	{
		return studentid;
	}
	
	/**
	 * view all students enrollent in a course
	 * @param table Student_Enrollment Table
	 * @param jdbc_connection connection to student_enrollment table
	 * @param statement to execute operations
	 * @return all students in a course
	 */
	public String[] viewStudentsEnrolled(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		System.out.println("in browse enrolled stuendts");
		String sql= "SELECT * FROM " +table+ " WHERE COURSE_ID=?";
		ResultSet object;
		String[] strings=new String[1];
		strings[0]="Student is not enrolled in this course";
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
			object=statement.executeQuery();
			while (object.next()) {
				if (object.getInt("STUDENT_ID")==studentid)
					strings[0]="Student is enrolled in this course";
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return strings;
	}
	
	/**
	 * delete a student from a course
	 * @param table Student_Enrollment Table
	 * @param jdbc_connection connection to student_enrollment table
	 * @param statement to execute operations
	 */
	public void deleteEnrollment(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql = "DELETE FROM " +table + " WHERE STUDENT_ID=? AND COURSE_ID=?";
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, studentid);
			statement.setInt(2, courseid);
			statement.executeUpdate();
			System.out.println("enrollment removed");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * get students id's of those enrollent in a specific course
	 * @param table
	 * @param jdbc_connection
	 * @param statement
	 * @return all student id's in a course
	 */
	public int[] viewStudents(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql = "SELECT * FROM " +table + " WHERE COURSE_ID=?";
		ResultSet object;
		ArrayList<Integer> list=new ArrayList<Integer>();
		try{
				statement = jdbc_connection.prepareStatement(sql);
				statement.setInt(1, courseid);
				object=statement.executeQuery();
				while (object.next()) {
					list.add(object.getInt("STUDENT_ID"));
				}
				//System.out.println("enrollment removed");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		int[] temp=new int[list.size()];
		for (int i=0; i<list.size(); i++) {
			temp[i]=list.get(i);
		}
		return temp;
	}
	
	/**
	 * get course id's of those enrollent in a specific course
	 * @param table
	 * @param jdbc_connection
	 * @param statement
	 * @return all course id's for a student
	 */
	public int[] viewCoursesForStudent(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql = "SELECT * FROM " +table + " WHERE STUDENT_ID = ?";
		ResultSet object;
		ArrayList<Integer> list=new ArrayList<Integer>();
		try{
				statement = jdbc_connection.prepareStatement(sql);
				statement.setInt(1, studentid);
				object=statement.executeQuery();
				while (object.next()) {
					list.add(object.getInt("COURSE_ID"));
				}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		int[] temp=new int[list.size()];
		for (int i=0; i<list.size(); i++) {
			temp[i]=list.get(i);
		}
		return temp;
	}
}
