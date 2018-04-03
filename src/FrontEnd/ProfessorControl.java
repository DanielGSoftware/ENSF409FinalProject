package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorControl extends Users{
	private ProfessorModel pModel;
	private ProfessorView pView;
	
	public ProfessorControl(ProfessorView view) {
		//pModel=model; Add in the model parameter latter; taken out for testing
		pView=view;
		pView.addCreateCourseListener(new CreateCourseListener());
		pView.setVisible(true);
	}
	
	class  CreateCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] strings=pView.createCourse();
			if (strings!=null) {
				pModel.createCourse(strings);
			}
		}
	}
	
	public static void main(String[] args)
	{
		ProfessorView proffview=new ProfessorView("Professor Learning Platform", 12345678, "Moussavi");
		ProfessorControl user=new ProfessorControl(proffview);
	}
}
