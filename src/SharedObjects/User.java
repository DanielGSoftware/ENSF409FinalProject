package SharedObjects;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.table.TableModel;

import BackEnd.EmailHandler;

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
				temp=new String[4];
				if (object.getString("TYPE").equals("S"))
					temp[0]="STUDENT";
				else
					temp[0]="PROFF";
				temp[1]=object.getString("FIRSTNAME");
				temp[2]=object.getString("LASTNAME");
				int idfromtable=object.getInt("USER_ID");
				temp[3]=Integer.toString(idfromtable);
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
				temp[3]= ""+userid; 
				temp[4]="";
				//temp[4]=object.getString("LASTNAME");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println(temp[0]);
//		System.out.println(temp[1]);
		return temp;
	}
	
	public String[] searchStudentsUserId(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +table+ " WHERE USER_ID = ? AND TYPE = ?";
		String[] temp=null;
		ResultSet object;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, type);
			object=statement.executeQuery();
			if (object.next()) {
				temp=new String[5];
				int userid=object.getInt("USER_ID");
				temp[0]="Students user id is: "+userid;
				//temp[1]=object.getString("PASSWORD");
				temp[1]="Students email is: "+object.getString("EMAIL");
				temp[2]="Students name is: "+object.getString("FIRSTNAME")+" "+object.getString("LASTNAME");
				temp[3]= ""+userid; 
				temp[4]="";
				//temp[4]=object.getString("LASTNAME");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println(temp[0]);
//		System.out.println(temp[1]);
		return temp;
	}
	
	public String[] getEmailInfoStudent(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +table+ " WHERE USER_ID = ? AND TYPE = ?";
		String[] emailinfo=new String[2];
		ResultSet object;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, type);
			object=statement.executeQuery();
			if (object.next()) {
				emailinfo[0]=object.getString("EMAIL");
				emailinfo[1]=object.getString("EMAILPASSWORD");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return emailinfo;
	}
	
	public String[] getEmailInfoProff(String table, Connection jdbc_connection, PreparedStatement statement)
	{
		String sql= "SELECT * FROM " +table+ " WHERE USER_ID = ? AND TYPE = ?";
		String[] emailinfo=new String[2];
		ResultSet object;
		try {
			statement=jdbc_connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, type);
			object=statement.executeQuery();
			if (object.next()) {
				emailinfo[0]=object.getString("EMAIL");
				emailinfo[1]=object.getString("EMAILPASSWORD");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return emailinfo;
	}
}
