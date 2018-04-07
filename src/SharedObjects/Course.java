package SharedObjects;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class Course implements Serializable{
	private int proffid;
	private String name;
	private int active;
	private int courseid;
	private static final long serialVersionUID = 2;

	public Course(int proffid, String name, int active, int courseid)
	{
		this.proffid=proffid;
		this.name=name;
		this.active=active;
		this.courseid=courseid;
	}
	
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
	
	public String courseofStudent(String coursetable, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +coursetable+ " WHERE COURSE__ID = ?";
		String course=null;
		ResultSet object;
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, courseid);
			object=statement.executeQuery();		
			if (object.getInt("ACTIVE")==1) {
				course=""+courseid+";"+object.getString("NAME");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return course;
	}

}
