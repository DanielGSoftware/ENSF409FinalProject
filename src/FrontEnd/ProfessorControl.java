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
		pView.addCourseActiveListener(new CourseActiveStatus());
		pView.addListListener(new ListListener());
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
	
	class CourseActiveStatus implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("courseactivestatus button hit in control class");
			String[] course=pView.getSelectedList();
			pModel.courseActive(course);
			pView.displayCourseActiveUpdateMessage();
		}
	}
	
	class ListListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			pView.setCoursePage();
		}
	}
	
//	public static void main(String[] args)
//	{
//		ProfessorView proffview=new ProfessorView("Professor Learning Platform", 12345678, "Moussavi");
//		ProfessorControl user=new ProfessorControl(proffview);
//	}
}
