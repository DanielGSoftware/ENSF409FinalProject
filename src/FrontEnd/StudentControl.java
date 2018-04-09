package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;

import SharedObjects.User;

public class StudentControl {
	
	private StudentModel sModel;
	private StudentView sView;
	
	public StudentControl(StudentModel model, StudentView view) {
		sModel=model;
		sView=view;
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
			//current parameter value courseid
			sModel.sendEmailToProff(1070);
		}
	}
}
