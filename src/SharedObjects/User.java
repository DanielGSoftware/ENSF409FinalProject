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
		System.out.println("in browse users");
		String sql= "SELECT * FROM " +table+ " WHERE FIRSTNAME = ? AND LASTNAME = ?";
		String[] temp=null;
		ResultSet object;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			object=statement.executeQuery();
			if (object.next()) {
				temp=new String[3];
				temp[0]=object.getString("FIRSTNAME");
				temp[1]=object.getString("LASTNAME");
				int idfromtable=object.getInt("USER_ID");
				temp[2]=Integer.toString(idfromtable);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}		
		return temp;
	}
	
	
	public String[] searchStudents(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +table+ " WHERE LASTNAME = ? AND TYPE = ?";
		String[] temp=null;
		ResultSet object;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setString(1, lastName);
			statement.setString(2, type);
			object=statement.executeQuery();
			if (object.next()) {
				temp=new String[5];
				int userid=object.getInt("USER_ID");
				temp[0]="Students user id is: "+userid;
				//temp[1]=object.getString("PASSWORD");
				temp[1]="Students email is: "+object.getString("EMAIL");
				temp[2]="Students name is: "+object.getString("FIRSTNAME")+" "+object.getString("LASTNAME");
				temp[3]= "" + userid;
				//temp[4]=object.getString("LASTNAME");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(temp[0]);
		System.out.println(temp[1]);
		return temp;
	}
}
