package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Action;

import SharedObjects.User;

public class StudentControl {
	
	private StudentModel sModel;
	private StudentView sView;
	
	public StudentControl(StudentModel model, StudentView view) {
		 sModel=model;
		 sView=view;
		 //view.addcourses()
		 sView.setVisible(true);
		// sModel.downloadAssignment("TrialSendingToStudents.txt", 1070);
	}
	
	class GetCourseList implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] listofcourses=sModel.getCourseList(sView.getStudentID());
			//ADD TO VIEW!
		}
	}
	
	class SendEmailToProff implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//sModel.sendEmailToProff(sView.getCourseID(), sView.readEmailMessage());
			//make sure to open up a JOptionPane where we type in message.
			//email message should also have students id
			//read email message returns an array of strings
			sModel.sendEmailToProff();
		}
	}
	
	class DownLoadAssignment implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//file's name should be picked up and sent 
			//perhaps file path as well?
			sModel.downloadAssignment("Assignment1.txt", 1070);
		}
	}
	
	class UploadAssignment implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//File file=sView.chooseFile();
			//need course id and file path
			sModel.uploadAssignment(1070, "trialassignmenttosendtoproff.txt", 
					"C:\\Users\\muham\\OneDrive\\Documents\\ENSF409StudentFiles\\sendToProff\\trialassignmenttosendtoproff.txt");
		}
	}
	
}
