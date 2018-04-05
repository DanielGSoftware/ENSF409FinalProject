package SharedObjects;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User implements Serializable {
	private int id;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String type;
	private static final long serialVersionUID = 7;
	
	public User(int id, String password, String email, String firstName, String lastName, String type) 
	{
		this.id = id;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.type=type;
	}
	
	public String[] findUser(String table, Connection jdbc_connection, PreparedStatement statement)
	{ 
		System.out.println("in browse courses");
		String sql= "SELECT * FROM " +table+ " WHERE FIRSTNAME = ? AND LASTNAME = ? AND TYPE = ?";
		//ArrayList<String> list=new ArrayList<String>();
		String[] temp=new String[3];
		ResultSet object;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, type);
			object=statement.executeQuery();
			//list.add(courseid+";"+object.getString("NAME")+";Currently Active to Students");
			temp[0]=object.getString("FIRSTNAME");
			temp[1]=object.getString("LASTNAME");
			int idfromtable=object.getInt("USER_ID");
			temp[2]=Integer.toString(idfromtable);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return temp;
	}
	
}
