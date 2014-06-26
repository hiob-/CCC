package ui.preferences;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import logic.modelController.EMailServerController;
import logic.notification.NotificationHandler;
import model.ObserverObjectWrapper;
import model.email.EmailAddress;
import model.email.EmailServerData;


/**
 * 
 * @author Diego Etter
 * @version 1.0
 *
 */
public class EmailPreferencesController implements Observer{
	
	List<EmailAddress> addressList;
	List<EmailServerData> serverList;
	JPanel view;
	EmailPreferencesView emailPrefView;
	EmailServerEditController serverEditController;
	boolean newEmailAddress;
	
	/**
	 * creates the Controller and the view for the EmailPreferences
	 * For the view: getview()
	 */
	public EmailPreferencesController(){
		loadDatas();
		initView();
		enableAlertTest();
	}
	
	/**
	 * 
	 * @return a JPanel with the EmailPreferences
	 */
	public JPanel getView(){
		return view;
	}
	
	private void loadDatas(){
		addressList = EMailServerController.getInstance().getEmailAddressList();
		EMailServerController.getInstance().addObserver(this);
		for(EmailAddress address: addressList){
			address.addObserver(this);
		}
		serverList = EMailServerController.getInstance().getEMailServerList();
		for(EmailServerData server: serverList){
			server.addObserver(this);
		}
	}
	
	private void initView(){
		emailPrefView = new EmailPreferencesView();
		//emailPrefView.setAddressList(addressList);
		emailPrefView.setAddressTable(addressList);
		emailPrefView.setServerList(serverList);
		//setCrudButtons enable(false)
		emailPrefView.enableAddressSaveButton(false);
		emailPrefView.enableAddressRemoveButton(false);
		emailPrefView.enableServerEditButton(false);
		emailPrefView.enableServerRemoveButton(false);
		//setListener
		emailPrefView.setNewEmailAddressListener(new NewAddressListener());
		emailPrefView.setEditEmainAddressListener(new EditAddressListener());
		emailPrefView.setAddServerListener(new AddServerListener());
		emailPrefView.setEditServerListener(new EditServerListener());
		emailPrefView.setSaveEmailAddressListener(new SaveAddressListener());
		emailPrefView.setCancelEmailAddressListener(new CancelAddressListener());
		emailPrefView.setRemoveEmailAddressListener(new RemoveAddressListener());
		emailPrefView.setRemoveServerListener(new RemoveServerListener());
		emailPrefView.setAddressTextFieldListener(new AddressTextFieldListener());
		emailPrefView.setActiveListener(new AddressIsActiveListener());
		emailPrefView.setAddressTableListener(new AddressListListener());
		emailPrefView.setServerListListener(new ServerListListener());
		emailPrefView.setTestAlertListener(new TestEmailAlertListener());
		emailPrefView.setAddressOptionPanel();
		//initPanel
		view = new JPanel();
		view.setLayout(new BorderLayout());
		view.add(emailPrefView, BorderLayout.CENTER);
	}
	
	private void compareAddressValue(){
		String newAddress = emailPrefView.getEmailTextfield();
		boolean length = newAddress.length()>0;
		boolean signtest = newAddress.contains("@");
		String[] structure = newAddress.split("@");
		boolean strtest = structure.length == 2;
		boolean domaintest;
		if(strtest){
			String domainstr = structure[1];
			String[] domain = domainstr.split("\\.");

			if(domain.length==2){
				String domainname = domain[1];
				domaintest = domainname.length()>=2;
			}else{
				domaintest = false;
			}
			
		}else{
			domaintest = false;
		}
		
		if(length && signtest && strtest && domaintest){
			for(EmailAddress email: addressList){
				if(email.getAddress().equals(newAddress)){
					emailPrefView.enableAddressSaveButton(false);
					return;
				}
			}
			emailPrefView.enableAddressSaveButton(true);
		}else{
			emailPrefView.enableAddressSaveButton(false);
		}
	}
	
	private void enableAlertTest(){
		if(addressList.size()>0 && serverList.size()>0){
			emailPrefView.enableTestAlertButton(true);
		}else{
			emailPrefView.enableTestAlertButton(false);
		}
	}
	
	private void clearAddressFields(){
		
	}
	
	private void saveAddress(){
		if(newEmailAddress){
			EmailAddress newaddress = new EmailAddress();
			newaddress.setAddress(emailPrefView.getEmailTextfield());
			newaddress.setActive(true);
			EMailServerController.getInstance().addEmailAddress(newaddress);
		}else{
			EmailAddress selectedAddress = emailPrefView.getSelectedEmailAddress();
			selectedAddress.setAddress(emailPrefView.getEmailTextfield());
		}
		emailPrefView.setAddressOptionPanel();
	}
	
