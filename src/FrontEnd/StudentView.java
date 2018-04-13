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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;

/**
 * Provides the data fields and methods to create a GUI for a student.
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 11, 2018
 */
public class StudentView extends JFrame implements OurStyle{
	/**
	 * The student's first name
	 */
	private String studentFirstName;
	
	/**
	 * The student's last name
	 */
	private String studentLastName;
	
	/**
	 * The student's ID
	 */
	private int studentID;
	
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
	 * A Home page list model for courses which is held by a list
	 */
	private DefaultListModel<String> courseListModel;
	
	/**
	 * A Home page list for courses which holds a list model
	 */
	private JList<String> courseJList;
	
	/**
	 * A Home page button which fills the course list model with courses
	 */
	private JButton getCourses;
	
	/**
	 * A panel which holds all components related to the Course page
	 */
	private JPanel coursePanel;
	
	/**
	 * A Course page scroll pane to hold the assignment list
	 */
	private JScrollPane assignScrollPane;
	
	/**
	 * A Course page list model for assignments which is held by a list
	 */
	private DefaultListModel<String> assignListModel;
	
	/**
	 * A Course page list for assignments which holds a list model
	 */
	private JList<String> assignJList;
	
	/**
	 * A Course page text area which holds the grade of the selected assignment
	 */
	private JTextArea grade;
	
	/**
	 * A Course page button to download the selected assignment
	 */
	private JButton downloadAssign;
	
	/**
	 * A Course page button to upload an assignment to a drop-box
	 */
	private JButton uploadAssign;
	
	/**
	 * A Course page button to send an email to the course's professor
	 */
	private JButton sendEmailToProff;
	
	/**
	 * A Course page button to return to the Home page
	 */
	private JButton returnHome;
	

	/** A constructor which requires the student's ID, first name, and last name. Also
	 * creates and sets restrictions on the container as well as the Home and Course pages.
	 * @param studentID - the student ID
	 * @param studentFirstName - the student first name
	 * @param studentLastName - the student last name
	 */
	public StudentView(int studentID, String studentFirstName, String studentLastName) {
		super("Student Learning Platform");
		this.studentID = studentID;
		this.studentFirstName = studentFirstName;
		this.studentLastName = studentLastName;
		container = getContentPane();
		mainCards = new CardLayout();
		container.setLayout(mainCards);
		setSize(700, 500);
		setResizable(false);
		makeWindowListener();
		createHomeDisplay();
		initializeCourseDisplay();
	}
	
	public void setCurrentCourseID(int courseID) {
		currentCourseID = courseID;
	}
	
	public void setGrade(int theGradeAsInt) {
		grade.setText(""+theGradeAsInt);
	}
	
	public int getCurrentCourseID() {
		return currentCourseID;
	}
	
	public String getFirstName()
	{
		return studentFirstName;
	}
	
	public String getLastName()
	{
		return studentLastName;
	}
	
	public int getStudentID()
	{
		return studentID;
	}
	
	public String getAssignmentName() {
		String fileName = assignJList.getSelectedValue();
		return fileName;
	}
	
	public String[] getCourseSelected() {
		String[] courseInfo = courseJList.getSelectedValue().split(";");
		return courseInfo;
	}

	/** Creates a JOptionPane which displays the message to the user.
	 * @param message - the message to be displayed
	 */
	public void simpleMessage(String message) {
		JOptionPane.showMessageDialog(null, message, null,
									  JOptionPane.PLAIN_MESSAGE);
	}
	
