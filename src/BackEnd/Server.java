package BackEnd;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private DataBaseManager dataBaseManager;
	private ServerSocket serverSocket;
	
	public Server(int portnumber) throws IOException
	{
		serverSocket=new ServerSocket(portnumber);
		dataBaseManager=new DataBaseManager(serverSocket);
	}
	
	public static void main (String[] main)
	{
		try {
			Server server=new Server(9090);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
