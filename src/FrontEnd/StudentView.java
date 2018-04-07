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

public class StudentView extends JFrame{
	private String studentFirstName;
	private String studentLastName;
	private int studentID;
	private int currentCourseID;
	
	private CardLayout mainCards;
	private Container container;
	
	// Home panel displays their courses and click on them to view the course page
	private JPanel homePanel;
	private DefaultListModel<String> courseListModel;
	private JList<String> courseJList;
	
	// Course panel displays their assignments and they can click on them to
	// view grade, or upload an assignment. Students can also send their course's proff
	// and email
	private JPanel coursePanel;
	private DefaultListModel<String> assignListModel;
	private JList<String> assignJList;
	private JTextField grade;
	private JScrollPane assignScrollPane;
	private JButton uploadAssign;
	private JButton sendEmailToProff;
	
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
	
	public void simpleMessage(String message) {
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.PLAIN_MESSAGE);
	}
	
	private void createBanner(JPanel bannerPanel, String topMessage)
	{
		JLabel banner=new JLabel(topMessage);
		banner.setFont(new Font("Times New Roman", Font.BOLD,20));
		banner.setForeground(Color.white);
		bannerPanel.setOpaque(true);
		bannerPanel.add(banner, JLabel.CENTER);
		bannerPanel.setBackground(new Color(255, 209, 175));
	}
	
	public void createHomeDisplay() {
		homePanel = new JPanel(new BorderLayout());
		container.add(homePanel, "HOME");
		createHomeTopPanel();
		createHomeCenterPanel();
	}

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
	
	private void createHomeCenterPanel() {
		JPanel grandPanel = new JPanel();
		courseListModel = new DefaultListModel<String>();
		courseJList = new JList<String>(courseListModel);
		JScrollPane courseScrollPane = new JScrollPane(courseJList);
		courseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		grandPanel.add(courseScrollPane);
		homePanel.add(grandPanel, BorderLayout.CENTER);
		
	}
	
	private void initializeCourseDisplay() {
		coursePanel = new JPanel(new BorderLayout());
		container.add(coursePanel, "COURSE");
		grade = new JTextField();
		uploadAssign = new JButton("UPLOAD ASSIGNMENT");
		sendEmailToProff = new JButton("EMAIL PROFFESSOR");
		assignListModel = new DefaultListModel<String>();
		assignJList = new JList<String>(assignListModel);
		assignJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assignJList.setVisibleRowCount(15);
		assignJList.setFont(new Font("Courier New", Font.BOLD, 11));
	}
	
	
	
	private void createCourseDisplay(String []courseInfo) {
		createCourseTopPanel(courseInfo);
		createCourseInnerPanel(courseInfo);
	}
	
	private void createCourseTopPanel(String [] courseInfo) {
		JPanel grandPanel = new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		//WHERE courseInfo[0] IS THE COURSE NAME
		createBanner(bannerPanel, courseInfo[0]);
		JPanel topButtons = new JPanel();
		topButtons.add(uploadAssign);
		topButtons.add(sendEmailToProff);
		grandPanel.add(bannerPanel);
		grandPanel.add(topButtons);
		coursePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	private void createCourseInnerPanel(String[] courseInfo) {
		JPanel grandPanel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		JScrollPane assignScrollPane = new JScrollPane(assignJList);
		assignScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JLabel gradeInfo = new JLabel("ASSIGNMENT GRADE: ");
		bottomPanel.add(gradeInfo);
		bottomPanel.add(grade);
		grandPanel.add(assignScrollPane, BorderLayout.CENTER);
		grandPanel.add(bottomPanel, BorderLayout.SOUTH);
	}
	
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
		StudentView sv = new StudentView(69420, "Daniel", "Guieb");
		sv.setVisible(true);
	}
}
