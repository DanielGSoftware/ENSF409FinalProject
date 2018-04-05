package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;

import SharedObjects.Course;


public class ProfessorView extends JFrame {
	private String profffirstname;
	private String profflastname;
	private int proffid;
	
	
	private JButton enrollment;
	private JButton uploadassignment;
	private JButton setassignmentactive;
	private JButton viewgrades;
	private JScrollPane searchresults;
	private DefaultListModel<String> listmodel = new DefaultListModel<String>();
	private JList<String> displaylist = new JList<String>();
	private Container container;
	
	private JButton returnHome;
	
	private JPanel homePanel;
	private JButton createCourses;
	private JButton browseCourses;
	
	private JPanel coursePanel;
	private JButton setCourseactive;
	private JButton searchStudents;
	private JButton searchAssigns;
	private JButton massEmail;
	
	
	public ProfessorView(int proffid, String profffirstname, String profflastname)
	{
		super("Professor Learning Platform");
		this.proffid=proffid;
		this.profffirstname=profffirstname;
		this.profflastname=profflastname;
		container=getContentPane();
		container.setLayout(new BorderLayout());
		setSize(700, 500);
//		setResizable(false);
		
		createHomeDisplay();
		createCourseDisplay();
		container.add(homePanel);
//		setPage(homePanel);
		
//		createHomeTopDisplayPanel();
//		createHomeCenterDisplay();
//		createBottomDisplayPanel();
		makeWindowListener();
	}
	
	public int getProffID()
	{
		return proffid;
	}
	
	public DefaultListModel<String> getListModel()
	{
		return listmodel;
	}
	
	public void setPage(JPanel newPanel, JPanel oldPanel) {
		newPanel.setVisible(true);
		container.add(newPanel);
		oldPanel.setVisible(false);
		
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
		createHomeTopPanel();
		createHomeCenterPanel();
		createReturnHomeButton();
//		container.add(homePanel);
	}
	
	private void createReturnHomeButton() {
		returnHome = new JButton("Return Home");
		returnHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setPage(homePanel, coursePanel);
			}
		});
	}
	
	private void createHomeTopPanel()
	{	
		JPanel grandPanel=new JPanel(new GridLayout(2, 1));
		
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, "Professor Learning Platforms");
		
		JPanel buttonPanel=new JPanel();
		createCourses=new JButton("Create Course");
		browseCourses=new JButton("Browse Courses");
		
		browseCourses.addActionListener(new ActionListener() {			// TEST CODE
			@Override													//
			public void actionPerformed(ActionEvent e) {				//
				setPage(coursePanel, homePanel);									//
				System.out.println("SWITCHING TO COURSE PAGE");			//
			}															//
		});																//
		
		
//		setCourseactive=new JButton("Set Course Active Status");
//		searchStudents=new JButton("Search Students");
		buttonPanel.add(createCourses);
		buttonPanel.add(browseCourses);
//		buttonPanel.add(setCourseactive);
//		buttonPanel.add(searchStudents);
//		temp[1][0].add(buttonPanel);
		
		grandPanel.add(bannerPanel);
		grandPanel.add(buttonPanel);
		homePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	
//	private void createBottomDisplayPanel()
//	{
//		JPanel southpanel=new JPanel();
//		enrollment=new JButton("Enroll Student");
//		uploadassignment=new JButton("Upload Assignment");
//		setassignmentactive=new JButton("Set Assignment Active");
//		viewgrades=new JButton("View Grades");
//		southpanel.add(enrollment);
//		southpanel.add(uploadassignment);
//		southpanel.add(setassignmentactive);
//		southpanel.add(viewgrades);
//		container.add(southpanel, BorderLayout.SOUTH);
//	}
	
	private void createHomeCenterPanel()
	{
		JPanel grandPanel = new JPanel();
		
		String welcome = "Welcome to your home page, Professor " +
						 profffirstname + " ("+proffid+")" + "\n"
						 + "What would you like to do?";
		
//		String[] argument= {"Welcome Professor "+profffirstname+" "+profflastname+" ("+proffid+")", "You are currently in no course or student page"};
//		listmodel=new DefaultListModel<String>();
//		listmodel.addElement(argument[0]);
//		listmodel.addElement(argument[1]);
//		displaylist=new JList<String>();
//		displaylist.setModel(listmodel);
		JTextArea welcomePane = new JTextArea(welcome);
		welcomePane.setFont(new Font("Courier New", Font.PLAIN, 14));
//		welcomePane.setColumns(30);
		welcomePane.setRows(20);
		
		
//		System.out.println("In constructor: " + displaylist.getModel().getSize());
//		displaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		displaylist.setVisibleRowCount(15);
//		displaylist.setFont(new Font("Courier New", Font.BOLD, 11));
//		JTextPane displayarea=new JTextPane();
//		displayarea.setText("Welcome Professor "+profffirstname+" "+profflastname+" ("+proffid+")\nYou are currently in no course or student page");
//		displayarea.setFont(new Font("Courier New", Font.BOLD, 11) );
//		displayarea.setEditable(false);
//		searchresults=new JScrollPane(displaylist);
//		searchresults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		searchresults.setPreferredSize(new Dimension(600,325));
//		temp[0][0].add(searchresults);
		grandPanel.add(welcomePane);
		homePanel.add(grandPanel, BorderLayout.CENTER);
	}
	
