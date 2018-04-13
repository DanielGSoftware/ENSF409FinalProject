package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Provides the data fields and methods to control how the student GUI interacts 
 * with the student model
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 13, 2018
 *
 */
public class StudentControl {
	/**
	 * The student model which handles object reading and writing
	 */
	private StudentModel sModel;
	
	/**
	 * The professor GUI which handles what the student sees
	 */
	private StudentView sView;
	
	/**
	 * A constructor which sets the model and GUI to the incoming model and view.
	 * Also adds listeners to the buttons in the GUI and sets the GUI visible
	 * @param model - the model to use
	 * @param view - the view to use
	 */
	public StudentControl(StudentModel model, StudentView view) {
		 sModel=model;
		 sView=view;
		 sView.addHomeListeners(new SelectCourse(), new GetCourseList());
		 sView.setVisible(true);
	}
		
	/**
	 * A list selection listener for the course list in the student GUI.
	 * When pressed, the view will create a course display from the info on the 
	 * course selected. This is done by the model getting the assignment info.
	 * Also, adds listeners to the buttons in the Course page
	 *
	 */
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
			sView.addCourseListeners(new SelectAssignment(), new DownLoadAssignment(), 
					new UploadAssignment(), new SendEmailToProff(), new ReturnHome());
			sView.goCoursePage();
		}
	}
	
	/**
	 * An action listener for the get course button in the student GUI.
	 * When pressed, the course list in the view will be updated to the courses that the
	 * model finds the student to be in. Hides the button after use
	 *
	 */
	class GetCourseList implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] listofcourses=sModel.getCourseList(sView.getStudentID());
			sView.addCourses(listofcourses);
			sView.hideGetCourseButton();
		}
	}
	
	/**
	 * An action listener for the send email button in the student GUI.
	 * When pressed, the model will send an email that was provided by the view
	 *
	 */
	class SendEmailToProff implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			sModel.sendEmailToProff(sView.getCurrentCourseID(), sView.sendingMail());
		}
	}
	
	/**
	 * An action listener for the download assignment button in the student GUI.
	 * When pressed, the model will download the assignments based on the assignment
	 * selected in the view
	 *
	 */
	class DownLoadAssignment implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			sModel.downloadAssignment(sView.getAssignmentName(), sView.getCurrentCourseID());
		}
	}
	
	/**
	 * An action listener for the upload assignment button in the student GUI.
	 * When pressed, the model will upload a file chosen by the view to the database
	 *
	 */
	class UploadAssignment implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			File file=sView.chooseFile();
			String filename=file.getName();
			String filepath=file.getAbsolutePath();
			sModel.uploadAssignment(sView.getCurrentCourseID(), filename, filepath);
		}
	}
	
	/**
	 * An list selection listener for the assignment list in the student GUI. 
	 * When pressed, the model will get the grade of the assignment selected
	 * and update it to the view
	 *
	 */
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
	
	/**
	 * An action listener for the return home button in the student GUI.
	 * When pressed, the view will show the Home page
	 *
	 */
	class ReturnHome implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			sView.goHomePage();
		}
	}
	
}
