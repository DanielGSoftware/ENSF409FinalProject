package SharedObjects;
/**
 * Huzaifa Amar and Daniel Guieb
 */
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Array;

public class StudentEnrollment implements Serializable{
	private int studentid;
	private int courseid;
	private int enrollmentid;
	private static final long serialVersionUID = 5;

	public StudentEnrollment(int enrollmentid, int studentid, int courseid) {
		this.enrollmentid=enrollmentid;
		this.studentid=studentid;
		this.courseid=courseid;
	}
	
	public int getStudentId()
	{
		return studentid;
	}
	
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
}