//	public void switchToHomePage() {
//		container.add(homePanel);
//	}
	
	public void createCourseDisplay() {
		coursePanel = new JPanel(new BorderLayout());
		createCourseTopPanel();
		createCourseCenterPanel();
	}
	
	private void createCourseTopPanel() {
		JPanel grandPanel = new JPanel(new GridLayout(2, 1));
		
		JPanel bannerPanel = new JPanel();
		createBanner(bannerPanel, "COURSE NAME - NOT COMPLETE");
		
		JPanel topButtons = new JPanel();
		setCourseactive = new JButton("Change Active Status");
		searchStudents = new JButton("Students");
		searchAssigns = new JButton("Assigns");
		topButtons.add(setCourseactive);
		topButtons.add(searchStudents);
		topButtons.add(searchAssigns);
		topButtons.add(returnHome);
		
		grandPanel.add(bannerPanel);
		grandPanel.add(topButtons);
		coursePanel.add(grandPanel, BorderLayout.NORTH);
	}
	
	private void createCourseCenterPanel() {
		JPanel grandPanel = new JPanel();
		
	}
	
//	public void switchToCoursePage() {
//		container.remove(homePanel);
//		container.add(coursePanel);
//	}
	
	
	
	
	
	
	private void makeWindowListener()
	{
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog((JFrame) e.getSource(), "Are you sure you want to exit the application?",
						"Exit Application", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				if (result==JOptionPane.NO_OPTION)
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			
		});
	}
	
	
	public void addCreateCourseListener(ActionListener a)
	{
		createCourses.addActionListener(a);
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
		browseCourses.addActionListener(a);
	}
	
	
	public void updateDisplay(String[] list)
	{
		System.out.println("updating display");
		while (!listmodel.isEmpty())
		{
			listmodel.removeElementAt(0);
		}
		
		for (int i=0; i<list.length; i++) {
			listmodel.addElement(list[i]);
		}
	}
	
	public void addCourseActiveListener(ActionListener a)
	{
		setCourseactive.addActionListener(a);
	}
	
	public void addListListener(ListSelectionListener a)
	{
		displaylist.addListSelectionListener(a);
	}
	
//	public void setCoursePage()
//	{
//		System.out.println("SET COURSE PAGE TEST");
//		ArrayList<String> strings=getSelectedList();
//		System.out.println("LMAO");
//		updateDisplay(strings);
//	}
	
	public ArrayList<String> getSelectedList()
	{
		System.out.println("In selected list method");
		System.out.println(displaylist.getSelectedValue());
		System.out.println(displaylist.getModel().getElementAt(0));
		//String string=displaylist.getSelectedValue();
//		int index=0;
		String string=new String(displaylist.getSelectedValue());
		System.out.println(string);
		//String[] strings=string.split(";");
		ArrayList<String> strings=new ArrayList<String>();
		strings.add(string);
		return strings;
	}
	
	public void displayCourseActiveUpdateMessage()
	{
		JOptionPane.showMessageDialog(null, "Course Activation Status Sucessfully Changed. Please hit browse courses to "
				+ "see changes","Success",JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void main(String[] args) {
		ProfessorView pView = new ProfessorView(69420, "Daniel", "Guieb");
		pView.setVisible(true);
	}
}
