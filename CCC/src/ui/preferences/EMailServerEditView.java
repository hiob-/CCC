package ui.preferences;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import ui.internationalization.Strings;


public class EMailServerEditView extends JPanel{
	
	/**
	 * @author Diego Etter
	 * @version 1.0
	 * 
	 * The Create and EditPanel for creating a new EmailServer
	 */
	private static final long serialVersionUID = 1L;

	private JLabel jLabel_username;
	private JLabel jLabel_password;
	private JLabel jLabel_hostaddress;
	private JLabel jLabel_port;
	
	private JTextField jTextfield_username;
	private JTextField jTextfield_password;
	private JTextField jTextfield_hostaddress;
	private JTextField jTextfield_port;
	
	private JRadioButton jRadioButton_nonkey;
	private JRadioButton jRadioButton_SSL;
	private JRadioButton jRadioButton_TLS;
	
	private JButton jButton_Save;
	private JButton jButton_Cancel;
	private JButton jButton_Test;
	
	private JLabel statusLight;
	
	public EMailServerEditView(){
		initComponents();
		initPanel();
	}
	
	
	private void initComponents() {
		jLabel_username = new JLabel(Strings.getString("EMailServerEditView.username")); //$NON-NLS-1$
		jLabel_password = new JLabel(Strings.getString("EMailServerEditView.password"));
		jLabel_hostaddress = new JLabel(Strings.getString("EMailServerEditView.hostaddress"));
		jLabel_port = new JLabel(Strings.getString("EMailServerEditView.port"));
		jTextfield_username = new JTextField();
		jTextfield_password = new JPasswordField();
		jTextfield_hostaddress = new JTextField();
		jTextfield_port = new JTextField();
		jRadioButton_nonkey = new JRadioButton(Strings.getString("EMailServerEditView.nonKey"));
		jRadioButton_SSL = new JRadioButton(Strings.getString("EMailServerEditView.SSL"));
		jRadioButton_TLS = new JRadioButton(Strings.getString("EMailServerEditView.TLS"));
		ButtonGroup encryptionGroup = new ButtonGroup();
		encryptionGroup.add(jRadioButton_nonkey);
		encryptionGroup.add(jRadioButton_SSL);
		encryptionGroup.add(jRadioButton_TLS);
		jRadioButton_nonkey.setSelected(true);
		jButton_Cancel = new JButton(Strings.getString("EMailServerEditView.cancel"));
		jButton_Save = new JButton(Strings.getString("EMailServerEditView.save"));
		jButton_Test = new JButton(Strings.getString("EMailServerEditView.test"));
		statusLight = new JLabel();
		statusLight.setBorder(BorderFactory.createLineBorder(Color.black));
		statusLight.setBackground(Color.white);
		statusLight.setOpaque(true);
		statusLight.setMinimumSize(new Dimension(20,20));
		

		
		
	}


