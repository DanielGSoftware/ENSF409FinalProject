package BackEnd;
/**
 * Huzaifa Amar and Daniel Guieb
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private ServerSocket serverSocket;
	private ExecutorService threadpool;
	
	public Server(int portnumber) throws IOException
	{
		serverSocket=new ServerSocket(portnumber);
		threadpool=Executors.newCachedThreadPool();
	} 
	
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
