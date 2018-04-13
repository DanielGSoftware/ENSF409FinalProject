package FrontEnd;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.PasswordAuthentication;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Provides the data fields and methods to create a login GUI for a user
 * @authors Daniel Guieb, Huzaifa Amar
 * @version 1.0
 * @since April 12, 2018
 */
public class LoginGUI extends JFrame implements OurStyle{
	/**
	 * A container which holds all GUI components
	 */
	private Container container;
	
	/**
	 * A text field to input a user's user information
	 */
	private JTextField userT;
	
	/**
	 * A text field to input a user's password
	 */
	private JPasswordField passT;
	
	/**
	 * A button to send the text field information to the server
	 */
	private JButton signInB;
	
	/**
	 * A constructor which creates and sets restrictions on the container
	 */
	public LoginGUI() {
		super("Login");
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
		String pass = "";
		for(int i=0; i<passT.getPassword().length; i++) {
			pass += passT.getPassword()[i];
		}
		return pass;
	}
	
	/**
	 * Creates a JOptionPane which displays the error to the user
	 * @param errorMessage
	 */
	public void simpleError(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage, "An error has occured", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Creates the "banner" of the page which is a message
	 */
	private void createLoginBanner() {
		JPanel bannerPanel =  new JPanel();
		JLabel banner = new JLabel("Login To Your Home Page");
		banner.setFont(SMALLFONT);
		bannerPanel.setOpaque(true);
		setOurStyle(bannerPanel);
		bannerPanel.add(banner, JLabel.CENTER);
		container.add(bannerPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Creates the main panel and adds it to the container
	 */
	private void createMainPanel() {
		JPanel grandPanel = new JPanel(new BorderLayout());
		setOurStyle(grandPanel);
		JPanel fieldPanel = new JPanel(new BorderLayout());
		setOurStyle(fieldPanel);
		
		JPanel userPanel = new JPanel();
		setOurStyle(userPanel);
		JLabel userL = new JLabel("Username");
		userL.setFont(SMALLFONT);
		userT = new JTextField();
		userT.setBorder(BORDER);
		userT.setColumns(15);
		userPanel.add(userL);
		userPanel.add(userT);
		
		JPanel passPanel = new JPanel();
		setOurStyle(passPanel);
		JLabel passL = new JLabel("Password");
		passL.setFont(SMALLFONT);
		passT = new JPasswordField();
		passT.setEchoChar('\u2022');
		passT.setBorder(BORDER);
		passT.setColumns(15);
		passPanel.add(passL);
		passPanel.add(passT);	
		
		JPanel signInPanel = new JPanel();
		setOurStyle(signInPanel);
		signInB = new JButton("Sign In");
		setButtonStyle(signInB);
		signInPanel.add(signInB);
		fieldPanel.add(userPanel, BorderLayout.NORTH);
		fieldPanel.add(passPanel, BorderLayout.CENTER);
		fieldPanel.add(signInPanel, BorderLayout.SOUTH);
		
		grandPanel.add(fieldPanel, BorderLayout.CENTER);
		container.add(grandPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Sets a button's foreground, background, and font to a specified style
	 * @param theButton - the button to style
	 */
	private void setButtonStyle(JButton theButton) {
		theButton.setForeground(BUTTONTEXT);
		theButton.setBackground(FOREGROUND);
		theButton.setFont(BUTTONFONT);
	}
	
	/**
	 * Sets a componnet's foreground and background to a specified style
	 * @param theComponent - the component to style
	 */
	private void setOurStyle(JComponent theComponent) {
		theComponent.setForeground(FOREGROUND);
		theComponent.setBackground(BACKGROUND);
	}
	
	/**
	 * Adds an action listener to the sign in button
	 * @param aListener - the sign in button's action listener
	 */
	public void addSignInActionListener(ActionListener aListener) {
		signInB.addActionListener(aListener);
	}
	
	/**
	 * Sets the default closing option of the overall JFrame
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
}