	private void initPanel(){
		GridBagLayout emailLayout = new GridBagLayout();
		emailLayout.columnWidths = new int[]{20,100,100,100,100,20};
		emailLayout.columnWeights = new double[]{0.0,0.0,0.0,0.0,0.0,1.0};
		emailLayout.rowHeights = new int[]{20,20,20,20,20,20,20,20,15,20,20,20};
		emailLayout.rowWeights = new double[]{0.5,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.5};
		setLayout(emailLayout);
		
		GridBagConstraints usertext = new GridBagConstraints();
		usertext.insets = new Insets(0, 0, 5, 5);
		usertext.anchor = GridBagConstraints.SOUTHWEST;
		usertext.gridx = 1;
		usertext.gridy = 1;
		add(jLabel_username, usertext);
		
		GridBagConstraints userfield = new GridBagConstraints();
		userfield.insets = new Insets(0, 0, 5, 5);
		userfield.anchor = GridBagConstraints.WEST;
		userfield.fill = GridBagConstraints.BOTH;
		userfield.gridwidth = 4;
		userfield.gridx = 1;
		userfield.gridy = 2;
		add(jTextfield_username, userfield);
		
		GridBagConstraints passwordtext = new GridBagConstraints();
		passwordtext.insets = new Insets(0, 0, 5, 5);
		passwordtext.anchor = GridBagConstraints.SOUTHWEST;
		passwordtext.gridx = 1;
		passwordtext.gridy = 3;
		add(jLabel_password, passwordtext);
		
		GridBagConstraints passwordfield = new GridBagConstraints();
		passwordfield.insets = new Insets(0, 0, 5, 5);
		passwordfield.anchor = GridBagConstraints.WEST;
		passwordfield.fill = GridBagConstraints.BOTH;
		passwordfield.gridwidth = 4;
		passwordfield.gridx = 1;
		passwordfield.gridy = 4;
		add(jTextfield_password, passwordfield);
		
		GridBagConstraints hostaddresstext = new GridBagConstraints();
		hostaddresstext.insets = new Insets(0, 0, 5, 5);
		hostaddresstext.anchor = GridBagConstraints.SOUTHWEST;
		hostaddresstext.gridx = 1;
		hostaddresstext.gridy = 5;
		add(jLabel_hostaddress, hostaddresstext);
		
		GridBagConstraints hostaddressfield = new GridBagConstraints();
		hostaddressfield.insets = new Insets(0, 0, 5, 5);
		hostaddressfield.anchor = GridBagConstraints.WEST;
		hostaddressfield.fill = GridBagConstraints.BOTH;
		hostaddressfield.gridwidth = 4;
		hostaddressfield.gridx = 1;
		hostaddressfield.gridy = 6;
		add(jTextfield_hostaddress, hostaddressfield);
		
		GridBagConstraints nonkey = new GridBagConstraints();
		nonkey.insets = new Insets(0, 0, 5, 5);
		nonkey.anchor = GridBagConstraints.CENTER;
		//nonkey.fill = GridBagConstraints.BOTH;
		nonkey.gridx = 1;
		nonkey.gridy = 7;
		add(jRadioButton_nonkey, nonkey);
		
		GridBagConstraints ssl = new GridBagConstraints();
		ssl.insets = new Insets(0, 0, 5, 5);
		ssl.anchor = GridBagConstraints.CENTER;
		//ssl.fill = GridBagConstraints.BOTH;
		ssl.gridx = 2;
		ssl.gridy = 7;
		add(jRadioButton_SSL, ssl);
		
		GridBagConstraints tls = new GridBagConstraints();
		tls.insets = new Insets(0, 0, 5, 5);
		tls.anchor = GridBagConstraints.CENTER;
		//tls.fill = GridBagConstraints.BOTH;
		tls.gridx = 3;
		tls.gridy = 7;
		add(jRadioButton_TLS, tls);
		
		GridBagConstraints porttext = new GridBagConstraints();
		porttext.insets = new Insets(0, 0, 5, 5);
		porttext.anchor = GridBagConstraints.SOUTHWEST;
		porttext.gridx = 1;
		porttext.gridy = 8;
		add(jLabel_port, porttext);
		
		GridBagConstraints portfield = new GridBagConstraints();
		portfield.insets = new Insets(0, 0, 5, 5);
		portfield.anchor = GridBagConstraints.WEST;
		portfield.fill = GridBagConstraints.BOTH;
		portfield.gridwidth = 4;
		portfield.gridx = 1;
		portfield.gridy = 9;
		add(jTextfield_port, portfield);
		
		GridBagConstraints testButton = new GridBagConstraints();
		testButton.anchor = GridBagConstraints.CENTER;
		testButton.fill = GridBagConstraints.HORIZONTAL;
	//	testButton.insets = new Insets(10, 0, 5, 5);
		testButton.gridx = 1;
		testButton.gridy = 10;
		add(jButton_Test, testButton);
		
		GridBagConstraints teststatus = new GridBagConstraints();
		teststatus.fill = GridBagConstraints.BOTH;
		teststatus.insets = new Insets(0, 5, 0, 60);
		teststatus.gridx = 2;
		teststatus.gridy = 10;
		add(statusLight, teststatus);
		
		GridBagConstraints cancelButton = new GridBagConstraints();
		cancelButton.anchor = GridBagConstraints.CENTER;
		cancelButton.fill = GridBagConstraints.HORIZONTAL;
		//cancelButton.insets = new Insets(10, 0, 5, 5);
		cancelButton.gridx = 3;
		cancelButton.gridy = 10;
		add(jButton_Cancel, cancelButton);
		
		GridBagConstraints saveButton = new GridBagConstraints();
		saveButton.anchor = GridBagConstraints.CENTER;
		saveButton.fill = GridBagConstraints.HORIZONTAL;
		//saveButton.insets = new Insets(10, 0, 5, 5);
		saveButton.gridx = 4;
		saveButton.gridy = 10;
		add(jButton_Save, saveButton);
	}
	
