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
		}
	}
	
	
}
