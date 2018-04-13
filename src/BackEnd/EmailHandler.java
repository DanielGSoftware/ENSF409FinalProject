package BackEnd;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Contains information to deal with email requests
 * @author Huzaifa Amar	and Daniel Guieb
 */
public class EmailHandler {
	/**
	 * properties object
	 */
	private Properties properties;
	/**
	 * To establish an email connection
	 */
	private Session session;
	/**
	 * email message
	 */
	private Message message;
	/**
	 * email account of sender
	 */
	private String senderemail;
	/**
	 * senders password
	 */
	private String senderpassword;
	/**
	 * email address of reciever
	 */
	private InternetAddress recieveremails;
	/**
	 * subject line of email
	 */
	private String subjectline;
	/**
	 * message line of proff
	 */
	private String emailmessage;
	
	/**
	 * Creats an object of Email Handler with passed parameters
	 * @param senderemail email of sender
	 * @param senderpassword password of sender
	 * @param recieveremail reciever's email
	 * @param subjectline the subject
	 * @param emailmessage the actual email
	 */
	public EmailHandler (String senderemail, String senderpassword, String recieveremail, String subjectline, String emailmessage)
	{
		this.senderemail=senderemail;
		this.senderpassword=senderpassword;
		try {
			recieveremails=new InternetAddress(recieveremail);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.subjectline=subjectline;
		this.emailmessage=emailmessage;
	}
	
	/**
	 * create email
	 */
	public void createEmail()
	{
		createProperties();
		createSession();
		sendEmail();
	}

	/**
	 * create properties object
	 */
	private void createProperties()
	{
		properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true"); 
		properties.put("mail.smtp.auth", "true"); 
		properties.put("mail.smtp.host", "smtp.gmail.com"); 
		properties.put("mail.smtp.port", "587"); 
	}
	
	/**
	 * create session object
	 */
	private void createSession()
	{
		session = Session.getInstance(properties,
				new javax.mail.Authenticator(){
				 protected PasswordAuthentication getPasswordAuthentication() {
				 return new PasswordAuthentication(senderemail, senderpassword);
				 }
				});
	}
	
	/**
	 * send email to required destination
	 */
	private void sendEmail()
	{
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderemail));
			message.setRecipient(Message.RecipientType.TO, recieveremails);
			message.setSubject(subjectline);
			message.setText(emailmessage);
			Transport.send(message); // Send the Email Message
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
