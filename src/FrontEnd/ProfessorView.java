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

public class ProfessorView extends JFrame implements OurStyle{
	private String proffFirstName;
	private String proffLastName;
	private int proffID;
	private int currentCourseID;
	
	private CardLayout mainCards;
	private Container container;
	
	private JPanel homePanel;
	private JButton createCourses;
	private JButton viewCourses;
	private DefaultListModel<String> courseListModel;
	private JList<String> courseJList;
	
	private JPanel coursePanel;
	private JButton setCourseactive;
	private JButton viewStudents;
	private JButton viewAssigns;
	private JButton returnHome;
	private JPanel courseInnerPanel;
	private CardLayout courseInnerCards;
	private JScrollPane courseScrollPane;
	private DefaultListModel<String> studentListModel = new DefaultListModel<String>();
	private JList<String> studentJList = new JList<String>();	
	private DefaultListModel<String> assignListModel;
	private JList<String> assignmentJList;
	private JButton emailStudents;
	private JButton searchStudents;
	private JButton enrollment;
	private JTextField findStudents;
	private JButton uploadAssign;
	private JButton setAssignActive;
	private JButton viewSubmissions;
	
	
	/**	A constructor which requires a professor's ID, first name, and last name.
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
//		String[] info= {"1", "hello", "wow"};
		setSize(700, 500);
//		setResizable(false);
		makeWindowListener();
		createHomeDisplay();
		intitializeCourseDisplay();
//		createCourseDisplay(info);
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
	
	public String getSearchParam()
	{
		System.out.println(findStudents.getSelectedText());
		return findStudents.getSelectedText();
	}
	
	public int getCourseID()
	{
		String string=courseJList.getSelectedValue();
		String[] strings=string.split(";");
		return Integer.parseInt(strings[0]);
	}
	
	public DefaultListModel<String> getListModel()
	{
		return studentListModel;
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
	
	/** Creates the home panel and adds it as the first card to the 
	 * container's cardLayout
	 */
	public void createHomeDisplay() {
		homePanel = new JPanel(new BorderLayout());
		container.add(homePanel, "HOME");
		createHomeTopPanel();
		createHomeCenterPanel();
	}
	
	/** Creates the top panel of the home panel
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
	
	
	/** Creates the center panel of the home page
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
	

	/*
	 * NOT COMMENTED BECAUSE GOING TO CREATE A METHOD CALLED
	 * 			initializeCourseDisplay();
	 * WHICH WILL DO THE INITIALIZING AND THEN 
	 * 			createCourseDisplay(String[] info)
	 * WILL ACTUALLY PUT THE CONTENTS INTO IT
	 * 
	 */
	
	private void intitializeCourseDisplay() {
		coursePanel = new JPanel(new BorderLayout());
		container.add(coursePanel, "COURSES");
		
	}
	
	public void createCourseDisplay(String [] courseInfo) {
//		coursePanel = new JPanel(new BorderLayout());
//		container.add(coursePanel, "COURSES");
		currentCourseID = Integer.parseInt(courseInfo[0]);
		if(viewStudents != null) {
			container.remove(coursePanel);
			intitializeCourseDisplay();
		}
		createCourseTopPanel(courseInfo);
		createCourseInnerPanel(courseInfo);
	}
	
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
	
	public JScrollPane createAssignScrollPane(String id) {
		assignListModel=new DefaultListModel<String>();
		assignListModel.addElement(id);
		assignmentJList=new JList<String>(studentListModel);
		assignmentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assignmentJList.setVisibleRowCount(15);
		assignmentJList.setFont(SMALLFONT);
		JScrollPane assignScrollPane = new JScrollPane(assignmentJList);
		assignScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		assignScrollPane.setPreferredSize(new Dimension(500,300));
		return assignScrollPane;
	}
	
	/** Initializes buttons and adds them onto the buttonPanel
	 * @param buttonsPanel - the student button panel
	 */
	private void addStudentButtons(JPanel buttonsPanel) {
		enrollment =  new JButton("ENROLL/UNENROLL");
		emailStudents = new JButton("EMAIL STUDENTS");
		searchStudents = new JButton("SEARCH A STUDENT");
		setButtonStyle(enrollment);
		setButtonStyle(emailStudents);
		setButtonStyle(searchStudents);
		findStudents = new JTextField();
		findStudents.setColumns(15);
		findStudents.setBorder(BORDER);
		buttonsPanel.add(enrollment);
		buttonsPanel.add(emailStudents);
		buttonsPanel.add(searchStudents);
		buttonsPanel.add(findStudents);
	}
	