	/** 
	 * Creates a JOptionPane which displays the error to the user.
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
		createHomeBotPanel();
	}
	
	/**	
	 * Creates the content in the North panel of the Home panel
	 */
	private void createHomeTopPanel() {
		JPanel grandPanel=new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, "Student Learning Platform");
		JPanel welcomePanel = new JPanel();
		setOurStyle(welcomePanel);
		JLabel welcomeLabel = new JLabel("Welcome, " + studentFirstName + " " + 
										studentLastName + " ("+studentID+")");
		welcomeLabel.setFont(SMALLFONT);
		welcomePanel.add(welcomeLabel);
		grandPanel.add(bannerPanel);
		grandPanel.add(welcomePanel);
		homePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	/**	
	 * Creates the "banner" of a page, which includes a ribbon and a message.
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
		setOurStyle(bannerPanel);
	}
	
	/** 
	 * Creates the content in the center panel of the Home page
	 */
	private void createHomeCenterPanel() {
		JPanel grandPanel = new JPanel();
		setOurStyle(grandPanel);
		courseListModel = new DefaultListModel<String>();
		courseJList = new JList<String>(courseListModel);
		JScrollPane courseScrollPane = new JScrollPane(courseJList,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		courseScrollPane.setPreferredSize(new Dimension(600, 350));
		courseJList.setFont(SMALLFONT);
		grandPanel.add(courseScrollPane);
		homePanel.add(grandPanel, BorderLayout.CENTER);
		
	}
	
	/** 
	 * Adds a list of courses onto the course list model
	 * @param courses - the list of courses
	 */
	public void addCourses(String[] courses) {
		for(int i=0; i<courses.length; i++) {
			courseListModel.addElement(courses[i]);
		}
	}
	
	/**
	 * Hides the get course button
	 */
	public void hideGetCourseButton() {
		getCourses.setVisible(false);
	}
	
	/**
	 * Creates the content in the South panel of the Home page
	 */
	private void createHomeBotPanel() {
		JPanel grandPanel = new JPanel();
		setOurStyle(grandPanel);
		getCourses = new JButton("See Courses");
		grandPanel.add(getCourses);
		homePanel.add(grandPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Initializes the Course panel and adds it to the container's card layout
	 */
	private void initializeCourseDisplay() {
		coursePanel = new JPanel(new BorderLayout());
		container.add(coursePanel, "COURSE");
	}

	
	
	/** Creates the course display from the incoming course info and assignment info. If a
	 * button is already instantiated, will remove the Course panel and create a new one
	 * @param courseInfo - the course information
	 * @param assignInfo - the assignments information
	 */
	public void createCourseDisplay(String []courseInfo, String[] assignInfo) {
		//Where courseInfo[0] is the courseID
		if(uploadAssign != null) {
			container.remove(coursePanel);
			initializeCourseDisplay();
		}
		createCourseTopPanel(courseInfo);
		createCourseInnerPanel(assignInfo);
	}
	
	/**	
	 * Creates the content in the North panel of the course panel according to the 
	 * incoming course information
	 * @param courseInfo - the course information
	 */
	private void createCourseTopPanel(String [] courseInfo) {
		JPanel grandPanel = new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		//Where courseInfo[0] is the course name
		createBanner(bannerPanel, courseInfo[1]);
		JPanel topButtons = new JPanel();
		setOurStyle(topButtons);
		uploadAssign = new JButton("SUBMIT TO DROPBOX");
		downloadAssign = new JButton("DOWNLOAD ASSIGNMENT");
		sendEmailToProff = new JButton("EMAIL PROFFESSOR");
		returnHome = new JButton("HOME");
		setButtonStyle(uploadAssign);
		setButtonStyle(downloadAssign);
		setButtonStyle(sendEmailToProff);
		setButtonStyle(sendEmailToProff);
		setButtonStyle(returnHome);
		topButtons.add(downloadAssign);
		topButtons.add(uploadAssign);
		topButtons.add(sendEmailToProff);
		topButtons.add(returnHome);
		grandPanel.add(bannerPanel);
		grandPanel.add(topButtons);
		coursePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	/**	Creates the content in the center panel of the course panel according to the incoming
	 * assignment information
	 * @param courseInfo- the assignment information
	 */
	private void createCourseInnerPanel(String[] assignInfo) {
		JPanel grandPanel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		assignListModel = new DefaultListModel<String>();
		assignJList = new JList<String>(assignListModel);
		assignScrollPane = new JScrollPane(assignJList);
		assignJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assignJList.setVisibleRowCount(15);
		assignJList.setFont(SMALLFONT);
		coursePanel.add(assignScrollPane, BorderLayout.CENTER);
		JScrollPane assignScrollPane = new JScrollPane(assignJList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		for(int i=0; i<assignInfo.length; i++) {
			assignListModel.addElement(assignInfo[i]);
		}
		
		JLabel gradeInfo = new JLabel("ASSIGNMENT GRADE: ");
		grade = new JTextArea();
		grade.setColumns(5);
		grade.setEditable(false);
		grade.setBorder(BORDER);
		bottomPanel.add(gradeInfo);
		bottomPanel.add(grade);
		setOurStyle(bottomPanel);
		grandPanel.add(assignScrollPane, BorderLayout.CENTER);
		grandPanel.add(bottomPanel, BorderLayout.SOUTH);
		coursePanel.add(grandPanel, BorderLayout.CENTER);
	}	
	
	/**
	 * Changes the container's card to the Home page
	 */
	public void goHome() {
		mainCards.show(container, "HOME");
	}
	
	/**
	 * Changes the contaner's card to the Course page
	 */
	public void goCoursePage() {
		mainCards.show(container, "COURSE");
	}
	
	/** 
	 * Creates a JOptionPane where a student can enter their email content
	 * @return a String array which contains the student ID, subject, and 
	 * email message
	 */
	public String [] sendingMail() {
		/*	[0] is the studentID
		 * 	[1] is the subject line
		 * 	[2] is the email message
		 */	
		String[] theMail = new String[3];
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
			theMail[0] = ""+studentID;
			theMail[1] = subject.getText();
			theMail[2] = messageA.getText();
		}
		else {
			JOptionPane.getRootFrame().dispose();
		}
		UIManager.put("OptionPane.minimumSize", new Dimension(100,50));
		return theMail;
	}
	
	/**
	 * Creates a file form a file browser to upload
	 * @return - the file to upload
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
	
	/** Adds a list selection listener to the course JList
	 * @param courseJList - the courseJList listener
	 */
	public void addHomeListeners(ListSelectionListener courseJList, ActionListener getCourses) {
		this.courseJList.addListSelectionListener(courseJList);
		this.getCourses.addActionListener(getCourses);
	}
	
	/** Adds listeners onto their respective buttons and list
	 * @param assignJList - the assignJList listener
	 * @param uploadAssign - the uploadAssign listener
	 * @param sendEmailToProff - the sendEmailToProff listener
	 */
	public void addCourseListeners(ListSelectionListener assignJList,
			ActionListener downloadAssign,ActionListener uploadAssign, 
			ActionListener sendEmailToProff, ActionListener returnHome) {
		this.assignJList.addListSelectionListener(assignJList);
		this.downloadAssign.addActionListener(downloadAssign);
		this.uploadAssign.addActionListener(uploadAssign);
		this.sendEmailToProff.addActionListener(sendEmailToProff);
		this.returnHome.addActionListener(returnHome);
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
}
