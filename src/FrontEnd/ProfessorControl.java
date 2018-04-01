package FrontEnd;

public class ProfessorControl extends Users{
	private ProfessorModel pModel;
	private ProfessorView pView;
	
	public ProfessorControl(ProfessorModel model, ProfessorView view) {
		pModel=model;
		pView=view;
		pView.setVisible(true);
	}
}
