package BackEnd;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class that accepts clients and provides client with a mean of connection to a pre-existing database
 * @author Huzaifa Amar and Daniel Guieb
 *
 */
public class Server {
	/**
	 * serversocket connection to accept connections from clients
	 */
	private ServerSocket serverSocket;
	/**
	 * threadpool to allow multiple clients to connect
	 */
	private ExecutorService threadpool;
	
	/**
	 * starts server
	 * @param portnumber port number to connect too
	 * @throws IOException if connection fails
	 */
	public Server(int portnumber) throws IOException
	{
		serverSocket=new ServerSocket(portnumber);
		threadpool=Executors.newCachedThreadPool();
	} 
	
	/**
	 * waits till client tries to connect; gives said client access to database
	 */
	public void communicate()
	{
		System.out.println("Server up and running");
		try {	
			while (true) {
				 DataBaseManager dataBaseManager=new DataBaseManager(serverSocket.accept());
				 threadpool.execute(dataBaseManager);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] main)
	{
		try {
			Server server=new Server(9090);
			server.communicate();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
