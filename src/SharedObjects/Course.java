package SharedObjects;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class Course implements Serializable {
	private int proffid;
	private String name;
	private int active;
	private static final long serialVersionUID = 1;

	public Course(int proffid, String name, int active)
	{
		this.proffid=proffid;
		this.name=name;
		this.active=active;
	}
	
	public String[] browseCourses(String coursetable, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +coursetable+ " WHERE PROFF_ID=?";
		ArrayList<String> listofcoures=new ArrayList<String>();
		ResultSet course;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setInt(1, proffid);
			course=statement.executeQuery();
			while (course.next()) {
				if (course.getInt("ACTIVE")==1)
					listofcoures.add(course.getString("NAME")+", Currently Active to Students");
				else 
					listofcoures.add(course.getString("NAME")+", Currently Inactive to Students");
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
		return temp;
	}
	
	public void createCourse(String coursetable, Connection jdbc_connection, PreparedStatement statement, String id)
	{
		String sql = "INSERT INTO " + coursetable +
				" VALUES ( ?, ?, ?, ?)";
		try{
			statement = jdbc_connection.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(id));
			statement.setInt(2, proffid);
			statement.setString(3, name);
			statement.setInt(4, active);
			statement.executeUpdate();
			id=Integer.toString(Integer.parseInt(id)+10);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

}
