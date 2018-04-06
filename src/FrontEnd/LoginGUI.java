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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LoginGUI extends JFrame{
	private JTextField userT;
	private JTextField passT;
	private JButton signInB;
	private Container container;
	
	/*
	 * Create an interface of colors that are consistent and pretty, but in 
	 * the mean time use this code for everything
	 */
	public Color myPink = new Color(255, 166, 207);
	public Color myPeach = new Color(255, 209, 175);
	public Color myOrange = new Color(255, 192, 78);
	
	public LoginGUI(ObjectInputStream readObject, ObjectOutputStream sendObject) {
		super("Login");
		new LoginModel(readObject, sendObject);
		container = getContentPane();
		setLayout(new BorderLayout());
		setSize(500, 160);
		setResizable(false);
		
		createLoginBanner();
		createMainPanel();
		makeWindowListener();
	}
	
	public String getUser() {
		return userT.getText();
	}
	
	public String getPass() {
		return passT.getText();
	}
	
	private void createLoginBanner() {
		JPanel bannerPanel =  new JPanel();
		JLabel banner = new JLabel("Login To Your Home Page");
		banner.setFont(new Font("Times New Roman", Font.BOLD,20));
		banner.setForeground(new Color(80, 86, 87));
		bannerPanel.setOpaque(true);
		bannerPanel.setBackground(myPeach);
		bannerPanel.add(banner, JLabel.CENTER);
		container.add(bannerPanel, BorderLayout.NORTH);
	}
	
	private void createMainPanel() {
		JPanel grandPanel = new JPanel(new BorderLayout());
		JPanel fieldPanel = new JPanel(new BorderLayout());
		
		JPanel userPanel = new JPanel();
		JLabel userL = new JLabel("Username");
		userT = new JTextField();
		userT.setColumns(15);
		userPanel.add(userL);
		userPanel.add(userT);
//		userPanel.setBackground(new Color(198, 199, 255));
		userPanel.setBackground(Color.white);
		
		JPanel passPanel = new JPanel();
		JLabel passL = new JLabel("Password");
		passT = new JTextField();
		passT.setColumns(15);
		passPanel.add(passL);
		passPanel.add(passT);
//		passPanel.setBackground(new Color(198, 199, 255));
		passPanel.setBackground(Color.white);
		
		JPanel signInPanel = new JPanel();
		signInB = new JButton("Sign In");
		signInPanel.add(signInB);
		signInPanel.setBackground(Color.white);
//		signInB.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("STUFF SHOULD SEND");
//				System.out.println(userT.getText());
//				System.out.println(passT.getText());
//				validLogin = theModel.loginAttempt(userT.getText(), passT.getText());
//				if(validLogin) {
//					// make a student or proff?
//					System.out.println("CLOSING");
//					close();
//				}
//				else {
//					sendError("Check user info and try again please");
//				}
//			}
//		});
		
		
		fieldPanel.add(userPanel, BorderLayout.NORTH);
		fieldPanel.add(passPanel, BorderLayout.CENTER);
		fieldPanel.add(signInPanel, BorderLayout.SOUTH);
		
		grandPanel.add(fieldPanel, BorderLayout.CENTER);
		
		container.add(grandPanel, BorderLayout.CENTER);
	}
	
	public void addSignInActionListener(ActionListener aListener) {
		signInB.addActionListener(aListener);
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
	
	public void sendError(String s) {
		JOptionPane.showMessageDialog(null, s,
				"Error", JOptionPane.ERROR_MESSAGE);
	}
}