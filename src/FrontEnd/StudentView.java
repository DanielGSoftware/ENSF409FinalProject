package FrontEnd;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
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

public class StudentView extends JFrame{
	private String studentFirstName;
	private String studentLastName;
	private int studentID;
	
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
	private JTextField assignGrade;
	private JScrollPane assignScrollPane;
	private JButton uploadAssign;
	private JButton sendEmailToProff;
	
	public StudentView(int studentID, String studentFirstName, String studentLastName
					   String[] courses) {
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
										studentLastName + " ("+studentID+").");
		welcomePanel.add(welcomeLabel);
		grandPanel.add(bannerPanel);
		grandPanel.add(welcomePanel);
		homePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	private void createHomeCenterPanel() {
		JPanel grandPanel = new JPanel();
		
		courseListModel = new DefaultListModel<String>();
		
		
		homePanel.add(grandPanel, BorderLayout.CENTER);
		
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
