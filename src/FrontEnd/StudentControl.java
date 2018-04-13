package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentControl {
	
	private StudentModel sModel;
	private StudentView sView;
	
	public StudentControl(StudentModel model, StudentView view) {
		 sModel=model;
		 sView=view;
		 sView.addHomeListeners(new SelectCourse(), new GetCourseList());
		 sView.setVisible(true);
	}
		
	class SelectCourse implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			String[] courseinfo=sView.getCourseSelected();
			int courseid=Integer.parseInt(courseinfo[0]);
			String[] assignmentList=sModel.getAssignmentList(courseid);
			System.out.println(assignmentList[0]);
			sView.setCurrentCourseID(Integer.parseInt(courseinfo[0]));
			sView.createCourseDisplay(courseinfo, assignmentList);
			sView.addCourseListeners(new SelectAssignment(), new DownLoadAssignment(), new UploadAssignment(), new SendEmailToProff(),
					new ReturnHome());
			sView.goCoursePage();
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
		}
	}
	
	class DownLoadAssignment implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//file's name should be picked up and sent 
			//perhaps file path as well?
			sModel.downloadAssignment(sView.getAssignmentName(), sView.getCurrentCourseID());
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
	
	class SelectAssignment implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			String assignName = sView.getAssignmentName();
			int assignmentGrade=sModel.viewGradeForAssignment(assignName, 
					sView.getCurrentCourseID(), sView.getStudentID());
			sView.setGrade(assignmentGrade);
			}
	}
	
	class ReturnHome implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			sView.goHome();
		}
	}
	
}
