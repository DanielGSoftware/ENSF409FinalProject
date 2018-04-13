package SharedObjects;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

/**
* The class that connects to and performs operations involving the course table in databases
* @author Huzaifa Amar and Daniel Guieb
*
*/
public class Course implements Serializable{
	/**
	 * proff if of the course object
	 */
	private int proffid;
	/**
	 * name of the course object
	 */
	private String name;
	/**
	 * if course is active or not
	 */
	private int active;
	/**
	 * courseid of the course object
	 */
	private int courseid;
	/**
	 * serializable id of the course class
	 */
	private static final long serialVersionUID = 2;

	/**
	 * creates a course object with the given parameters
	 * @param proffid of course
	 * @param name of course
	 * @param active if course is visible to students or not
	 * @param courseid of the course
	 */
	public Course(int proffid, String name, int active, int courseid)
	{
		this.proffid=proffid;
		this.name=name;
		this.active=active;
		this.courseid=courseid;
	}
	
	/**
	 * Search for courses by proff id
	 * @param table Course table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return string array of info
	 */
	public String[] browseCourses(String coursetable, Connection jdbc_connection, PreparedStatement statement)
	{
		System.out.println("in browse courses");
		String sql= "SELECT * FROM " +coursetable+ " WHERE PROFF_ID=?";
		ArrayList<String> listofcoures=new ArrayList<String>();
		ResultSet course;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setInt(1, proffid);
			course=statement.executeQuery();
			while (course.next()) {
				String courseid= String.valueOf(course.getInt("COURSE_ID"));
				if (course.getInt("ACTIVE")==1)
					listofcoures.add(courseid+";"+course.getString("NAME")+";Currently Active to Students");
				else 
					listofcoures.add(courseid+";"+course.getString("NAME")+";Currently Inactive to Students");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		String temp[]=new String[listofcoures.size()];
		for (int i=0; i<listofcoures.size(); i++) {
			temp[i]=listofcoures.get(i);
		}
		
		for (int i=0; i<listofcoures.size(); i++) {
			System.out.println(listofcoures.get(i));
		}
		
		return temp;
	}
	
	/**
	 * create a new course, either active or inactive
	 * @param table Course table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 */
	public void createCourse(String coursetable, Connection jdbc_connection, PreparedStatement statement, int id)
	{
		String sql = "INSERT INTO " + coursetable +
				" VALUES (?,?,?,?);";
		
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setInt(2, proffid);
			statement.setString(3, name);
			statement.setInt(4, active);
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}	
	}
	
	/**
	 * change course activation status of a specific course
	 * @param table Course table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 */
	public void courseActivationStatus(String coursetable, Connection jdbc_connection, PreparedStatement statement)
	{
		System.out.println("CHanging course active status");
		String sql = "UPDATE "+coursetable+ " SET ACTIVE = ? WHERE COURSE_ID = ?";
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, active);
			statement.setInt(2, courseid);
			statement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * getting a students courses
	 * @param table Course table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return the course if its active, null otherwise
	 */
	public String courseofStudent(String coursetable, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +coursetable+ " WHERE COURSE_ID = ?";
		String course=null;
		ResultSet object;
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
			object=statement.executeQuery();		
			if (object.next()) {
				if (object.getInt("ACTIVE")==1) {
					course=courseid+";"+object.getString("NAME");
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return course;
	}
	
	/**
	 * getting the proff id of a specific course
	 * @param table Course table to connect to
	 * @param jdbc_connection java connection that links to the database
	 * @param statement execute commands within database
	 * @return the proffs id 
	 */
	public int getProffID(String coursetable, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +coursetable+ " WHERE COURSE_ID = ? AND ACTIVE = ?";
		int proffif=0;
		ResultSet object;
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
			statement.setInt(2, active);
			object=statement.executeQuery();	
			if (object.next())
				proffif=object.getInt("PROFF_ID");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return proffif;
	}

}
