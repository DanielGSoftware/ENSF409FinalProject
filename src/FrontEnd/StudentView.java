package FrontEnd;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
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
	
	public StudentView(int studentID, String studentFirstName, String studentLastName) {
		super("Student Learning Platform");
		this.studentID = studentID;
		this.studentFirstName = studentFirstName;
		this.studentLastName = studentLastName;
		container = getContentPane();
		mainCards = new CardLayout();
		container.setLayout(mainCards);
		
	}
}
