package FrontEnd;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

/**
 * Provides the data fields and methods to create a GUI for a professor.
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 11, 2018
 *
 */
public class ProfessorView extends JFrame implements OurStyle{
	/**
	 * The professor's first name
	 */
	private String proffFirstName;
	
	/**
	 * The professor's last name
	 */
	private String proffLastName;
	
	/**
	 * The professor's ID
	 */
	private int proffID;
	
	/**
	 * The ID of the current working course
	 */
	private int currentCourseID;
	
	/**
	 * A card layout for the Home and Course pages
	 */
	private CardLayout mainCards;
	
	/**
	 * A container which holds all GUI components
	 */
	private Container container;
	
	/**
	 * A panel which holds all components related to the Home page
	 */
	private JPanel homePanel;
	
	/**
	 * A Home page button to create a course
	 */
	private JButton createCourses;
	
	/**
	 * A Home page button to view courses
	 */
	private JButton viewCourses;
	
	/**
	 * A Home page scroll pane to hold the course list
	 */
	private JScrollPane courseScrollPane;
	
	/**
	 * A Home page list model for courses which is held by a list
	 */
	private DefaultListModel<String> courseListModel;
	
	/**
	 * A Home page list for courses which holds a list model
	 */
	private JList<String> courseJList;
	
	/**
	 * A panel which holds all components related to the Course page
	 */
	private JPanel coursePanel;
	
	/**
	 * A Course page button which toggles the course between active and
	 * inactive
	 */
	private JButton setCourseactive;
	
	/**
	 * A Course page button to view students enrolled in the course
	 */
	private JButton viewStudents;
	
	/**
	 * A Course page button to view course assignments
	 */
	private JButton viewAssigns;
	
	/**
	 * A Course page button to return to the Home page
	 */
	private JButton returnHome;
	
	/**
	 * A Course page inner panel which holds the Students and Assignments 
	 * inner pages
	 */
	private JPanel courseInnerPanel;
	
	/**
	 * A Course page card layout for the Students and Assignments pages
	 */
	private CardLayout courseInnerCards;
	
	/**
	 * A Course page, Student page list model for the students which is held by a 
	 * list 
	 */
	private DefaultListModel<String> studentListModel;
	
	/**
	 * A Course page, Student page list for the students which holds a list model
	 */
	private JList<String> studentJList;
	
	/**
	 * A Course page, Student page button to mass email students
	 */
	private JButton emailStudents;
	
	/**
	 * A Course page, Student page button to search for a student by name
	 */
	private JButton searchStudents;
	
	/**
	 * A Course page, Student page button to change the enrollment status of a student
	 */
	private JButton enrollment;
	
	/**
	 * A course page, Student page text field to enter the parameter of search
	 */
	private JTextField studentSearchParam;
	
	/**
	 * A Course page, Assignment page list model for the assignments which is held 
	 * by a list
	 */
	private DefaultListModel<String> assignListModel;
	
	/**
	 * A Course page, Assignment page list for the assignments which holds a list 
	 * model
	 */
	private JList<String> assignmentJList;
	
	/**
	 * A Course page, Assignment page button to upload an assignment
	 */
	private JButton uploadAssign;
	
	/**
	 * A Course page, Assignment page button to change the active status of an assignment
	 */
	private JButton setAssignActive;
	
	/**
	 * A Course page, Assignment page button to view submissions for an assignment
	 */
	private JButton viewSubmissions;
	
	
	/**	A constructor which sets the professor's ID, first name, and last name.
	 * Also creates and sets restrictions on the container as well as the Home and 
	 * Course pages.
	 * @param proffID - the professor's ID
	 * @param proffFirstName - the professor's first name 
	 * @param proffLastName - the professor's last name
	 */
	public ProfessorView(int proffID, String proffFirstName, String proffLastName) {
		super("Professor Learning Platform");
		this.proffID=proffID;
		this.proffFirstName=proffFirstName;
		this.proffLastName=proffLastName;
		container=getContentPane();
		mainCards = new CardLayout();
		container.setLayout(mainCards);
		setSize(700, 500);
		setResizable(false);
		makeWindowListener();
		createHomeDisplay();
		intitializeCourseDisplay();
	}
	
	public void setCurrentCourseID(int courseID) {
		currentCourseID = courseID;
	}
	
	public int getCurrentCourseID() {
		return currentCourseID;
	}
	
	public int getProffID()
	{
		return proffID;
	}
	
