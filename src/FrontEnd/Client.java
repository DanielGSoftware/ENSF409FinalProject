package FrontEnd;


import java.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	private LoginGUI login;
	private Socket socket;
	private ObjectOutputStream sendObject;
	private ObjectInputStream readObject;
	
	public Client(String host, int portnumber) throws UnknownHostException, IOException
	{
		socket=new Socket(host, portnumber);
		sendObject=new ObjectOutputStream(socket.getOutputStream());
		readObject=new ObjectInputStream(socket.getInputStream());
	}
	
	public void makeLoginGUI()
	{
		login = new LoginGUI(readObject, sendObject);
		login.setVisible(true);
	}
	
	private void makeProfessorGUI(String profffirstname, String profflastname, int proffid) throws IOException 
	{

		ProfessorModel proffmodel=new ProfessorModel(sendObject, readObject);
		ProfessorView proffview=new ProfessorView(proffid, profffirstname, profflastname);
		ProfessorControl proffcontrol=new ProfessorControl(proffmodel, proffview);
	}
	
	public static void main(String[] args)
	{
		try { 
			Client client=new Client("localhost", 9090);
//			client.makeLoginGUI();
			client.makeProfessorGUI("Winston", "DaGorilla", 1030);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
