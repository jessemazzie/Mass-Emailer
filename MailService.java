import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Vector;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

public class MailService {
	Session session;
	
	MailService(String senderDomain, String portNumber, String senderPass, String senderUsername) {
		Properties props;
		MailAuthenticator authenticator;
		props = new Properties();
		authenticator = new MailAuthenticator(senderUsername, senderPass);
		
		props.put("mail.smtp.host", senderDomain);
		props.put("mail.smtp.socketFactory.port", portNumber);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.prot", portNumber);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.transport.protocol", "smtps");

		session = Session.getDefaultInstance(props, authenticator);
	}
	
	void sendMail(String fromAddress, Vector<String> toAddresses, String body) {
		PrintWriter goodEmails, badEmails;
		MimeMessage message = new MimeMessage(session);
		
		try {
			goodEmails = new PrintWriter(new FileOutputStream("good_emails.txt"));
			badEmails = new PrintWriter(new FileOutputStream("bad_emails.txt"));
		
		
			for(String recipient : toAddresses) {
				try {
					message.setFrom(new InternetAddress(fromAddress));
					message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
					message.setText(body);
					//TODO: Set date
					Transport.send(message);
					System.out.println("Message successfully sent to " + recipient);
					goodEmails.print(recipient);
				} catch (SendFailedException ae) {
					System.out.println("Address exception occured");
					badEmails.print(recipient);
				} catch (MessagingException e) {
					//Not sure what causes this.
					e.printStackTrace();
				} 
			}
			
			goodEmails.close();
			badEmails.close();
		} catch (FileNotFoundException e1) {
			//This shouldn't happen (hopefully)
			e1.printStackTrace();
		}
	}
}

class MailAuthenticator extends Authenticator {
	String senderUsername;
	String senderPass;
	
	MailAuthenticator(String senderUsername, String senderPass) {
		this.senderPass = senderPass;
		this.senderUsername = senderUsername;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(senderUsername, senderPass);
	}
}