	public void setCancelListener(ActionListener AL){
		jButton_Cancel.addActionListener(AL);
	}
	
	public void setSaveListener(ActionListener AL){
		jButton_Save.addActionListener(AL);
	}
	
	public void setTestListener(ActionListener AL){
		jButton_Test.addActionListener(AL);
	}
	
	public void setUsernameDocumentListener(DocumentListener DL){
		jTextfield_username.getDocument().addDocumentListener(DL);
	}
	
	public void setPasswordDocumentListener(DocumentListener DL){
		jTextfield_password.getDocument().addDocumentListener(DL);
	}
	
	public void setHostAddressDocumentListener(DocumentListener DL){
		jTextfield_hostaddress.getDocument().addDocumentListener(DL);
	}
	
	public void setPortDocumentListener(DocumentListener DL){
		jTextfield_port.getDocument().addDocumentListener(DL);
	}
	
	public void setUsernameText(String text){
		jTextfield_username.setText(text);
	}
	
	public void setPasswordText(String text){
		jTextfield_password.setText(text);
	}
	
	public void setHostAddressText(String text){
		jTextfield_hostaddress.setText(text);
	}
	
	public void setPortText(String text){
		jTextfield_port.setText(text);
	}
	
	public void setSSL(){
		jRadioButton_SSL.setSelected(true);
	}
	
	public void setTLS(){
		jRadioButton_TLS.setSelected(true);
	}
	
	public void setNONE(){
		jRadioButton_nonkey.setSelected(true);
	}
	
	public String getUsernameText(){
		return jTextfield_username.getText();
	}
	
	public String getPasswordText(){	
		return jTextfield_password.getText();
	}
	
	public String getHostAddressText(){
		return jTextfield_hostaddress.getText();
	}
	
	public String getPortText(){
		return jTextfield_port.getText();
	}
	
	public boolean nonencryptionSelected(){
		return jRadioButton_nonkey.isSelected();
	}
	
	public boolean sslSelected(){
		return jRadioButton_SSL.isSelected();
	}
	
	public boolean tlsSelected(){
		return jRadioButton_TLS.isSelected();
	}
	
	public void invalideUser(){
		jTextfield_username.setForeground(Color.red);
	//	jTextfield_username.setOpaque(true);
	}
	
	public void valideUser(){
		jTextfield_username.setForeground(Color.green);
	//	jTextfield_username.setOpaque(true);
	}
	
	public void invalidePassword(){
		jTextfield_password.setForeground(Color.red);
	//	jTextfield_password.setOpaque(true);
	}
	
	public void validePassword(){
		jTextfield_password.setForeground(Color.green);
	//	jTextfield_password.setOpaque(true);
	}
	
	public void invalideHostAddress(){
		jTextfield_hostaddress.setForeground(Color.red);
	//	jTextfield_hostaddress.setOpaque(true);
	}
	
	public void valideHostAddress(){
		jTextfield_hostaddress.setForeground(Color.green);
	//	jTextfield_hostaddress.setOpaque(true);
	}
	
	public void invalidePort(){
		jTextfield_port.setForeground(Color.red);
	//	jTextfield_port.setOpaque(true);
	}
	
	public void validePort(){
		jTextfield_port.setForeground(Color.green);
	//	jTextfield_port.setOpaque(true);
	}
	
	public void validateSaveButton(boolean validate){
		jButton_Save.setEnabled(validate);
	}
	
	public void neutralTextfields(){
		jTextfield_username.setForeground(Color.black);
		jTextfield_password.setForeground(Color.black);
		jTextfield_hostaddress.setForeground(Color.black);
		jTextfield_port.setForeground(Color.black);
	}
	
	public void serverTestOK(){
		statusLight.setBackground(Color.green);
		jButton_Test.setEnabled(true);
		updateUI();
	}
	
	public void serverTestWait(){
		statusLight.setBackground(Color.YELLOW);
		jButton_Test.setEnabled(false);
		updateUI();
	}
	
	public void serverTestFail(){
		statusLight.setBackground(Color.red);
		jButton_Test.setEnabled(true);
		updateUI();
	}
	
	public void enableSaveButton(boolean enable){
		jButton_Save.setEnabled(enable);
	}
	
	


}
