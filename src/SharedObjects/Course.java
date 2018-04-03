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
					listofcoures.add(course.getString("NAME")+", Currently UnActive to Students");
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

}
