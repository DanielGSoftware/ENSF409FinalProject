package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LoginGUI extends JFrame{
	
	private LoginModel loginmodel;
	
	private JTextField userT;
	private JTextField passT;
	private JButton signInB;
	private JButton clear;
	private Container container;
	
	/*
	 * Create an interface of colors that are consistent and pretty, but in 
	 * the mean time use this code for everything
	 */
	public Color myPink = new Color(255, 166, 207);
	
	public LoginGUI() {
		super("Login");
		container = getContentPane();
		setLayout(new BorderLayout());
		setSize(500, 200);
		setResizable(false);
		
		createLoginBanner();
		createMainPanel();
		makeWindowListener();
	}
	
	private void createLoginBanner() {
		JPanel bannerPanel =  new JPanel();
		JLabel banner = new JLabel("Login To Your Home Page");
		banner.setFont(new Font("Times New Roman", Font.BOLD,20));
		banner.setForeground(Color.white);
		bannerPanel.setOpaque(true);
		bannerPanel.setBackground(myPink);
		bannerPanel.add(banner, JLabel.CENTER);
		container.add(bannerPanel, BorderLayout.NORTH);
	}
	
	private void createMainPanel() {
		JPanel grandPanel = new JPanel();
		grandPanel.setLayout(new BorderLayout());
		JPanel fieldPanel = new JPanel(new BorderLayout());
		//fieldPanel.setLayout(new GridLayout(2, 1));
		
		JPanel userPanel = new JPanel();
		JLabel userL = new JLabel("Username");
		userT = new JTextField();
		userT.setColumns(15);
		userPanel.add(userL);
		userPanel.add(userT);
//		userPanel.setBackground(Color.white);
		
		JPanel passPanel = new JPanel();
		JLabel passL = new JLabel("Password");
		passT = new JTextField();
		passT.setColumns(15);
		passPanel.add(passL);
		passPanel.add(passT);
//		passPanel.setBackground(Color.WHITE);
		
		signInB = new JButton("Sign In");
		signInB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("STUFF SHOULD SEND");
			}
		});
		
		fieldPanel.add(userPanel, BorderLayout.NORTH);
		fieldPanel.add(passPanel, BorderLayout.CENTER);
//		JPanel temp=new JPanel();
//		temp.add(signInB);
//		temp.add(comp, constraints);
		fieldPanel.add(signInB, BorderLayout.SOUTH);
		grandPanel.add(fieldPanel, BorderLayout.CENTER);
		//grandPanel.add(signInB, BorderLayout.SOUTH);
		
		container.add(grandPanel, BorderLayout.CENTER);
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
				if (result==JOptionPane.NO_OPTION)
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			
		});
	}
	
	public static void main(String[] args) {
		LoginGUI gui = new LoginGUI();
		gui.setVisible(true);
	}
	
}