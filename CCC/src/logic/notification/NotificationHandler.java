package logic.notification;

import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.modelController.EMailServerController;
import model.email.EmailAddress;
import model.email.EmailServerData;
import model.rule.Rule;

/**
 * AlertHandler provides Methodes for triggering alerts (Email, Popup)
 * 
 * @author Diego Etter
 * @version 1.1
 * 
 */

public class NotificationHandler {

	private NotificationHandler() {
	};

	private static Session openSession;
	private static EmailServerData emailServerBuffer;
	private static LinkedList<String> SuccessEmail = new LinkedList<>();
	private static LinkedList<String> ErrorEmail = new LinkedList<>();

	/**
	 * 
	 * @param EmailServerData
	 *            to test the connection
	 * @throws AuthenticationFailedException
	 *             --> if the authentication fails
	 * @throws MessagingException
	 *             --> if the connection-options are incorrect
	 */
	public static void TestConnection(final EmailServerData email)
			throws AuthenticationFailedException, MessagingException {
		emailServerBuffer = email;
		openCorrectConnection();
	}
	
	/**
	 * This Trigger sends a Message to all active EmailAccounts It uses one
	 * server --> if the serverconnection fails, it takes the next saved server
	 * Opens a MessageDialog with the result in it
	 * 
	 * @param String
	 *            alertSubject
	 * @param String
	 *            alertMessage
	 */
	public static boolean sendTestEmail(final String alertSubject, final String alertMessage){
	boolean error = false;
		for (final EmailServerData server : EMailServerController.getInstance().getEMailServerList()) {
			emailServerBuffer = server;
			try {
				openCorrectConnection();
				sendMail(alertSubject, alertMessage, true);
				StringBuffer message = new StringBuffer();
					message.append("Successfully sent Email(s) over: " + server.getUsername() + "\n\n" + "Successfully Email(s) to:")	;
				while(SuccessEmail.size()>0){
					message.append("\n" + SuccessEmail.removeFirst()); 
				}
				message.append("\n\n"+"Error Emails to:");
				while(ErrorEmail.size()>0){
					message.append("\n" + ErrorEmail.removeFirst()); 
				}
				
				JOptionPane.showMessageDialog(null, message.toString(), "Email Test",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} catch (final MessagingException e) {
				JOptionPane.showMessageDialog(null, "Couldn't reach Server: " + server.getUsername(), "Server Error",
						JOptionPane.ERROR_MESSAGE);
				error = true;
			}
			
		}
		return !error;
	}

	/**
	 * This Trigger sends a Message to all active EmailAccounts It uses one
	 * server --> if the serverconnection fails, it takes the next saved server
	 * 
	 * @param String
	 *            alertSubject
	 * @param String
	 *            alertMessage
	 */
	public static void triggerEmailAlert(final Rule pRule) {
		String mailMessage = "The following event was triggered: "+pRule.toText();
		String mailSubject = "[CCC] - Rule triggered";
		for (final EmailServerData server : EMailServerController.getInstance().getEMailServerList()) {
			emailServerBuffer = server;
			try {
				openCorrectConnection();
				sendMail(mailSubject,mailMessage,false);
				return;
			} catch (final MessagingException e) {
				Logger.write("Couldn't reach the server: " + server.getUsername(), LogLevel.error);
				Logger.write(e.getMessage(), LogLevel.error);
			}
		}
	}

	/**
	 * This Trigger opens a new Popup with the param as its message
	 * 
	 * @param String
	 *            alertTitle
	 * @param String
	 *            alertMessage
	 */
	public static void triggerPopUpAlert(Rule rule) {
		String alertMessage = rule.toString();
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, alertMessage, "CCC",
				JOptionPane.WARNING_MESSAGE);

	}

	private static void openCorrectConnection()
			throws AuthenticationFailedException, MessagingException {
		switch (emailServerBuffer.getEncyption()) {
		case "TLS":
			openSessionTLS();
			break;
		case "SSL":
			openSessionSSL();
			break;
		default:
			openSessionNonEncryption();
			break;
		}

	}

	private static void sessionConnect(final Properties props)
			throws MessagingException, NoSuchProviderException {
		openSession = Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailServerBuffer
								.getUsername(), emailServerBuffer.getPassword());
					}
				});
		openSession.getTransport("smtp").connect();
	}
	

	private static void openSessionTLS() throws AuthenticationFailedException,
			MessagingException {
		// Output properties
		final Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", emailServerBuffer.getHost());
		props.put("mail.smtp.port", emailServerBuffer.getPort().toString());
		// open session
		sessionConnect(props);
	}


	private static void openSessionSSL() throws AuthenticationFailedException,
			MessagingException {
		// Output properties
		final Properties props = new Properties();
		String port = emailServerBuffer.getPort().toString();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.port",port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.host", emailServerBuffer.getHost());
		props.put("mail.smtp.port", port);

		// open session
		sessionConnect(props);
	}

	private static void openSessionNonEncryption()
			throws AuthenticationFailedException, MessagingException {
		final Properties props = new Properties();
		props.put("mail.smtp.host", emailServerBuffer.getHost());
		props.put("mail.smtp.port", emailServerBuffer.getPort());
		props.put("mail.smtp.auth", "true");

		sessionConnect(props);
	}

	private static void sendMail(String pMailSubject, String pMailMessage, boolean pTestEmail) {

		for (final EmailAddress email : EMailServerController.getInstance()
				.getEmailAddressList()) {
			if (email.isActive()) {

					// The E-Mail Body
					final Message message = new MimeMessage(openSession);
					try {
						message.setFrom(new InternetAddress("no_reply@ccctest.com"));
						message.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse(email.getAddress()));
						message.setSubject(pMailSubject);
						message.setText(pMailMessage);
						Transport.send(message);
						if(pTestEmail){
							SuccessEmail.add(email.getAddress());
						}
					} catch (MessagingException e) {
						if(pTestEmail){
							ErrorEmail.add(email.getAddress());
						}else{
							Logger.write("Couldn't send email to: " + e.getMessage(), LogLevel.error);
							Logger.write(e.getMessage(), LogLevel.error);
						}
					}






			}

		}

	}

}
