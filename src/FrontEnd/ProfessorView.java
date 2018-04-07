package FrontEnd;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class ProfessorView extends JFrame {
	private String proffFirstName;
	private String proffLastName;
	private int proffID;
	
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
	private JScrollPane searchresults;
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
	
	public String getSearchParam()
	{
		return findStudents.getText();
	}
	
	public int getCourseID()
	{
		String string=courseJList.getSelectedValue();
		String[] strings=string.split(";");
		return Integer.parseInt(strings[0]);
	}
	
	
	public ProfessorView(int proffID, String proffFirstName, String proffLastName) {
		super("Professor Learning Platform");
		this.proffID=proffID;
		this.proffFirstName=proffFirstName;
		this.proffLastName=proffLastName;
		container=getContentPane();
		mainCards = new CardLayout();
		container.setLayout(mainCards);
		String[] info=new String[3];
		info[2]="Hi";
		setSize(700, 500);
		setResizable(false);
		makeWindowListener();
		createHomeDisplay();
		createCourseDisplay(info);
	}
	
	public int getProffID()
	{
		return proffID;
	}
	
	public DefaultListModel<String> getListModel()
	{
		return studentListModel;
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

	public void createCourseJList(String[] courselist)
	{
		courseListModel.removeAllElements();
		for (int i=0; i<courselist.length; i++)
		{
			courseListModel.addElement(courselist[i]);
		}
	}
	
	private void createHomeTopPanel()
	{	
		JPanel grandPanel=new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, "Professor Learning Platform");
		JPanel buttonPanel=new JPanel();
		createCourses=new JButton("CREATE A COURSE");
		viewCourses=new JButton("VIEW COURSES");
		
		buttonPanel.add(createCourses);
		buttonPanel.add(viewCourses);
		grandPanel.add(bannerPanel);
		grandPanel.add(buttonPanel);
		homePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	
	private void createHomeCenterPanel()
	{
		JPanel grandPanel = new JPanel();
		String[] welcomeMessage= {"Welcome to your home page, Professor " +
								   proffFirstName + " " + proffLastName + " ("+proffID+")",
								   "What would you like to do?"};
		courseListModel=new DefaultListModel<String>();
		for(int i=0; i<welcomeMessage.length; i++) {
			courseListModel.addElement(welcomeMessage[i]);
		}
		courseJList = new JList<String>(courseListModel);
		searchresults=new JScrollPane(courseJList);
		searchresults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		grandPanel.add(searchresults);
		homePanel.add(grandPanel, BorderLayout.CENTER);
	}
	
	public void createCourseDisplay(String [] info) {
		coursePanel = new JPanel(new BorderLayout());
		container.add(coursePanel, "COURSES");
		createCourseTopPanel(info);
		createCourseInnerPanel(info);
	}
	
	private void createCourseTopPanel(String[] info) {
		JPanel grandPanel = new JPanel(new GridLayout(2, 1));
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, info[1]);
		JPanel topButtons = new JPanel();
		
		setCourseactive = new JButton("CHANGE COURSE ACTIVE STATUS");
		viewStudents = new JButton("STUDENTS");
		viewAssigns = new JButton("ASSIGNMENTS");
		returnHome = new JButton("HOME");
		
		topButtons.add(setCourseactive);
		topButtons.add(viewStudents);
		topButtons.add(viewAssigns);
		topButtons.add(returnHome);
		
		grandPanel.add(bannerPanel);
		grandPanel.add(topButtons);
		coursePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	private void createCourseInnerPanel(String[] info) {
		courseInnerCards = new CardLayout();
		courseInnerPanel = new JPanel(courseInnerCards);
		
		JPanel courseStudentPanel = new JPanel(new BorderLayout());
		JPanel courseAssignPanel = new JPanel(new BorderLayout());
		JScrollPane studentScrollPane = createStudentScrollPane(info[0]);
		JScrollPane assignScrollPane = createAssignScrollPane(info[0]);
		
		courseInnerPanel.add(courseStudentPanel, "STUDENTS");
		courseInnerPanel.add(courseAssignPanel, "ASSIGNMENTS");
		courseAssignPanel.add(assignScrollPane, BorderLayout.CENTER);
		courseStudentPanel.add(studentScrollPane, BorderLayout.CENTER);
		
		JPanel studentButtonsPanel = new JPanel();
		addStudentButtons(studentButtonsPanel);
		JPanel assignButtonsPanel = new JPanel();
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
		studentJList.setFont(new Font("Courier New", Font.BOLD, 11));
		
		JScrollPane studentScrollPane = new JScrollPane(studentJList);
		studentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		studentScrollPane.setPreferredSize(new Dimension(90,100));
		return studentScrollPane;
	}
	
	public JScrollPane createAssignScrollPane(String id) {
		assignListModel=new DefaultListModel<String>();
		assignListModel.addElement(id);
		assignmentJList=new JList<String>(studentListModel);
		assignmentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		assignmentJList.setVisibleRowCount(15);
		assignmentJList.setFont(new Font("Courier New", Font.BOLD, 11));
		
		JScrollPane assignScrollPane = new JScrollPane(assignmentJList);
		assignScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		assignScrollPane.setPreferredSize(new Dimension(90,100));
		return assignScrollPane;
	}
	
	private void addStudentButtons(JPanel buttonsPanel) {
		enrollment =  new JButton("ENROLL/UNENROLL");
		emailStudents = new JButton("EMAIL STUDENTS");
		searchStudents = new JButton("SEARCH A STUDENT");
		findStudents = new JTextField();
		findStudents.setColumns(15);
		buttonsPanel.add(enrollment);
		buttonsPanel.add(emailStudents);
		buttonsPanel.add(searchStudents);
		buttonsPanel.add(findStudents);
	}
	
	private void addAssignButtons(JPanel buttonsPanel) {
		setAssignActive = new JButton("CHANGE ACTIVE STATUS");
		uploadAssign = new JButton("UPLOAD ASSIGNMENT");
		viewSubmissions = new JButton("VIEW SUBMISSIONS");
		buttonsPanel.add(setAssignActive);
		buttonsPanel.add(uploadAssign);
		buttonsPanel.add(viewSubmissions);
	}
	
	public void viewStudentsPage() {
		courseInnerCards.show(courseInnerPanel, "STUDENTS");
	}
	
	public void viewAssignsPage(String[] assignments) {
		//assignmentJModel.removeAllElements();
		for (int i=0; i<assignments.length; i++) {
			assignListModel.addElement(assignments[i]);
		}
		courseInnerCards.show(courseInnerPanel, "ASSIGNMENTS");
	}
	
	public void viewHomePage() {
		mainCards.show(container, "HOME");
		studentJList.clearSelection();
		courseJList.clearSelection();
	}
	
	public void viewCoursesPage() {
		int index=courseJList.getSelectedIndex();
		String string=courseListModel.get(index);
		String[] info=string.split(";");
		createCourseDisplay(info);
		mainCards.show(container, "COURSES");	
	}
	
	public String[] getSelectedList()
	{
		String string=courseJList.getSelectedValue();
		String[] strings=string.split(";");
		System.out.println(string);
		return strings;
	}
	
	public void updateStudentListDisplay(String[] string)
	{
		studentListModel.removeAllElements();
		for (int i=0; i<string.length; i++) {
			studentListModel.addElement(string[i]);
		}
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
	
//	public String[] EnrollStudentJOptionPane()					// commented out for errors
//	{
//		
//	}
	
	
	
	
	
	public void addHomeListeners(ActionListener createCourses, ActionListener viewCourses, 
			 ListSelectionListener courseListListener) 
	{
		this.createCourses.addActionListener(createCourses);
		this.viewCourses.addActionListener(viewCourses);
		this.courseJList.addListSelectionListener(courseListListener);
	}

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
	
	public String[] createCourse()
	{
		Object[] objects={ "create course",  "cancel"};
		JPanel panel=new JPanel();
		JTextField coursename=new JTextField("Enter the Course Name");
		JTextField active=new JTextField("Active?");
		panel.add(coursename);
		panel.add(active);
		int result = JOptionPane.showOptionDialog(null, panel, "Enter Info of new Course to Create",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, objects, null);
		
		if (result==JOptionPane.YES_OPTION){
			if (!active.getText().equals("1") && !active.getText().equals("0")) {
				JOptionPane.showMessageDialog(null, "Active field should either have a 1"
						+ " (course active) or O (course inactive)","Error",JOptionPane.PLAIN_MESSAGE);
			}
			else {
				String[] strings= {coursename.getText(), active.getText()};
				JOptionPane.showMessageDialog(null, "Course succesfully entered","Success",JOptionPane.PLAIN_MESSAGE);
				return strings;
			}
		}	
			
		if (result==JOptionPane.NO_OPTION){
			JOptionPane.getRootFrame().dispose();
		}
		return null;
	}
	
	public void addBrowseCourseListener(ActionListener a)
	{
		viewCourses.addActionListener(a);
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