	/** Initializes buttons and adds them onto the buttonPanel
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
	
	public void viewStudentsPage() {
		courseInnerPanel.setVisible(false);
		courseInnerCards.show(courseInnerPanel, "STUDENTS");
//		courseInnerPanel.setVisible(false);
		courseInnerPanel.setVisible(true);
	}
	
	public void viewAssignsPage() {
		courseInnerPanel.setVisible(false);
		courseInnerCards.show(courseInnerPanel, "ASSIGNMENTS");
//		courseInnerPanel.setVisible(false);
		courseInnerPanel.setVisible(true);
	}
	
	public void viewHomePage() {
		mainCards.show(container, "HOME");
//		studentJList.clearSelection();
//		courseJList.clearSelection();
//		studentJList.setSelectedIndex(0);
//		courseJList.setSelectedIndex(0);
	}
	
	public void viewCoursesPage() {
//		int index=courseJList.getSelectedIndex();
//		String string=courseListModel.get(index);
//		String deepCopyString = stringDeepCopy(string);
//		String[] courseInfo=deepCopyString.split(";");
//		createCourseDisplay(courseInfo);
		mainCards.show(container, "COURSES");	
	}
	
	public String[] getCourseInfo() {
		int index = courseJList.getSelectedIndex();
		String courseInfoAsOneLine = new String(courseListModel.get(index));
		String[] courseInfo = courseInfoAsOneLine.split(";");
		return courseInfo;
	}
	
	public void updateStudentListDisplay(String[] students)
	{	
		courseInnerPanel.setVisible(false);
		studentListModel.removeAllElements();
		for (int i=0; i<students.length; i++) {
			studentListModel.addElement(students[i]);
		}
		courseInnerPanel.setVisible(true);
	}
	
	public void updateAssignListDisplay(String[] assigns) {
		assignmentJList.setVisible(false);
		System.out.println(assigns[0]);
		System.out.println(assigns[1]);
		System.out.println(assigns[2]);
		assignListModel.removeAllElements();
		for (int i=0; i<assigns.length; i++) {
			assignListModel.addElement(assigns[i]);
		}
		assignmentJList.setVisible(true);
	}
	
	public File chooseFile()
	{
		File selectedFile=null;
		JFileChooser fileBrowser=new JFileChooser();
		if(fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			selectedFile = fileBrowser.getSelectedFile();
//		long length=selectedFile.length();
//		byte[] content=new byte [(int) length];
//		try {
//			FileInputStream fis = new FileInputStream(selectedFile);
//			BufferedInputStream bos = new BufferedInputStream(fis);
//			bos.read(content, 0, (int)length);
//			} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			} catch(IOException e){
//			e.printStackTrace();
//			}
//		return content;
		return selectedFile;
	}

	/** Gives the user a pop-up where they can enter their email contents.
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

	/** Gives the user a pop-up where they can create a course.
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
	
	private void setButtonStyle(JButton theButton) {
		theButton.setForeground(BUTTONTEXT);
		theButton.setBackground(FOREGROUND);
		theButton.setFont(BUTTONFONT);
	}
	
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
	public void addCourseListeners(ActionListener setCourseActive, ActionListener viewStudents, ActionListener viewAssigns, 
			   ActionListener returnHome, ActionListener emailStudents, ActionListener searchStudents, 
			   ActionListener enrollment, ActionListener uploadAssign, ActionListener setAssignActive, 
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
				int result = JOptionPane.showConfirmDialog((JFrame) e.getSource(), "Are you sure you want to exit the application?",
						"Exit Application", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				else if (result==JOptionPane.NO_OPTION)
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			
		});
	}

	
//	public void updateDisplay(String[] list)
//	{
//		System.out.println("updating display");
//
//		listmodel.removeAllElements();
//
//		while (!studentListModel.isEmpty())
//		{
//			studentListModel.removeElementAt(0);
//		}
//
//		
//		for (int i=0; i<list.length; i++) {
//			studentListModel.addElement(list[i]);
//		}
//		listmodel.addElement("\n\n");
//	}

	
//	public void setCoursePage()
//	{
//		System.out.println("SET COURSE PAGE TEST");
//		ArrayList<String> strings=getSelectedList();
//		System.out.println("LMAO");
//		updateDisplay(strings);
//	}
	

		//studentJList.addListSelectionListener(a);


//	public ArrayList<String> getSelectedList()
//	{
//		System.out.println("In selected list method");
//		System.out.println(studentJList.getSelectedValue());
//		System.out.println(studentJList.getModel().getElementAt(0));
//		//String string=displaylist.getSelectedValue();
////		int index=0;
//		String string=new String(studentJList.getSelectedValue());
//		System.out.println(string);
//		//String[] strings=string.split(";");
//		ArrayList<String> strings=new ArrayList<String>();
//		strings.add(string);
//		return strings;
//	}
	
	
	//Replaced with simpleMessage() method
//	public void displayCourseActiveUpdateMessage()
//	{
//		JOptionPane.showMessageDialog(null, "Course Activation Status Sucessfully Changed. Please hit browse courses to "
//				+ "see changes","Success",JOptionPane.PLAIN_MESSAGE);
//	}
//	

	public static void main(String[] args) {
		ProfessorView pView = new ProfessorView(69420, "Daniel", "Guieb");
		pView.setVisible(true);
	}
}
