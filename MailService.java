import java.util.Properties;

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
	
	void sendMail(String fromAddress, String toAddress, String body) {
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(fromAddress));
			message.setRecipient(RecipientType.TO, new InternetAddress(toAddress));
			message.setText(body);
			//TODO: Set date
			Transport.send(message);
		} catch (AddressException e) {
			//TODO: Put in array to be put in file for "bad" emails.
		} catch (MessagingException e) {
			//Not sure what causes this.
			e.printStackTrace();
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