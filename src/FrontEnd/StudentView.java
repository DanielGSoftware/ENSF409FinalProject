package FrontEnd;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.omg.CORBA.PRIVATE_MEMBER;

import SharedObjects.Course;

public class StudentView extends JFrame implements OurColours{
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
	
	/*
	 * Course panel displays the student's assignments where they can click on
	 * them to view that assignment's grade, or upload an assignment. Students
	 * can also send their course's professor an email.
	 */
	private JPanel coursePanel;
	private DefaultListModel<String> assignListModel;
	private JList<String> assignJList;
	private JTextField grade;
	private JScrollPane assignScrollPane;
	private JButton uploadAssign;
	private JButton sendEmailToProff;
	

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
//		String[] fuckMe = {"nvm its chill", "i love this project", "wowow uwu xD"};
//		createCourseDisplay(fuckMe);
//		mainCards.show(container, "COURSE");
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
	
	public int getCourseID()
	{
		return currentCourseID;
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
	}
	
	/**	Creates the top panel of the home panel
	 */
	private void createHomeTopPanel() {
		JPanel grandPanel=new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, "Student Learning Platform");
		JPanel welcomePanel = new JPanel();
		JLabel welcomeLabel = new JLabel("Welcome, " + studentFirstName + " " + 
										studentLastName + " ("+studentID+")");
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
		banner.setFont(new Font("Times New Roman", Font.BOLD,20));
		banner.setForeground(FONTCOLOUR);
		bannerPanel.setOpaque(true);
		bannerPanel.add(banner, JLabel.CENTER);
		bannerPanel.setBackground(MAINCOLOUR);
	}
	
	/** Creates the center panel of the home page
	 */
	private void createHomeCenterPanel() {
		JPanel grandPanel = new JPanel();
		courseListModel = new DefaultListModel<String>();
		courseJList = new JList<String>(courseListModel);
		JScrollPane courseScrollPane = new JScrollPane(courseJList);
		courseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		grandPanel.add(courseScrollPane);
		homePanel.add(grandPanel, BorderLayout.CENTER);
		
	}
	
	/** Initializes the components to be used in the course panel when a user
	 * clicks on the button that pulls up their course
	 */
	private void initializeCourseDisplay() {
		coursePanel = new JPanel(new BorderLayout());
		container.add(coursePanel, "COURSE");
		grade = new JTextField();
		grade.setColumns(10);
		uploadAssign = new JButton("UPLOAD ASSIGNMENT");
		sendEmailToProff = new JButton("EMAIL PROFFESSOR");
		assignListModel = new DefaultListModel<String>();
		assignJList = new JList<String>(assignListModel);
		assignScrollPane = new JScrollPane(assignJList);
		assignJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assignJList.setVisibleRowCount(15);
		assignJList.setFont(new Font("Courier New", Font.BOLD, 11));
	}

	
	/** Creates the course display from the courseInfo and assignInfo
	 * @param courseInfo - the information of the course
	 * @param assignInfo - the information of the assignments
	 */
	private void createCourseDisplay(String []courseInfo, String[] assignInfo) {
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
		topButtons.add(uploadAssign);
		topButtons.add(sendEmailToProff);
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
		JScrollPane assignScrollPane = new JScrollPane(assignJList);
		assignScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//TODO create JList from assignInfo
		JLabel gradeInfo = new JLabel("ASSIGNMENT GRADE: ");
		bottomPanel.add(gradeInfo);
		bottomPanel.add(grade);
		grandPanel.add(assignScrollPane, BorderLayout.CENTER);
		grandPanel.add(bottomPanel, BorderLayout.SOUTH);
		coursePanel.add(grandPanel, BorderLayout.CENTER);
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
	
	public static void main(String[] args) {
		StudentView sv = new StudentView(69240, "Daniel", "Guieb");
		sv.setVisible(true);
	}
}
