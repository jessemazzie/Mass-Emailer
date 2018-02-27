import java.util.Properties;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

public class MailService {
	Session session;
	
	MailService() {
		Properties props = new Properties();
		session = Session.getDefaultInstance(props);
	}
	
	void sendMail(String fromAddress, String toAddress, String body) {
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(fromAddress));
			message.setRecipient(RecipientType.TO, new InternetAddress(toAddress));
			message.setText(body);
		} catch (AddressException e) {
			//TODO: Put in array to be put in file for "bad" emails.
		} catch (MessagingException e) {
			//Not sure what causes this.
			e.printStackTrace();
		}
	}
}
