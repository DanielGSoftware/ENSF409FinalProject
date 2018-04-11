package SharedObjects;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class Assignment implements Serializable {

	private int assignmentid;
	private int courseid;
	private String filename;
	private String path;
	private int active;
	private String date;
	private static final long serialVersionUID =5;

	public Assignment(int courseid, String filename, String path) {
		this.courseid=courseid;
		this.filename=filename;
		this.path=path;
		active=1;
		date="March 16, 2020";
	}

	public String[] searchAssignment(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		System.out.println("in find assignment");
		String sql= "SELECT * FROM " +table+ " WHERE COURSE_ID = ?";
		String[] temp=null;
		ResultSet object;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
			object=statement.executeQuery();
			if (object.next()) {
				temp=new String[3];
				temp[0]=object.getString("TITLE");
				temp[1]=object.getString("DUE_DATE");
				if (object.getInt("ACTIVE")==1)
					temp[2]="Assignment is currently visible to students";
				else
					temp[2]="Assignment is currently not visible to students";
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return temp;
	}
	
	public void addAssignments (String table, Connection jdbc_connection, PreparedStatement statement, int id)
	{
		String sql = "INSERT INTO " + table +
				" VALUES (?,?,?,?,?,?);";
		
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setInt(2, courseid);
			statement.setString(3, filename);
			statement.setString(4, path);
			statement.setInt(5, active);
			statement.setString(6,  date);
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public String[] downloadAssignmentStudent(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +table+ " WHERE COURSE_ID = ? AND TITLE = ?";
		String[] fileinfo=new String[2];
		ResultSet assignment;
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
			statement.setString(2, filename);
			assignment=statement.executeQuery();
			if (assignment.next()) {
				fileinfo[0]=assignment.getString("TITLE");
				fileinfo[1]=assignment.getString("PATH");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return fileinfo;
	}
	
	public String[] downloadAssignmentsProff(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +table+ " WHERE COURSE_ID = ?";
		ArrayList<String> fileinfo = new ArrayList<String>();
		ResultSet assignment;
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
			statement.setString(2, filename);
			assignment=statement.executeQuery();
			while (assignment.next()) {
				if (assignment.getString("PATH").contains("sendToProff"))
					fileinfo.add(assignment.getString("TITLE")+";"+assignment.getString("PATH"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		String[] temp=new String[fileinfo.size()];
		for (int i=0; i<fileinfo.size(); i++) {
			temp[i]=fileinfo.get(i);
		}
		return temp;
	}
}
