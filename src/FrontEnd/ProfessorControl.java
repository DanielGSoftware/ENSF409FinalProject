package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import SharedObjects.Course;

public class ProfessorControl {
	private ProfessorModel pModel;
	private ProfessorView pView;
	
	
	public ProfessorControl(ProfessorModel model, ProfessorView view) {
		pModel=model; 
		pView=view;
		pView.addCreateCourseListener(new CreateCourseListener());
		pView.addBrowseCourseListener(new BrowseCourseListener());
		//pView.addCourseActiveListener(new CourseActiveStatus());
		//pView.addListListener(new ListListener());
		pView.addSearchStudentsListener(new SearchStudentsListener());
		pView.addEnrollStudentListener(new StudentEnrollmentListener());
		pView.setVisible(true);
	}
	
	class CreateCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] strings=pView.createCourse();
			if (strings!=null) {
				pModel.createCourse(pView.getProffID(), strings);
			}
		}
	}
	
	class BrowseCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] courselist=pModel.browseCourse(pView.getProffID());
			pView.updateDisplay(courselist);
		}
		
	}
	
//	class CourseActiveStatus implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("courseactivestatus button hit in control class");
//			String[] course=pView.getSelectedList();
//			pModel.courseActive(course);
//			pView.displayCourseActiveUpdateMessage();
//		}
//	}
	
//	class ListListener implements ListSelectionListener
//	{
//		@Override
//		public void valueChanged(ListSelectionEvent e) {
//			System.out.println("in list acton listener");
//			pView.setCoursePage();
//		}

	class CourseActiveStatus implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("courseactivestatus button hit in control class");
//			String[] course=pView.getSelectedList();
//			pModel.courseActive(course);
//			pView.displayCourseActiveUpdateMessage();
		}
	}
	
	class ListListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			System.out.println("in list acton listener");
//			pView.setCoursePage();
		}
	}

	
	class SearchStudentsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//add to search students button
			System.out.println("Searching for McCree");
			//some JOptionPane gives  student id and course id
			String[] strings=pModel.SearchStudents("Peter", 1070);
			//display information in gui 
			System.out.println(strings[0]);
			pView.updateDisplay(strings);
		}
	}
	
	class StudentEnrollmentListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//picks up what student is hit
			String[] string=new String[1];
			string[0]=pModel.StudentEnrollment(1000,1070);
			pView.updateDisplay(string);
		}
	}
}
