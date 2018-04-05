package SharedObjects;

import java.io.Serializable;

public class User implements Serializable {
	private int id;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private char type;
	
	private static final long serialVersionUID = 7;
	
	public User(int id, String password, String email, String firstName, 
			String lastName) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
}
