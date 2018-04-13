package SharedObjects;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The class that connects to and performs operations involving the assignment table in databases
 * @author Huzaifa Amar and Daniel Guieb
 *
 */
public class Assignment implements Serializable {

	/**
	 * course id of the assignment
	 */
	private int courseid;
	
	/**
	 * file name of the assignment
	 */
	private String filename;
	
	/**
	 * path of the assignment
	 */
	private String path;
	
	/**
	 * whether the assignment is active or not
	 */
	private int active;
	
	/**
	 * due date of the assignment
	 */
	private String date;
	
	/**
	 * serializable id of the assignemt
	 */
	private static final long serialVersionUID =5;

	/**
	 * takes information to fill the assignment class with; anything not used is given -1 or null
	 * @param courseid of the assignment
	 * @param filename of the assignment
	 * @param path of the assignment
	 */
	public Assignment(int courseid, String filename, String path) {
		this.courseid=courseid;
		this.filename=filename;
		this.path=path;
		active=1;
		date="March 16, 2020";
	}

	/**
	 * Search assignment table for assignments in proff folder
	 * @param table Assignment table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return string array of info
	 */
	public String[] searchAssignmentProff(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		System.out.println("in find assignment");
		String sql= "SELECT * FROM " +table+ " WHERE COURSE_ID = ?";
		String[] temp=null;
		ResultSet object=null;
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
		if (object==null) {
			return null;
		}
		return temp;
	}
	
	/**
	 * Adding assignments to assignment table
	 * @param table Assignment table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return
	 */
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
	
	/**
	 * Looking at assignment table to see which assignments are to  be downloaded into student folders
	 * @param table Assignment table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return string array of info
	 */
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
				if (assignment.getString("PATH").contains("sendToStudents")) {
					fileinfo[0]=assignment.getString("TITLE");
					fileinfo[1]=assignment.getString("PATH");
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return fileinfo;
	}
	
	/**
	 * Looking at assignment table to see which assignments are to  be downloaded into proff folders
	 * @param table Assignment table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return string array of info
	 */
	public String[] downloadAssignmentsProff(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +table+ " WHERE COURSE_ID = ?";
		ArrayList<String> fileinfo = new ArrayList<String>();
		ResultSet assignment;
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
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
	
	/**
	 * Getting list of all assignments with corresponding course id
	 * @param table Assignment table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return string array of info
	 */
	public String[] getAssignmentList(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +table+ " WHERE COURSE_ID = ?";
		ArrayList<String> assignmentList = new ArrayList<String>();
		ResultSet assignment;
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
			assignment=statement.executeQuery();
			while (assignment.next()) {
				if (assignment.getInt("ACTIVE")==1 && assignment.getString("PATH").contains("sendToStudents")) {
					assignmentList.add(assignment.getString("TITLE"));
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		if (assignmentList.isEmpty()) {
			return null;
		}
			
		String[] temp=new String[assignmentList.size()];
		for (int i=0; i<assignmentList.size(); i++) {
			temp[i]=assignmentList.get(i);			}
		return temp;
	}
}