	private void changeToPreferenceView(){
		view.removeAll();
		view.add(emailPrefView, BorderLayout.CENTER);
		view.updateUI();
	}
	
	class NewAddressListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			newEmailAddress = true;
			emailPrefView.setAddressModPanel();
		}
		
	}
	
	
	
	class SaveAddressListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			saveAddress();
		}
		
	}
	
	class EditAddressListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			newEmailAddress = false;
			emailPrefView.setEmailTextfield(emailPrefView.getSelectedEmailAddress().getAddress());
			emailPrefView.setAddressModPanel();
			
		}
		
	}
	
	class RemoveAddressListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			EmailAddress selectedAddress = emailPrefView.getSelectedEmailAddress();
			EMailServerController.getInstance().removeEmailAddress(selectedAddress);
		}
		
	}
	
	class CancelAddressListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			emailPrefView.clearAddressFields();
			emailPrefView.setAddressOptionPanel();
		}
		
	}
	
	class AddServerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			serverEditController = new EmailServerEditController();
			serverEditController.setCancelButtonListner(new CancelServerEditListener());
			serverEditController.setSaveButtonListener(new SaveServerEditListener());
			view.removeAll();
			view.add(serverEditController.getView(), BorderLayout.CENTER);
			view.updateUI();
		}
		
	}
	
	class EditServerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			serverEditController = new EmailServerEditController(	emailPrefView.getSelectedServerData());
			serverEditController.setCancelButtonListner(new CancelServerEditListener());
			serverEditController.setSaveButtonListener(new SaveServerEditListener());
			view.removeAll();
			view.add(serverEditController.getView(), BorderLayout.CENTER);
			view.updateUI();
		}
	}
	
	class RemoveServerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			EmailServerData selectedServer = emailPrefView.getSelectedServerData();
			EMailServerController.getInstance().removeEMailServer(selectedServer);
		}
		
	}
	
	class SaveServerEditListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(serverEditController.saveServerInputs()){
				changeToPreferenceView();
			}
		}
		
	}
	
	class CancelServerEditListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			changeToPreferenceView();
		}
		
	}
	
	class AddressListListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			boolean isSelected = emailPrefView.addressListIsSelected();
			emailPrefView.enableAddressEditButton(isSelected);
			emailPrefView.enableAddressRemoveButton(isSelected);
			
		}
		
	}
	
	
	class ServerListListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
				emailPrefView.enableServerEditButton(emailPrefView.serverListIsSelected());
				emailPrefView.enableServerRemoveButton(emailPrefView.serverListIsSelected());
		}
		
	}
	
	class AddressIsActiveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			compareAddressValue();
		}
	}
	
	
	class TestEmailAlertListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			EmailAlertTestThread alertTest = new EmailAlertTestThread();
			alertTest.start();
			
		}
		
	}
	
	class EmailAlertTestThread extends Thread{
		
		public void run(){
			emailPrefView.enableTestAlertButton(false);
			emailPrefView.setTestEmailStatus(1);
			boolean result = NotificationHandler.sendTestEmail("CCC_TestAlert" + new Date().toString(), "This is a Testmessage from the CCC_Client");
			if(result){
				emailPrefView.setTestEmailStatus(2);
				emailPrefView.enableTestAlertButton(true);
			}else{
				emailPrefView.setTestEmailStatus(3);
				emailPrefView.enableTestAlertButton(true);
			}
		}
	}
	
	
	class AddressTextFieldListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
			compareAddressValue();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			compareAddressValue();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			compareAddressValue();
			
		}
		
	}
	

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof ObserverObjectWrapper){
			ObserverObjectWrapper wrap = (ObserverObjectWrapper)arg1;
			if(wrap.getObject() instanceof EmailAddress){
				
				EmailAddress email = (EmailAddress)wrap.getObject();
				switch(wrap.getActionType()){
				case ADD: 	email.addObserver(this);
							emailPrefView.updateAddressList();
							clearAddressFields();
					break;
				case MODIFICATION:  emailPrefView.updateAddressList();
									compareAddressValue();
					break;
				case REMOVE: emailPrefView.removeAddressToList(email);
							 clearAddressFields();
					break;
				default:
					break;
				
				}
			} else if(wrap.getObject() instanceof EmailServerData){
				EmailServerData server = (EmailServerData)wrap.getObject();
				switch(wrap.getActionType()){
				case ADD: 	server.addObserver(this);
							emailPrefView.addServerToList(server);
					break;
				case MODIFICATION: emailPrefView.updateServerList();
					break;
				case REMOVE: emailPrefView.removeServerFromList(server);
					break;
				default:
					break;
				
				}
			}
		}
		enableAlertTest();
	}
	
	
}
