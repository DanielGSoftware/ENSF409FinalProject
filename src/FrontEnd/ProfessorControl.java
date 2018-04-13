package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Provides data fields and methods to control how the professor GUI interacts
 * with the professor model
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 13, 2018
 */
public class ProfessorControl {
	/**
	 * The professor model which handles object reading and writing
	 */
	private ProfessorModel pModel;
	
	/**
	 * The professor GUi which handles what the professor sees
	 */
	private ProfessorView pView;
	
	
	/**
	 * A constructor which sets the model and GUI to the incoming model
	 * and view. Also adds listeners to the buttons in the GUI and sets the
	 * GUI visible
	 * @param model - the model to use
	 * @param view - the view to use
	 */
	public ProfessorControl(ProfessorModel model, ProfessorView view) {
		pModel=model; 
		pView=view;
		pView.addHomeListeners(new CreateCourseListener(), 
				new ViewCourseListener(), new CourseListListener());
		pView.setVisible(true);
	}
	
	/**
	 * An action listener for the create course button in the professor GUI.
	 * When pressed, the model will create a course in the database
	 */
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
	
	/**
	 * An action listener for the email student button in the professor GUI.
	 * When pressed, the professor will enter an email in the view which will
	 * be sent through the model to the students
	 *
	 */
	class EmailStudentListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] emailInfo=pView.sendingMail();
			pModel.sendEmailToStudents(emailInfo);
		}
	}
	
	/**
	 * An action listener for the view students button in the professor GUI.
	 * When pressed, the view will change to see a list of students provided by
	 * the model
	 *
	 */
	class ViewStudentsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] students=pModel.viewStudents(pView.getCourseID());
			pView.updateStudentListDisplay(students);
			pView.viewStudentsPage();
		}
	}
	
	/**
	 * An action listener for the view assignments button in the professor GUI.
	 * When pressed, the view will change to see a list of assignments provided by 
	 * the model
	 *
	 */
	class ViewAssignmentsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] assignments=pModel.viewAssign(pView.getCourseID());
			pView.updateAssignListDisplay(assignments);
			pView.viewAssignsPage();
		}
	}
	
	/**
	 * An action listener for the upload assignment button in the professor GUI.
	 * When pressed, the view will get a file and the model will add it onto the 
	 * database
	 *
	 */
	class UploadAssignmentListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			File file=pView.chooseFile();
			System.out.println(file.getPath());
			pModel.addAssignment(pView.getCourseID(), file.getName(), file.getPath());
		}
		
	}
	
	class AssignmentActiveStatusListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * An action listener for the view submissions listener in the professor GUI.
	 * When pressed, the model will download all assignments based on the course
	 * provided by the view
	 *
	 */
	class ViewSubmissionsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			pModel.downloadAllAssignments(pView.getCourseID());
		}
	}
	
	/**
	 * An action listener for the return home button in the professor GUI.
	 * When pressed, will change the view to see the Home page
	 */
	class ReturnHomeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			pView.viewHomePage();
			
		}
		
	}
	
	/**
	 * An action listener for the view courses buttin in the professor GUi.
	 * When pressed, will fill the course list in Home page in the view
	 *
	 */
	class ViewCourseListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] courselist=pModel.viewCourse(pView.getProffID());
			pView.createCourseJList(courselist);
		}
		
	}
	
	/**
	 * A list selection listener for the course list on the home page in the 
	 * professor GUI. When pressed, will change the current course id, create the Course 
	 * page, add Course page listeners, and then change the view to the Course page
	 */
	class CourseListListener implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			pView.setCurrentCourseID(Integer.parseInt(pView.getCourseInfo()[0]));
			pView.createCourseDisplay(pView.getCourseInfo());
			pView.addCourseListeners(new CourseActiveStatus(), new ViewStudentsListener(),
					new ViewAssignmentsListener(), new ReturnHomeListener(), 
					new EmailStudentListener(), new SearchStudentsListener(), 
					new StudentEnrollmentListener(), new UploadAssignmentListener(), 
					new AssignmentActiveStatusListener(), new ViewSubmissionsListener());
			pView.viewCoursesPage();
		}
		
	}
	
	/**
	 * An action listener for the course active status button in the professor GUI.
	 * When pressed, the model will change the course active status of the current 
	 * class, which is determined by the view
	 */
	class CourseActiveStatus implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("courseactivestatus button hit in control class");
			String[] courseInfo = pView.getCourseInfo();
			pModel.courseActive(courseInfo);
			pView.simpleMessage("Course Activation Status Sucessfully Changed. "
								+ "Please hit browse courses to see changes");
		}
	}

	/**
	 * An action listener for the search student button in the professor GUI.
	 * When pressed, the model will search for students in the course based on the
	 * information provided by the view, and then update the student list in the 
	 * Course page
	 *
	 */
	class SearchStudentsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] courseInfo = pView.getCourseInfo();
			int courseid=Integer.parseInt(courseInfo[0]);
			String[] strings=pModel.searchStudents(pView.getSearchParam(), courseid);
			pView.updateStudentListDisplay(strings);
		}
	}
	
	class StudentEnrollmentListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] string=new String[1];
			string[0]=pModel.studentEnrollment(1000,1070);
			pView.updateStudentListDisplay(string);
		}
	}
	
	class SetGradesForAssignment implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//get assignment name from view
			//parameter should be file name, course id, studentid, and the grade
			pModel.setGradesForAssignment("Assignment1.txt", 1070, 1000, 87);
		}
	}
}
