package mailer;
import javax.mail.*;
import prop.Config;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class Mailer {
	/**
	 * sends a mail with the given Text and subject
	 * @param text
	 * @param subject
	 */
	public static void sendMail(String text, String subject){
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		String name = Config.loadProp("GMAIL_NAME");
		String pw = Config.loadProp("GMAIL_PW");
		String receiver = Config.loadProp("ADRESS_RECEIVE");

		
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(name,pw);
				}
			});

		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(name + "@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiver));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
