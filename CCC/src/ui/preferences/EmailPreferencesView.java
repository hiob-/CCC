package ui.preferences;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import model.email.EmailAddress;
import model.email.EmailServerData;
import ui.ColorScheme;
import ui.internationalization.Strings;

public class EmailPreferencesView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel jPanel_AddressOptionButtons;
	private JPanel jPanel_AddressModification;
	private JPanel jPanel_AddressModPlaceholder;
	
	private JLabel jLabel_EmailAddresses;
	private JLabel jLabel_EmailServers;
	
	private JList<EmailServerData> jList_EmailServers;
	private JTable jTable_EmailAddresses;
	
	
	DefaultListModel<EmailAddress> addressListmodel;
	EmailAddressTableModel addressTableModel;
	DefaultListModel<EmailServerData> serverListmodel;
	
	private JScrollPane jScrollPane_EmailAddresses;
	private JScrollPane jScrollPane_EMailServers;

	private JTextField jTextField_EmailAddress;

	private JButton jButton_NewEmailAddress;
	private JButton jButton_EditEmailAddress;
	private JButton jButton_RemoveEmailAddress;
	
	private JButton jButton_AddEmailAddress;
	private JButton jButton_SaveEmailAddress;
	private JButton jButton_CancelEmailAddress;
	
	
	private JButton jButton_AddEmailServer;
	private JButton jButton_EditEmailServer;
	private JButton jButton_RemoveEmailServer;
	private JButton jButton_TestEmailAlert;

	private JCheckBox jCheckBox_IsActiveEmailAddress;
	private JPanel jPanel_EmailTestStatus;
	
	public EmailPreferencesView(){
		initComponents();
		initView();
	}
	
	
	private void initComponents(){
		jLabel_EmailAddresses = new JLabel(Strings.getString("EMailPreferencesView.emailAddressText"));
		jLabel_EmailServers = new JLabel(Strings.getString("EMailPreferencesView.emailServerText"));
		jButton_AddEmailAddress = new JButton(Strings.getString("EMailPreferencesView.addEmailAddress"));
		jButton_SaveEmailAddress = new JButton(Strings.getString("EMailPreferencesView.saveEmailAddress"));
		jButton_RemoveEmailAddress = new JButton(Strings.getString("EMailPreferencesView.removeEmailAddress"));
		jButton_RemoveEmailAddress.setEnabled(false);
		jButton_NewEmailAddress = new JButton(Strings.getString("EMailPreferencesView.newEmailAddress"));
		jButton_CancelEmailAddress = new JButton(Strings.getString("EMailPreferencesView.cancelEmailAddress"));
		jButton_EditEmailAddress = new JButton(Strings.getString("EMailPreferencesView.editEmailAddress"));
		jButton_EditEmailAddress.setEnabled(false);
		jButton_AddEmailServer = new JButton(Strings.getString("EMailPreferencesView.addEmailServer"));
		jButton_RemoveEmailServer = new JButton(Strings.getString("EMailPreferencesView.removeEmailServer"));
		jButton_EditEmailServer = new JButton(Strings.getString("EMailPreferencesView.editEmailServer"));
		jButton_TestEmailAlert = new JButton(Strings.getString("EMailPreferencesView.testEmailAlert"));
		jButton_TestEmailAlert.setEnabled(false);
		jCheckBox_IsActiveEmailAddress = new JCheckBox(Strings.getString("EMailPreferencesView.isActiveEmailAddress"));
		jTextField_EmailAddress = new JTextField();
		jScrollPane_EmailAddresses = new JScrollPane();
		jScrollPane_EMailServers = new JScrollPane();
		jPanel_AddressModPlaceholder = new JPanel(new BorderLayout());
		initAddressOptionPanel();
		initAddressModPanel();
	}
	
	private void initAddressOptionPanel(){
		GridBagLayout optionlayout = new GridBagLayout();
		optionlayout.columnWidths = new int[]{100,100,100,0};
		optionlayout.columnWeights = new double[]{0.0,0.0,0.0,1.0};
		optionlayout.rowHeights = new int[]{30,0};
		optionlayout.rowWeights = new double[]{0.0,1.0};
		jPanel_AddressOptionButtons = new JPanel(optionlayout);
		
		GridBagConstraints newaddrbtn = new GridBagConstraints();
		newaddrbtn.anchor = GridBagConstraints.CENTER;
		newaddrbtn.fill = GridBagConstraints.BOTH;
		newaddrbtn.gridx = 0;
		newaddrbtn.gridy = 0;
		jPanel_AddressOptionButtons.add(jButton_NewEmailAddress, newaddrbtn);
		
		GridBagConstraints editaddrbtn = new GridBagConstraints();
		editaddrbtn.anchor = GridBagConstraints.CENTER;
		editaddrbtn.fill = GridBagConstraints.BOTH;
		editaddrbtn.gridx = 1;
		editaddrbtn.gridy = 0;
		jPanel_AddressOptionButtons.add(jButton_EditEmailAddress, editaddrbtn);
		
		GridBagConstraints deladdrbtn = new GridBagConstraints();
		deladdrbtn.anchor = GridBagConstraints.CENTER;
		deladdrbtn.fill = GridBagConstraints.BOTH;
		deladdrbtn.gridx = 2;
		deladdrbtn.gridy = 0;
		jPanel_AddressOptionButtons.add(jButton_RemoveEmailAddress, deladdrbtn);
	}
	
	private void initAddressModPanel(){
		GridBagLayout modlayout = new GridBagLayout();
		modlayout.columnWidths = new int[]{100,100,100,0};
		modlayout.columnWeights = new double[]{0.0,0.0,0.0,1.0};
		modlayout.rowHeights = new int[]{30,30,0};
		modlayout.rowWeights = new double[]{0.0,0.0,1.0};
		jPanel_AddressModification = new JPanel(modlayout);
		
		GridBagConstraints addressline = new GridBagConstraints();
		addressline.anchor = GridBagConstraints.CENTER;
		addressline.insets = new Insets(10, 0, 0, 0);
		addressline.fill = GridBagConstraints.BOTH;
		addressline.gridwidth = 3;
		addressline.gridx = 0;
		addressline.gridy = 0;
		jPanel_AddressModification.add(jTextField_EmailAddress, addressline);
		
		GridBagConstraints saveaddrbtn = new GridBagConstraints();
		saveaddrbtn.anchor = GridBagConstraints.CENTER;
		saveaddrbtn.fill = GridBagConstraints.BOTH;
		saveaddrbtn.gridx = 0;
		saveaddrbtn.gridy = 1;
		jPanel_AddressModification.add(jButton_SaveEmailAddress, saveaddrbtn);
		
		GridBagConstraints canceladdbtn = new GridBagConstraints();
		canceladdbtn.anchor = GridBagConstraints.CENTER;
		canceladdbtn.fill = GridBagConstraints.BOTH;
		canceladdbtn.gridx = 1;
		canceladdbtn.gridy = 1;
		jPanel_AddressModification.add(jButton_CancelEmailAddress, canceladdbtn);
	}
	
	private void initView(){
		GridBagLayout emailPrefLayout = new GridBagLayout();
		emailPrefLayout.columnWidths = new int[]{20,100,100,100,25,100,100,100,20};
		emailPrefLayout.columnWeights = new double[]{0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,1.0};
		emailPrefLayout.rowHeights = new int[]{20,30,100,30,30,30,20};
		emailPrefLayout.rowWeights = new double[]{0.0,0.0,1.0,0.0,0.0,1.0,0.0};
		setLayout(emailPrefLayout);
		
		GridBagConstraints addresslabel = new GridBagConstraints();
		addresslabel.insets = new Insets(0, 0, 5, 5);
		addresslabel.anchor = GridBagConstraints.SOUTHWEST;
		addresslabel.gridwidth = 3;
		addresslabel.gridx = 1;
		addresslabel.gridy = 1;
		add(jLabel_EmailAddresses, addresslabel);
		
		GridBagConstraints serverlabel = new GridBagConstraints();
		serverlabel.insets = new Insets(0, 0, 5, 5);
		serverlabel.anchor = GridBagConstraints.SOUTHWEST;
		serverlabel.gridwidth = 3;
		serverlabel.gridx = 5;
		serverlabel.gridy = 1;
		add(jLabel_EmailServers, serverlabel);
	
		GridBagConstraints addressList = new GridBagConstraints();
		addressList.insets = new Insets(0, 0, 5, 5);
		addressList.anchor = GridBagConstraints.CENTER;
		addressList.fill = GridBagConstraints.BOTH;
		addressList.gridwidth = 3;
		addressList.gridx = 1;
		addressList.gridy = 2;
		add(jScrollPane_EmailAddresses, addressList);
		
		GridBagConstraints addressmod = new GridBagConstraints();
		addressmod.insets = new Insets(0, 0, 5, 5);
		addressmod.anchor = GridBagConstraints.CENTER;
		addressmod.fill = GridBagConstraints.BOTH;
		addressmod.gridheight = 3;
		addressmod.gridwidth = 3;
		addressmod.gridx = 1;
		addressmod.gridy = 3;
		add(jPanel_AddressModPlaceholder, addressmod);
		
		GridBagConstraints serverList = new GridBagConstraints();
		serverList.insets = new Insets(0, 0, 5, 5);
		serverList.anchor = GridBagConstraints.CENTER;
		serverList.fill = GridBagConstraints.BOTH;
		serverList.gridwidth = 3;
		serverList.gridx = 5;
		serverList.gridy = 2;
		add(jScrollPane_EMailServers, serverList);
		
		GridBagConstraints delservbtn = new GridBagConstraints();
		delservbtn.insets = new Insets(0, 0, 5, 5);
		delservbtn.anchor = GridBagConstraints.CENTER;
		delservbtn.fill = GridBagConstraints.BOTH;
		delservbtn.gridx = 7;
		delservbtn.gridy = 3;
		add(jButton_RemoveEmailServer, delservbtn);
		
		GridBagConstraints addservbtn = new GridBagConstraints();
		addservbtn.insets = new Insets(0, 0, 5, 5);
		addservbtn.anchor = GridBagConstraints.CENTER;
		addservbtn.fill = GridBagConstraints.BOTH;
		addservbtn.gridx = 5;
		addservbtn.gridy = 3;
		add(jButton_AddEmailServer, addservbtn);
		
		GridBagConstraints editservbtn = new GridBagConstraints();
		editservbtn.insets = new Insets(0, 0, 5, 5);
		editservbtn.anchor = GridBagConstraints.CENTER;
		editservbtn.fill = GridBagConstraints.BOTH;
		editservbtn.gridx = 6;
		editservbtn.gridy = 3;
		add(jButton_EditEmailServer, editservbtn);
		
		jPanel_EmailTestStatus = new JPanel();
		GridBagConstraints gbc_jPanel_EmailTestStatus = new GridBagConstraints();
		gbc_jPanel_EmailTestStatus.insets = new Insets(0, 0, 5, 5);
		gbc_jPanel_EmailTestStatus.fill = GridBagConstraints.BOTH;
		gbc_jPanel_EmailTestStatus.gridx = 4;
		gbc_jPanel_EmailTestStatus.gridy = 5;
		add(jPanel_EmailTestStatus, gbc_jPanel_EmailTestStatus);
		
		GridBagConstraints testalertbtn = new GridBagConstraints();
		testalertbtn.insets = new Insets(0, 0, 5, 5);
		testalertbtn.anchor = GridBagConstraints.CENTER;
		testalertbtn.fill = GridBagConstraints.BOTH;
		testalertbtn.gridwidth = 3;
		testalertbtn.gridx = 5;
		testalertbtn.gridy = 5;
		add(jButton_TestEmailAlert, testalertbtn);
	}
	
	public void clearfields(){
		jTextField_EmailAddress.setText("");
	}
	
	public void setNewEmailAddressListener(ActionListener AL){
		jButton_NewEmailAddress.addActionListener(AL);
	}
	
	public void setEditEmainAddressListener(ActionListener AL){
		jButton_EditEmailAddress.addActionListener(AL);
	}

	public void setAddEmailAddressListener(ActionListener AL){
		jButton_AddEmailAddress.addActionListener(AL);
	}
	
	public void setSaveEmailAddressListener(ActionListener AL){
		jButton_SaveEmailAddress.addActionListener(AL);;
	}
	
	public void setCancelEmailAddressListener(ActionListener AL){
		jButton_CancelEmailAddress.addActionListener(AL);
	}
	
	public void setRemoveEmailAddressListener(ActionListener AL){
		jButton_RemoveEmailAddress.addActionListener(AL);;
	}
	
	public void setAddServerListener(ActionListener AL){
		jButton_AddEmailServer.addActionListener(AL);;
	}
	
	public void setEditServerListener(ActionListener AL){
		jButton_EditEmailServer.addActionListener(AL);;
	}
	
	public void setRemoveServerListener(ActionListener AL){
		jButton_RemoveEmailServer.addActionListener(AL);;
	}
	
	public void setTestAlertListener(ActionListener AL){
		jButton_TestEmailAlert.addActionListener(AL);
	}
	public void setAddressTableListener(ListSelectionListener LS){
		jTable_EmailAddresses.getSelectionModel().addListSelectionListener(LS);
	}
	
	public void setAddressTextFieldListener(DocumentListener DL){
		jTextField_EmailAddress.getDocument().addDocumentListener(DL);
	}
	
	
	public void setServerListListener(ListSelectionListener LS){
		jList_EmailServers.addListSelectionListener(LS);
	}
	
	public void setAddressTable(List<EmailAddress> emailList){
		addressTableModel = new EmailAddressTableModel(emailList);
		jTable_EmailAddresses = new JTable(addressTableModel);
		jTable_EmailAddresses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPane_EmailAddresses.setViewportView(jTable_EmailAddresses);
		updateUI();
	}
	
	public void addAddressToList(EmailAddress address){
		addressTableModel.addEmailAddress(address);
	}
	
	public void removeAddressToList(EmailAddress address){
		addressTableModel.removeEmailAddress(address);
		jTable_EmailAddresses.clearSelection();
	}
	
	public void updateAddressList(){
		addressTableModel.fireTableDataChanged();
		jScrollPane_EmailAddresses.updateUI();
	}
	
	public void clearAddressFields(){
		jTextField_EmailAddress.setText("");
		jTable_EmailAddresses.clearSelection();
	}
	
	public void setServerList(List<EmailServerData> serverList){
		serverListmodel = new DefaultListModel<EmailServerData>();
		for(EmailServerData server: serverList){
			serverListmodel.addElement(server);
		}
		jList_EmailServers = new JList<EmailServerData>(serverListmodel);
		jList_EmailServers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList_EmailServers.setLayoutOrientation(JList.VERTICAL);
		jScrollPane_EMailServers.setViewportView(jList_EmailServers);
	}
	
	public void addServerToList(EmailServerData server){
		serverListmodel.addElement(server);
	}
	
	public void removeServerFromList(EmailServerData server){
		serverListmodel.removeElement(server);
		jList_EmailServers.clearSelection();
	}
	
	public void updateServerList(){
		jList_EmailServers.updateUI();
		jScrollPane_EMailServers.updateUI();
	}
	
	public void setEmailTextfield(String address){
		jTextField_EmailAddress.setText(address);
	}
	
	public String getEmailTextfield(){
		return jTextField_EmailAddress.getText();
	}
	
	public void setActive(boolean active){
		jCheckBox_IsActiveEmailAddress.setSelected(active);
	}
	
	public boolean getActive(){
		return jCheckBox_IsActiveEmailAddress.isSelected();
	}
	
	public void setActiveListener(ActionListener AL){
		jCheckBox_IsActiveEmailAddress.addActionListener(AL);
	}
	
	public EmailServerData getSelectedServerData(){
		return jList_EmailServers.getSelectedValue();
	}
	
	public EmailAddress getSelectedEmailAddress(){
		int row = jTable_EmailAddresses.getSelectedRow();
		return addressTableModel.getEmailAddressAt(row);
	}
	
	public void enableServerEditButton(boolean enable){
		jButton_EditEmailServer.setEnabled(enable);
	}
	
	public void enableServerRemoveButton(boolean enable){
		jButton_RemoveEmailServer.setEnabled(enable);
	}
	
	public void enableAddressSaveButton(boolean enable){
		jButton_SaveEmailAddress.setEnabled(enable);
	}
	
	public void enableAddressEditButton(boolean enable){
		jButton_EditEmailAddress.setEnabled(enable);
	}
	
	public void enableAddressRemoveButton(boolean enable){
		jButton_RemoveEmailAddress.setEnabled(enable);
	}
	
	public boolean addressListIsSelected(){
		return jTable_EmailAddresses.getSelectedRow()>-1;
	}
	
	public boolean serverListIsSelected(){
		return jList_EmailServers.getSelectedIndex()>-1;
	}

	public void enableTestAlertButton(boolean enable){
		jButton_TestEmailAlert.setEnabled(enable);
	}
	
	public void setAddressOptionPanel(){
		jPanel_AddressModPlaceholder.removeAll();
		jPanel_AddressModPlaceholder.add(jPanel_AddressOptionButtons, BorderLayout.CENTER);
		updateUI();
	}
	
	public void setAddressModPanel(){
		jPanel_AddressModPlaceholder.removeAll();
		jPanel_AddressModPlaceholder.add(jPanel_AddressModification, BorderLayout.CENTER);
		updateUI();
	}
	
	/**
	 * pState: 0=nothing, 1 = waiting, 2 = success, 3 = error
	 * */
	public void setTestEmailStatus(int pState){
		Color backgroundColor = null;
		switch(pState){
		case 1:
			backgroundColor = ColorScheme.GOLD;
			break;
		case 2:
			backgroundColor = ColorScheme.ACTIVE_GREEN;
			break;
		case 3:
			backgroundColor = ColorScheme.INACTIVE_ORANGE;
			break;
		case 0:
		default:
			backgroundColor = Color.gray;
			break;
		}
		jPanel_EmailTestStatus.setBackground(backgroundColor);
	}
}
