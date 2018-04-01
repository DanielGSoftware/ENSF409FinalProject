package FrontEnd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	private ObjectOutputStream sendObject;
	private ObjectInputStream readObject;
	private Users user;
	
	public Client(String host, int portnumber) throws UnknownHostException, IOException
	{
		socket=new Socket(host, portnumber);
		sendObject=new ObjectOutputStream(socket.getOutputStream());
		readObject=new ObjectInputStream(socket.getInputStream());
	}
	
	public void makeLoginGUI()
	{
		
	}
	
	private void makeProfessorGUI() 
	{
		ProfessorModel proffmodel=new ProfessorModel(sendObject, readObject);
		ProfessorView proffview=new ProfessorView("Professor Learning Platform");
		user=new ProfessorControl(proffmodel, proffview);
	}
	
	public static void main(String[] args)
	{
		try {
			Client client=new Client("localhost", 9090);
			client.makeLoginGUI();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
