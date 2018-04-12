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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentView extends JFrame implements OurStyle{
	private String studentFirstName;
	private String studentLastName;
	private int studentID;
	private int currentCourseID;
	
	private CardLayout mainCards;
	private Container container;
	
	/*
	 * Home panel displays the Student's courses and the student can
	 * click on them to view that course page
	 */
	private JPanel homePanel;
	private DefaultListModel<String> courseListModel;
	private JList<String> courseJList;
	private JButton getCourses;
	
	/*
	 * Course panel displays the student's assignments where they can click on
	 * them to view that assignment's grade, or upload an assignment. Students
	 * can also send their course's professor an email.
	 */
	private JPanel coursePanel;
	private DefaultListModel<String> assignListModel;
	private JList<String> assignJList;
	private JTextArea grade;
	private JScrollPane assignScrollPane;
	private JButton downloadAssign;
	private JButton uploadAssign;
	private JButton sendEmailToProff;
	private JButton returnHome;
	

	/** A constructor which requires the student's ID, first name, and last name.
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
		
		//Testing
		String[] testCourseInfo = {"ENCM369", "1070"};
		String[] testAssignInfo = {"Assign 1", "Assign 2", "Assign 3",
								   "Assign 4", "Assign 5", "Assign 6"};
		createCourseDisplay(testCourseInfo, testAssignInfo);
//		mainCards.show(container, "COURSE");
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
	
	
	/** Creates a JOptionPane which displays the message to the user.
	 * @param message - the message to be displayed
	 */
	public void simpleMessage(String message) {
		JOptionPane.showMessageDialog(null, message, null,
									  JOptionPane.PLAIN_MESSAGE);
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
		createHomeBotPanel();
	}
	
	/**	Creates the top panel of the home panel
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
	
	/**	Creates the "banner" of a page, which includes a ribbon and a message.
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
	
	/** Creates the center panel of the home page
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
		
		//Testing
		courseJList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				/*
				 * getselectedvalue split ; 
				 * setcourseid = split[0]
				 * createcoursedisplay(split[1], )
				 */
//				createCourseDisplay(testCourseInfo, testAssignInfo);
				mainCards.show(container, "COURSE");
			}
		});
		grandPanel.add(courseScrollPane);
		homePanel.add(grandPanel, BorderLayout.CENTER);
		
	}
	
	public void addCourses(String[] courses) {
		homePanel.setVisible(false);
		for(int i=0; i<courses.length; i++) {
			courseListModel.addElement(courses[i]);
		}
		homePanel.setVisible(true);
	}
	
	public void hideGetCourseButton() {
		getCourses.setVisible(false);
	}
	
	private void createHomeBotPanel() {
		JPanel grandPanel = new JPanel();
		setOurStyle(grandPanel);
		getCourses = new JButton("See Courses");
		grandPanel.add(getCourses);
		homePanel.add(grandPanel, BorderLayout.SOUTH);
	}
	
	/** Initializes the components to be used in the course panel when a user
	 * clicks on the button that pulls up their course
	 */
	private void initializeCourseDisplay() {
		coursePanel = new JPanel(new BorderLayout());
		container.add(coursePanel, "COURSE");
		grade = new JTextArea();
		grade.setColumns(5);
		grade.setEditable(false);
		grade.setBorder(BORDER);
		uploadAssign = new JButton("SUBMIT TO DROPBOX");
		downloadAssign = new JButton("DOWNLOAD ASSIGNMENT");
		sendEmailToProff = new JButton("EMAIL PROFFESSOR");
		returnHome = new JButton("HOME");
		setButtonStyle(uploadAssign);
		setButtonStyle(downloadAssign);
		setButtonStyle(sendEmailToProff);
		setButtonStyle(sendEmailToProff);
		setButtonStyle(returnHome);
		assignListModel = new DefaultListModel<String>();
		assignJList = new JList<String>(assignListModel);
		assignScrollPane = new JScrollPane(assignJList);
		assignJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assignJList.setVisibleRowCount(15);
		assignJList.setFont(SMALLFONT);
		coursePanel.add(assignScrollPane, BorderLayout.CENTER);
	}

	
	
	/** Creates the course display from the courseInfo and assignInfo
	 * @param courseInfo - the information of the course
	 * @param assignInfo - the information of the assignments
	 */
	private void createCourseDisplay(String []courseInfo, String[] assignInfo) {
		//Where courseInfo[1] is the courseID
		setCurrentCourseID(Integer.parseInt(courseInfo[1]));
		createCourseTopPanel(courseInfo);
		createCourseInnerPanel(assignInfo);
	}
	
	/**	Sets the top panel of the course panel according to courseInfo
	 * @param courseInfo - the information of the course that was clicked
	 */
	private void createCourseTopPanel(String [] courseInfo) {
		JPanel grandPanel = new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		//Where courseInfo[0] is the course name
		createBanner(bannerPanel, courseInfo[0]);
		JPanel topButtons = new JPanel();
		setOurStyle(topButtons);
		topButtons.add(downloadAssign);
		topButtons.add(uploadAssign);
		topButtons.add(sendEmailToProff);
		topButtons.add(returnHome);
		grandPanel.add(bannerPanel);
		grandPanel.add(topButtons);
		coursePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	/**	Sets the inner panel of the course panel according to courseInfo
	 * @param courseInfo- the information of the course that was clicked
	 */
	private void createCourseInnerPanel(String[] assignInfo) {
		JPanel grandPanel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		JScrollPane assignScrollPane = new JScrollPane(assignJList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		for(int i=0; i<assignInfo.length; i++) {
			assignListModel.addElement(assignInfo[i]);
		}
		/*
		 * Listener for assignListModel should set the text of grade to
		 * whatever the assignment grade is from database
		 */
		
		JLabel gradeInfo = new JLabel("ASSIGNMENT GRADE: ");
		bottomPanel.add(gradeInfo);
		bottomPanel.add(grade);
		setOurStyle(bottomPanel);
		grandPanel.add(assignScrollPane, BorderLayout.CENTER);
		grandPanel.add(bottomPanel, BorderLayout.SOUTH);
		coursePanel.add(grandPanel, BorderLayout.CENTER);
	}
	
	
	/** Finds and returns information about the selected assignment
	 * @return a String array of the current course ID and the assignment name
	 */
	public String[] getAssignmentInfo() {
		if(assignJList.getSelectedValue()!=null) {
			// [0] is the courseID, [1] is the assign name
			String[] assignInfo = {""+currentCourseID, assignJList.getSelectedValue()};
			return assignInfo;
		}
		return null;
	}
	
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
	
	private void setButtonStyle(JButton theButton) {
		theButton.setForeground(BUTTONTEXT);
		theButton.setBackground(FOREGROUND);
		theButton.setFont(BUTTONFONT);
	}
	
	private void setOurStyle(JComponent theComponent) {
		theComponent.setForeground(FOREGROUND);
		theComponent.setBackground(BACKGROUND);
	}
	
	public File chooseFile()
	{
		File selectedFile=null;
		JFileChooser fileBrowser=new JFileChooser();
		if(fileBrowser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			selectedFile = fileBrowser.getSelectedFile();
		return selectedFile;
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
			ActionListener uploadAssign, ActionListener sendEmailToProff) {
		this.assignJList.addListSelectionListener(assignJList);
		this.uploadAssign.addActionListener(uploadAssign);
		this.sendEmailToProff.addActionListener(sendEmailToProff);
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
		String[] courses = {"ENCM369, 1070", "ENGG233, 3030", "ENSF409, 2080"};
		StudentView sv = new StudentView(69240, "Daniel", "Guieb");
		sv.addCourses(courses);
		sv.setVisible(true);
	}
}
