package ui.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import logic.modelController.EMailServerController;
import logic.notification.NotificationHandler;
import model.email.EmailServerData;

/**
 * 
 * @author Diego Etter
 * @version 1.0
 *
 */

public class EmailServerEditController {
	
	EMailServerEditView emailServerView;
	EmailServerData email;
	
	/**
	 * Creates a empty Controller
	 * 
	 */
	public EmailServerEditController(){
		System.out.println("Create new Email");
		emailServerView = new EMailServerEditView();
		initView();
	}
	
	/**
	 * Creates a Controller with the Data of the selected EmailServerData
	 * @param EmailServerData to open
	 * 
	 */
	
	public EmailServerEditController(EmailServerData email){
		System.out.println("Edit Email");
		this.email = email;
		initView();
		fillFields(email);
	}
	
	public boolean saveServerInputs(){
		if(validateInputs()){
			String username = emailServerView.getUsernameText();
			String password = emailServerView.getPasswordText();
			String hostAddress = emailServerView.getHostAddressText();
			Integer port = Integer.parseInt(emailServerView.getPortText());
			String encryption = getEncryption();	
			if(email == null){
				EmailServerData emailServer = new EmailServerData(username, password, hostAddress, port, encryption);
				EMailServerController.getInstance().addEMailServer(emailServer); //via Observer
			} else {
				email.setUsername(username);
				email.setPassword(password);
				email.setHost(hostAddress);
				email.setPort(port);
				email.setEncryption(encryption.toString());
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void setSaveButtonListener(ActionListener AL){
		emailServerView.setSaveListener(AL);
	}
	
	public void setCancelButtonListner(ActionListener AL){
		emailServerView.setCancelListener(AL);
	}
	
	private void initView(){
		emailServerView = new EMailServerEditView();
		emailServerView.setUsernameDocumentListener(new UsernameDokumentListener());
		emailServerView.setPasswordDocumentListener(new PasswordDokumentListener());
		emailServerView.setHostAddressDocumentListener(new HostAddressDocumentListener());
		emailServerView.setPortDocumentListener(new PortAddressDocumentListener());
		emailServerView.setTestListener(new testButtonListener());
	}
	
	private void fillFields(EmailServerData email){
		emailServerView.setUsernameText(email.getUsername());
		emailServerView.setPasswordText(email.getPassword());
		emailServerView.setHostAddressText(email.getHost());
		emailServerView.setPortText(Integer.toString(email.getPort()));
		switch(email.getEncyption().toUpperCase()){
		case "SSL": emailServerView.setSSL();
			break;
		case "TLS": emailServerView.setTLS();
			break;
		default: emailServerView.setNONE();
			break;
		
		}
	}
	
	private boolean validateInputs(){
		boolean validUser = validateUser();
		boolean validPassword = validatePassword();
		boolean validHost = validateHost();
		boolean validPort = validatePort();
		return (validUser && validPassword && validHost && validPort);
		
	}
	
	private boolean validateUser(){
		if(emailServerView.getUsernameText().length()<1){
			emailServerView.invalideUser();
			return false;
		} else {
			// emailServerView.valideUser();
			return true;
		}
	}
	
	private boolean validatePassword(){
		if(emailServerView.getPasswordText().length()<1){
			emailServerView.invalidePassword();
			return false;
		} else {
			// emailServerView.validePassword();
			return true;
		}
	}
	
	private boolean validateHost(){
		if(emailServerView.getHostAddressText().length()<1){
			emailServerView.invalideHostAddress();
			return false;
		}else{
			// emailServerView.valideHostAddress();
			return true;
		}
	}
	
	private boolean validatePort(){
		if(emailServerView.getPortText().length()<1){
			emailServerView.invalidePort();
			return false;
		}else{
			String port = emailServerView.getPortText();
			try{
				Integer.parseInt(port);
				//emailServerView.validePort();
				return true;
			}catch(NumberFormatException e){
				emailServerView.invalidePort();
				return false;
			}
		}
	}
	
	private String getEncryption(){
		if(emailServerView.nonencryptionSelected()){
			return "NONE";
		}
		if(emailServerView.sslSelected()){
			return "SSL";
		}
		if(emailServerView.tlsSelected()){
			return "TLS";
		}
		return null;
	}
	
	public void setCancelListener(ActionListener AL){
		emailServerView.setCancelListener(AL);
	}
	
	private void testServerConnection(){
		if(validateInputs()){
			String username = emailServerView.getUsernameText();
			String password = emailServerView.getPasswordText();
			String hostAddress = emailServerView.getHostAddressText();
			Integer port = Integer.parseInt(emailServerView.getPortText());
			String encryption = getEncryption();
			EmailServerData emailServer = new EmailServerData(username, password, hostAddress, port, encryption);
			try {
				NotificationHandler.TestConnection(emailServer);
				emailServerView.serverTestOK();
			} catch (AuthenticationFailedException e) {
				emailServerView.serverTestFail();
				emailServerView.invalideUser();
				emailServerView.invalidePassword();
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
				emailServerView.serverTestFail();
				emailServerView.invalideHostAddress();
				emailServerView.invalidePort();
			}
		}
	}
	
	class ConnectionTestThread extends Thread{
		public void run(){
			emailServerView.neutralTextfields();
			testServerConnection();
		}
	}
	
	class testButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ConnectionTestThread test = new ConnectionTestThread();
			emailServerView.serverTestWait();
			test.start();
		}
		
	}
	
	
	class UsernameDokumentListener extends DocumentListenerAdapter{

		@Override
		public void changedUpdate(DocumentEvent e) {
			validateUser();
		}
		
	}
	
	class PasswordDokumentListener extends DocumentListenerAdapter{

		@Override
		public void changedUpdate(DocumentEvent e) {
			validatePassword();		
		}
	}
	
	
	class HostAddressDocumentListener extends DocumentListenerAdapter{

		@Override
		public void changedUpdate(DocumentEvent e) {
			validateHost();
		}
	}
	
	class PortAddressDocumentListener extends DocumentListenerAdapter{

		@Override
		public void changedUpdate(DocumentEvent e) {
			validatePort();
		}	
	}
	
	abstract class DocumentListenerAdapter implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			return;	
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			return;
		}
	}
	
	
	public JPanel getView(){
		return emailServerView;
	}
	
	
}
