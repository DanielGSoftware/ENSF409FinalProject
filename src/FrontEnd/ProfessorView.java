package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.Border;


public class ProfessorView extends JFrame {
	private JButton createcourses;
	private JButton browsecourses;
	private JScrollPane searchresults;
	private DefaultListModel<String> listmodel;
	private JList<String> displaylist;
	private Container container;
	
	public ProfessorView(String s)
	{
		super(s);
		container=this.getContentPane();
		this.setLayout(new BorderLayout());
		createTopDisplayPanel();
	}
	
	private void createTopDisplayPanel()
	{
		JPanel northpanel=new JPanel();
		createcourses=new JButton("Create Course");
		browsecourses=new JButton("Browse Courses");
		northpanel.add(createcourses);
		northpanel.add(browsecourses);
		container.add(northpanel, BorderLayout.NORTH);
	}
}
