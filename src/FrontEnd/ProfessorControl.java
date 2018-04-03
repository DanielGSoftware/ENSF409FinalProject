package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorControl {
	private ProfessorModel pModel;
	private ProfessorView pView;
	
	public ProfessorControl(ProfessorModel model, ProfessorView view) {
		pModel=model; 
		pView=view;
		pView.addCreateCourseListener(new CreateCourseListener());
		pView.addBrowseCourseListener(new BrowseCourseListener());
		pView.setVisible(true);
	}
	
	class CreateCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] strings=pView.createCourse();
			if (strings!=null) {
				//pModel.createCourse(strings);
			}
		}
	}
	
	class BrowseCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] courselist=pModel.browseCourse(pView.getProffID());
		}
		
	}
	
//	public static void main(String[] args)
//	{
//		ProfessorView proffview=new ProfessorView("Professor Learning Platform", 12345678, "Moussavi");
//		ProfessorControl user=new ProfessorControl(proffview);
//	}
}
