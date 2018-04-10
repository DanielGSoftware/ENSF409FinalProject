package BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileHandler {

	private String profffolder;
	private String studentfolder;
	private String fileToDownloadName;
	private String fileToDownloadPath;
	
	public FileHandler(String name, String filepath)
	{
		profffolder="C:\\Users\\muham\\OneDrive\\Documents\\ENSF409ProffFiles\\recievedFromStudents";
		studentfolder="C:\\Users\\muham\\OneDrive\\Documents\\ENSF409StudentFiles\\recievedFromProff";
		fileToDownloadName=name;
		fileToDownloadPath=filepath;
	}
	
	public void downloadAssignmentToStudent()
	{
		try {
			File toRead=new File(fileToDownloadPath);
			FileReader fileReader=new FileReader(toRead);
			BufferedReader reader=new BufferedReader(fileReader);
			File toDownload=new File(studentfolder, fileToDownloadName);
			toDownload.createNewFile();
			PrintWriter writer=new PrintWriter(toDownload);
			String readline=reader.readLine();
			while (readline!=null) {
				writer.println(readline);
				readline=reader.readLine();
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void downloadAssignmentsToProff()
	{
		
	}
}