	public DefaultListModel<String> getListModel()
	{
		return studentListModel;
	}
	
	public String getSearchParam()
	{
		return studentSearchParam.getSelectedText();
	}
	
	public int getCourseID()
	{
		String string=courseJList.getSelectedValue();
		String[] strings=string.split(";");
		return Integer.parseInt(strings[0]);
	}
	
	public String[] getCourseInfo() {
		int index = courseJList.getSelectedIndex();
		String courseInfoAsOneLine = new String(courseListModel.get(index));
		String[] courseInfo = courseInfoAsOneLine.split(";");
		return courseInfo;
	}
	
	/** Creates a JOptionPane which displays the message to the user.
	 * @param message - the message to be displayed
	 */
	public void simpleMessage(String message) {
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.PLAIN_MESSAGE);
	}
	
	/** Creates a JOptionPane which displays the error to the user.
	 * @param errorMessage - the specific error message
	 */
	public void simpleError(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "An error has occured",
									  JOptionPane.ERROR_MESSAGE);
	}
	
	/** 
	 * Creates the Home panel and adds it as the first card to the 
	 * container's card layout
	 */
	public void createHomeDisplay() {
		homePanel = new JPanel(new BorderLayout());
		container.add(homePanel, "HOME");
		createHomeTopPanel();
		createHomeCenterPanel();
	}
	
	/** 
	 * Creates the content in the North panel of the Home panel
	 */
	private void createHomeTopPanel()
	{	
		JPanel grandPanel=new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, "Professor Learning Platform");
		JPanel buttonPanel=new JPanel();
		setOurStyle(buttonPanel);
		createCourses=new JButton("CREATE A COURSE");
		viewCourses=new JButton("VIEW COURSES");
		setButtonStyle(createCourses);
		setButtonStyle(viewCourses);
		
		buttonPanel.add(createCourses);
		buttonPanel.add(viewCourses);
		grandPanel.add(bannerPanel);
		grandPanel.add(buttonPanel);
		homePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	/** Creates the "banner" of a page, which includes a ribbon and a message.
	 * @param bannerPanel - the JPanel which will be the banner panel
	 * @param topMessage - the display message
	 */
	private void createBanner(JPanel bannerPanel, String topMessage)
	{
		JLabel banner=new JLabel(topMessage);
		banner.setFont(BIGFONT);
		banner.setForeground(LABEL);
		bannerPanel.setOpaque(true);
		bannerPanel.add(banner, JLabel.CENTER);
		bannerPanel.setBackground(BACKGROUND);
	}
	
	
	/** Creates the content in the center panel of the Home panel
	 */
	private void createHomeCenterPanel()
	{
		JPanel grandPanel = new JPanel();
		setOurStyle(grandPanel);
		String[] welcomeMessage= {"Welcome to your home page, Professor " +
								   proffFirstName + " " + proffLastName + " ("+proffID+")",
								   "What would you like to do?"};
		courseListModel=new DefaultListModel<String>();
		for(int i=0; i<welcomeMessage.length; i++) {
			courseListModel.addElement(welcomeMessage[i]);
		}
		courseJList = new JList<String>(courseListModel);
		courseJList.setFont(SMALLFONT);
		courseScrollPane=new JScrollPane(courseJList);
		courseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		courseScrollPane.setPreferredSize(new Dimension(600, 350));
		
		grandPanel.add(courseScrollPane);
		homePanel.add(grandPanel, BorderLayout.CENTER);
	}
	

	/**
	 * Initializes the Course panel and adds it to the container's card layout
	 */
	private void intitializeCourseDisplay() {
		coursePanel = new JPanel(new BorderLayout());
		container.add(coursePanel, "COURSES");
		
	}
	
	/**
	 * Creates the content in the Course Panel based on incoming course info. If
	 * a button has already been instantiated, will remove the Course panel and 
	 * create a new one
	 * @param courseInfo - the course information 
	 */
	public void createCourseDisplay(String [] courseInfo) {
		if(viewStudents != null) {
			container.remove(coursePanel);
			intitializeCourseDisplay();
		}
		createCourseTopPanel(courseInfo);
		createCourseInnerPanel(courseInfo);
	}
	
	/**
	 * Creates the content in the North panel of the course panel according the the incoming
	 * course information
	 * @param courseInfo - the course information
	 */
	private void createCourseTopPanel(String[] courseInfo) {
		JPanel grandPanel = new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		//Where [1] is the name of the course
		createBanner(bannerPanel, courseInfo[1]);
		JPanel topButtons = new JPanel();
		setOurStyle(topButtons);
		
		setCourseactive = new JButton("CHANGE COURSE ACTIVE STATUS");
		viewStudents = new JButton("STUDENTS");
		viewAssigns = new JButton("ASSIGNMENTS");
		returnHome = new JButton("HOME");
		setButtonStyle(setCourseactive);
		setButtonStyle(viewStudents);
		setButtonStyle(viewAssigns);
		setButtonStyle(returnHome);
		
		topButtons.add(setCourseactive);
		topButtons.add(viewStudents);
		topButtons.add(viewAssigns);
		topButtons.add(returnHome);
		
		grandPanel.add(bannerPanel);
		grandPanel.add(topButtons);
		coursePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Creates the content in the center panel of the course panel according to the incoming
	 * course information
	 * @param courseInfo - the course information
	 */
	private void createCourseInnerPanel(String[] courseInfo) {
		courseInnerCards = new CardLayout();
		courseInnerPanel = new JPanel(courseInnerCards);
		JPanel courseStudentPanel = new JPanel(new BorderLayout());
		JPanel courseAssignPanel = new JPanel(new BorderLayout());
		//where [0] is the course ID
		JScrollPane studentScrollPane = createStudentScrollPane(courseInfo[0]);
		JScrollPane assignScrollPane = createAssignScrollPane(courseInfo[0]);
		
		courseInnerPanel.add(courseStudentPanel, "STUDENTS");
		courseInnerPanel.add(courseAssignPanel, "ASSIGNMENTS");

		courseAssignPanel.add(assignScrollPane, BorderLayout.CENTER);
		courseStudentPanel.add(studentScrollPane, BorderLayout.CENTER);
		
		JPanel studentButtonsPanel = new JPanel();
		JPanel assignButtonsPanel = new JPanel();
		addStudentButtons(studentButtonsPanel);
		addAssignButtons(assignButtonsPanel);
		courseStudentPanel.add(studentButtonsPanel, BorderLayout.SOUTH);
		courseAssignPanel.add(assignButtonsPanel, BorderLayout.SOUTH);
		coursePanel.add(courseInnerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Creates the student scroll pane
	 * @param id - ASLKDJALKDJALDJLASJDLKAJSDLKJASDLKJASDLKJASDLJASDLJASDLKJASDLJASDLJASDLJASDLJASDLKJ
	 * @returns the built student scroll pane
	 */
	public JScrollPane createStudentScrollPane(String id) {
		studentListModel=new DefaultListModel<String>();
		studentListModel.addElement(id);
		studentJList=new JList<String>(studentListModel);
		studentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentJList.setVisibleRowCount(15);
		studentJList.setFont(SMALLFONT);
		JScrollPane studentScrollPane = new JScrollPane(studentJList);
		studentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		studentScrollPane.setPreferredSize(new Dimension(500,300));
		return studentScrollPane;
	}
	
	/**
	 * Creates the assignment scroll pane
	 * @param id - ASLKDJALKDJALDJLASJDLKAJSDLKJASDLKJASDLKJASDLJASDLJASDLKJASDLJASDLJASDLJASDLJASDLKJ
	 * @returns the built assignment scroll pane
	 */
	public JScrollPane createAssignScrollPane(String id) {
		assignListModel=new DefaultListModel<String>();
		assignListModel.addElement(id);
		assignmentJList=new JList<String>(assignListModel);
		assignmentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assignmentJList.setVisibleRowCount(15);
		assignmentJList.setFont(SMALLFONT);
		JScrollPane assignScrollPane = new JScrollPane(assignmentJList);
		assignScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		assignScrollPane.setPreferredSize(new Dimension(500,300));
		return assignScrollPane;
	}
	
	/** Initializes buttons and adds them onto the student button panel
	 * @param buttonsPanel - the student button panel
	 */
	private void addStudentButtons(JPanel buttonsPanel) {
		enrollment =  new JButton("ENROLL/UNENROLL");
		emailStudents = new JButton("EMAIL STUDENTS");
		searchStudents = new JButton("SEARCH A STUDENT");
		setButtonStyle(enrollment);
		setButtonStyle(emailStudents);
		setButtonStyle(searchStudents);
		studentSearchParam = new JTextField();
		studentSearchParam.setColumns(15);
		studentSearchParam.setBorder(BORDER);
		buttonsPanel.add(enrollment);
		buttonsPanel.add(emailStudents);
		buttonsPanel.add(searchStudents);
		buttonsPanel.add(studentSearchParam);
	}
	
	/** Initializes buttons and adds them onto the assignment button panel
	 * @param buttonsPanel - the assignment button panel
	 */
	private void addAssignButtons(JPanel buttonsPanel) {
		setAssignActive = new JButton("CHANGE ACTIVE STATUS");
		uploadAssign = new JButton("UPLOAD ASSIGNMENT");
		viewSubmissions = new JButton("VIEW SUBMISSIONS");
		setButtonStyle(setAssignActive);
		setButtonStyle(uploadAssign);
		setButtonStyle(viewSubmissions);
		buttonsPanel.add(setAssignActive);
		buttonsPanel.add(uploadAssign);
		buttonsPanel.add(viewSubmissions);
	}
	
	/** Adds the elements of courseList to the courseListModel
	 * @param courseList - the courses to be on the courseListModel
	 */
	public void createCourseJList(String[] courseList)
	{
		courseListModel.removeAllElements();
		for (int i=0; i<courseList.length; i++)
		{
			courseListModel.addElement(courseList[i]);
		}
	}
	
	/**
	 * Changes the current course inner panel to the Student page
	 */
	public void viewStudentsPage() {
		courseInnerCards.show(courseInnerPanel, "STUDENTS");
	}
	
	/**
	 * Changes the current course inner panel to the Assignment page
	 */
	public void viewAssignsPage() {
		courseInnerCards.show(courseInnerPanel, "ASSIGNMENTS");
	}
	
	/**
	 * Changes the current container to the Home page
	 */
	public void viewHomePage() {
		mainCards.show(container, "HOME");
	}
	
	/**
	 * Changes the current container to the Course page
	 */
	public void viewCoursesPage() {
		mainCards.show(container, "COURSES");	
	}
	
	/**
	 * Removes old elements of the student list model and adds new ones
	 * @param students - the list of updated students
	 */
	public void updateStudentListDisplay(String[] students)
	{	
		studentListModel.removeAllElements();
		for (int i=0; i<students.length; i++) {
			studentListModel.addElement(students[i]);
		}
	}
	
	/**
	 * Removes old elements of the assignment list model and adds new ones
	 * @param assigns - the list of updated assignments
	 */
	public void updateAssignListDisplay(String[] assigns) {
		assignListModel.removeAllElements();
		for (int i=0; i<assigns.length; i++) {
			assignListModel.addElement(assigns[i]);
		}
	}
	
	/**
	 * Creates a file from a file browser to upload
	 * @return the file to upload
	 */
	public File chooseFile()
	{
		File selectedFile=null;
		JFileChooser fileBrowser=new JFileChooser();
		if(fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			selectedFile = fileBrowser.getSelectedFile();
		return selectedFile;
	}

	/** 
	 * Creates a JOptionPane where a professor can enter their email contents.
	 * @return a String array which contains the professor ID, subject, and 
	 * 		   email message.
	 */
	public String [] sendingMail() {
		/*	[0] is the courseID
		 * 	[1] is the subject line
		 * 	[2] is the email message
		 */	
		String[] theMail = new String[4];
		JPanel emailPanel = new JPanel(new BorderLayout());
		JTextField subject = new JTextField("Subject");
		JTextArea messageA = new JTextArea("What do you want to say?");
		JScrollPane mScrollPane = new JScrollPane(messageA);
		mScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);	
		emailPanel.add(subject, BorderLayout.NORTH);
		emailPanel.add(mScrollPane, BorderLayout.CENTER);
		String[] buttons = {"SEND", "CANCEL"};
		UIManager.put("OptionPane.minimumSize", new Dimension(500,500));
		int result = JOptionPane.showOptionDialog(null, emailPanel, "Send an email",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
				null, buttons, null);
		if(result == JOptionPane.YES_OPTION) {
			theMail[0] = ""+currentCourseID;
			theMail[1] = subject.getText();
			theMail[2] = messageA.getText();
			theMail[3]=""+proffID;
		}
		else {
			JOptionPane.getRootFrame().dispose();
		}
		UIManager.put("OptionPane.minimumSize", new Dimension(100,50));
		return theMail;
	}

	/** Creates a JOptionPane where a professor can create a course.
	 * @return a String array which contains the name of the course and its active bit
	 */
	public String[] createCourse()
	{
		Object[] objects={ "CREATE COURSE",  "CANCEL"};
		JPanel panel=new JPanel();
		JTextField coursename=new JTextField("Enter the Course Name");
		JTextField active=new JTextField("Active?");
		panel.add(coursename);
		panel.add(active);
		int result = JOptionPane.showOptionDialog(null, panel, 
				"Enter Info of new Course to Create",JOptionPane.YES_NO_OPTION, 
				JOptionPane.PLAIN_MESSAGE, null, objects, null);
		
		if (result==JOptionPane.YES_OPTION){
			if (!active.getText().equals("1") && !active.getText().equals("0")) {
				JOptionPane.showMessageDialog(null, "Active field should either have a 1"
						+ " (course active) or O (course inactive)","Error",
						JOptionPane.PLAIN_MESSAGE);
			}
			else {
				String[] strings= {coursename.getText(), active.getText()};
				JOptionPane.showMessageDialog(null, "Course succesfully entered",
						"Success",JOptionPane.PLAIN_MESSAGE);
				return strings;
			}	
		}	
			
		if (result==JOptionPane.NO_OPTION){
			JOptionPane.getRootFrame().dispose();
		}
		return null;
	}
	
	/**
	 * Sets a button's foreground, background, and font to a specified style
	 * @param theButton - the button to style
	 */
	private void setButtonStyle(JButton theButton) {
		theButton.setForeground(BUTTONTEXT);
		theButton.setBackground(FOREGROUND);
		theButton.setFont(BUTTONFONT);
	}
	
	/**
	 * Sets a component's foreground and background to a specified style
	 * @param theComponent - the component to style
	 */
	private void setOurStyle(JComponent theComponent) {
		theComponent.setForeground(FOREGROUND);
		theComponent.setBackground(BACKGROUND);
	}
	
	/** Adds listeners onto their respective buttons and list
	 * @param createCourses - the createCourses listener
	 * @param viewCourses - the viewCourses listener
	 * @param courseListListener - the courseListListener listener
	 */
	public void addHomeListeners(ActionListener createCourses, ActionListener viewCourses, 
			 ListSelectionListener courseListListener) 
	{
		this.createCourses.addActionListener(createCourses);
		this.viewCourses.addActionListener(viewCourses);
		this.courseJList.addListSelectionListener(courseListListener);
	}

	/** Adds listeners onto their respective buttons 
	 * @param setCourseActive -  the setCourseActive listener
	 * @param viewStudents - the viewStudents listener
	 * @param viewAssigns - the viewAssigns listener
	 * @param returnHome - the returnHome listener
	 * @param emailStudents - the emailStudents listener 
	 * @param searchStudents - the searchStudents listener
	 * @param enrollment - the enrollment listener
	 * @param uploadAssign - the uploadAssign listener
	 * @param setAssignActive - the setAssignActive listener
	 * @param viewSubmissions - the viewSubmissions listener
	 */
	public void addCourseListeners(ActionListener setCourseActive, 
			   ActionListener viewStudents, ActionListener viewAssigns, 
			   ActionListener returnHome, ActionListener emailStudents, 
			   ActionListener searchStudents, ActionListener enrollment, 
			   ActionListener uploadAssign, ActionListener setAssignActive, 
			   ActionListener viewSubmissions) {
		this.setCourseactive.addActionListener(setCourseActive);
		this.viewStudents.addActionListener(viewStudents);
		this.viewAssigns.addActionListener(viewAssigns);
		this.returnHome.addActionListener(returnHome);
		this.emailStudents.addActionListener(emailStudents);
		this.searchStudents.addActionListener(searchStudents);
		this.enrollment.addActionListener(enrollment);
		this.uploadAssign.addActionListener(uploadAssign);
		this.setAssignActive.addActionListener(setAssignActive);
		this.viewSubmissions.addActionListener(viewSubmissions);
	}
	
	/**	Sets the default closing option of the overall JFrame
	 */
	private void makeWindowListener()
	{
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog((JFrame) e.getSource(), 
						"Are you sure you want to exit the application?",
						"Exit Application", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				else if (result==JOptionPane.NO_OPTION)
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			
		});
	}

	public static void main(String[] args) {
		ProfessorView pView = new ProfessorView(69420, "Daniel", "Guieb");
		pView.setVisible(true);
	}
}
