package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentControl {
	
	private StudentModel sModel;
	private StudentView sView;
	
	public StudentControl(StudentModel model, StudentView view) {
		 sModel=model;
		 sView=view;
		 sView.addHomeListeners(new courseListener(), new GetCourseList());
		 sView.addCourseListeners(new courseListener(), new UploadAssignment(), new SendEmailToProff());
		 sView.setVisible(true);
		// sModel.downloadAssignment("TrialSendingToStudents.txt", 1070);
	}
	
	class courseListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			
		}
	}
	
	class GetCourseList implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] listofcourses=sModel.getCourseList(sView.getStudentID());
			sView.addCourses(listofcourses);
			sView.hideGetCourseButton();
		}
	}
	
	class SendEmailToProff implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			sModel.sendEmailToProff(sView.getCurrentCourseID(), sView.sendingMail());
			//make sure to open up a JOptionPane where we type in message.
			//email message should also have students id
			//read email message returns an array of strings
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
			File file=sView.chooseFile();
			//need course id and file path
			String filename=file.getName();
			String filepath=file.getAbsolutePath();
			sModel.uploadAssignment(sView.getCurrentCourseID(), filename, filepath);
		}
	}
	
	class ViewGradeForAssignment implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//paramters should be filename, courseid, studentid
			int assignmentGrade=sModel.viewGradeForAssignment();
			if (assignmentGrade==0) {
				System.out.println("Error: when tryna get student grades, "
						+ "database connection returned null");
			}
			else {
				//add it to studentview
			}
		}
	}
	
	class SelectedAssignment implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			String[] assignInfo = sView.getAssignmentInfo();
			//Where [1] is the assignment name
			int grade = sModel.getAssignGrade(assignInfo);
			sView.setGrade(grade);
		}
		
	}
	
}
