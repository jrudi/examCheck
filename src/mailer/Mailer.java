package mailer;
import javax.mail.*;
import prop.Props;
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

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Props.GMAIL_NAME,Props.GMAIL_PW);
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Props.GMAIL_NAME + "@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(Props.ADRESS_RECEIVE));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
