package BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Dealing with file download and upload. Contains information to connect to 2 specific folders
 * @author Huzaifa Amar and Daniel Guieb
 *
 */
public class FileHandler {

	/**
	 * the path to the profffolder
	 */
	private String profffolder;
	
	/**
	 * path to the student folder
	 */
	private String studentfolder;
	
	/**
	 * the name of the file were performation operations on
	 */
	private String fileToDownloadName;
	
	/**
	 * the path of the file were performing operations on
	 */
	private String fileToDownloadPath;
	
	/**
	 * takes name and path of the file to download
	 * @param name of file in question
	 * @param filepath of the file in question
	 */
	public FileHandler(String name, String filepath)
	{
		profffolder="C:\\Users\\muham\\OneDrive\\Documents\\ENSF409ProffFiles\\recievedFromStudents";
		studentfolder="C:\\Users\\muham\\OneDrive\\Documents\\ENSF409StudentFiles\\recievedFromProff";
		fileToDownloadName=name;
		fileToDownloadPath=filepath;
	}
	
	/**
	 * downloads a file to the student folder
	 */
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
	
	/**
	 * downloads an assignment to the proff's folder
	 */
	public void downloadAssignmentsToProff()
	{
		try {
			File toRead=new File(fileToDownloadPath);
			FileReader fileReader=new FileReader(toRead);
			BufferedReader reader=new BufferedReader(fileReader);
			File toDownload=new File(profffolder, fileToDownloadName);
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
}
