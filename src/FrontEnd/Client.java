package FrontEnd;

/**
 * Huzaifa Amar and Daniel Guieb
 */
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	private LoginGUI loginView;
	private LoginModel loginModel;
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
		loginView = new LoginGUI(readObject, sendObject);
		loginView.addSignInActionListener(new SignInListener());
		loginModel=new LoginModel(readObject, sendObject);
		loginView.setVisible(true); 
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
			client.makeLoginGUI();
			//client.makeProfessorGUI("Winston", "DaGorilla", 1030);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public class SignInListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("send buytton presed");
			String[] string=loginModel.loginAttempt(loginView.getUser(), loginView.getPass());
			if (string!=null) {
				loginView.setVisible(false);
				try {
					makeProfessorGUI(string[0], string[1], Integer.parseInt(string[2]));
				} 
				catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				}
			}
				
			else {
				loginView.sendError("Incorrect Login Information: User does not exist in database");
			}
		}
	}
}
