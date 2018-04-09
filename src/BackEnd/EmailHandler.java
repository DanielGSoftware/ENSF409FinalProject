package BackEnd;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;

public class EmailHandler {
	private Properties properties;
	private Session session;
	private Message message;
	private String senderemail;
	private String senderpassword;
	private InternetAddress recieveremails;
	private String subjectline;
	private String emailmessage;
	
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
	
	public void createEmail()
	{
		createProperties();
		createSession();
		sendEmail();
	}

	private void createProperties()
	{
		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true"); 
		properties.put("mail.smtp.auth", "true"); 
		properties.put("mail.smtp.host", "smtp.gmail.com"); 
		properties.put("mail.smtp.port", "587"); 
	}
	
	private void createSession()
	{
		Session session = Session.getInstance(properties,
				new javax.mail.Authenticator(){
				 protected PasswordAuthentication getPasswordAuthentication() {
				 return new PasswordAuthentication(senderemail, senderpassword);
				 }
				});
	}
	
	private void sendEmail()
	{
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderemail));
			message.setRecipient(Message.RecipientType.TO, recieveremails);
			message.setSubject("Your Message Subject");
			message.setText("Your Message Content");
			Transport.send(message); // Send the Email Message
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
