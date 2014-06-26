package ui.preferences;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import ui.internationalization.Strings;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import java.awt.Color;

public class PreferencesView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpinner updateIntervalSpinner;
	@SuppressWarnings("rawtypes")
	private JComboBox languageComboBox;
	private JTextField emailSenderTextField;
	private GridBagConstraints  gbc_EmailPrefPanel;
	private JTextField dbPathTextField;
	private JRadioButton doubleScientificButton;
	private JRadioButton doubleDecimalButton;


	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PreferencesView() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 5, 50, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 1, 0, 1, 0, 0, 1, 0, 10, 15, 25, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		
				JLabel lblRefreshTime = new JLabel(
						Strings.getString("PreferencesView.refreshTime")); //$NON-NLS-1$
				GridBagConstraints gbc_lblRefreshTime = new GridBagConstraints();
				gbc_lblRefreshTime.anchor = GridBagConstraints.WEST;
				gbc_lblRefreshTime.insets = new Insets(0, 0, 5, 0);
				gbc_lblRefreshTime.gridx = 1;
				gbc_lblRefreshTime.gridy = 0;
				add(lblRefreshTime, gbc_lblRefreshTime);

		updateIntervalSpinner = new JSpinner();

		updateIntervalSpinner.setModel(new SpinnerNumberModel(5, 3, 60, 1));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 1;
		add(updateIntervalSpinner, gbc_spinner);

		String[] languages = {
				Strings.getString("PreferencesView.lang_English"), Strings.getString("PreferencesView.lang_German") }; //$NON-NLS-1$ //$NON-NLS-2$
				
				JPanel panel_2 = new JPanel();
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.insets = new Insets(0, 0, 5, 0);
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 1;
				gbc_panel_2.gridy = 3;
				add(panel_2, gbc_panel_2);
				panel_2.setLayout(new GridLayout(0, 1, 0, 0));
				
				
				
				JLabel label = new JLabel(Strings.getString("PreferencesView.valueRep"));
				panel_2.add(label);
				
				doubleScientificButton = new JRadioButton(Strings.getString("PreferencesView.doubleScientific"));
				
				panel_2.add(doubleScientificButton);
				
				doubleDecimalButton = new JRadioButton(Strings.getString("PreferencesView.doubleDecimal"));
			
				panel_2.add(doubleDecimalButton);
				
			    ButtonGroup groupRep = new ButtonGroup();
			    groupRep.add(doubleDecimalButton);
			    groupRep.add(doubleScientificButton);
		
				JLabel lblLanguage = new JLabel(
						Strings.getString("PreferencesView.language")); //$NON-NLS-1$
				GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
				gbc_lblLanguage.anchor = GridBagConstraints.WEST;
				gbc_lblLanguage.insets = new Insets(0, 0, 5, 0);
				gbc_lblLanguage.gridx = 1;
				gbc_lblLanguage.gridy = 5;
				add(lblLanguage, gbc_lblLanguage);
		languageComboBox = new JComboBox(languages);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 6;
		add(languageComboBox, gbc_comboBox);
		
				JLabel lblDatabasePath = new JLabel(
						Strings.getString("PreferencesView.dbPath")); //$NON-NLS-1$
				GridBagConstraints gbc_lblDatabasePath = new GridBagConstraints();
				gbc_lblDatabasePath.anchor = GridBagConstraints.WEST;
				gbc_lblDatabasePath.insets = new Insets(0, 0, 5, 0);
				gbc_lblDatabasePath.gridx = 1;
				gbc_lblDatabasePath.gridy = 8;
				add(lblDatabasePath, gbc_lblDatabasePath);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 9;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 200, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		dbPathTextField = new JTextField();
		dbPathTextField.setText("");
		dbPathTextField.setColumns(20);
		GridBagConstraints gbc_dbPathTextField = new GridBagConstraints();
		gbc_dbPathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dbPathTextField.insets = new Insets(0, 0, 0, 5);
		gbc_dbPathTextField.gridx = 0;
		gbc_dbPathTextField.gridy = 0;
		panel_1.add(dbPathTextField, gbc_dbPathTextField);
		
				JLabel lblEmailSender = new JLabel(
						Strings.getString("PreferencesView.lblEmailSender")); //$NON-NLS-1$
				GridBagConstraints gbc_lblSenderNameFor = new GridBagConstraints();
				gbc_lblSenderNameFor.insets = new Insets(0, 0, 5, 0);
				gbc_lblSenderNameFor.anchor = GridBagConstraints.WEST;
				gbc_lblSenderNameFor.gridx = 1;
				gbc_lblSenderNameFor.gridy = 11;
				add(lblEmailSender, gbc_lblSenderNameFor);
				
				JPanel panel = new JPanel();
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.insets = new Insets(0, 0, 5, 0);
				gbc_panel.fill = GridBagConstraints.BOTH;
				gbc_panel.gridx = 1;
				gbc_panel.gridy = 12;
				add(panel, gbc_panel);
				GridBagLayout gbl_panel = new GridBagLayout();
				gbl_panel.columnWidths = new int[]{0, 0, 200, 0};
				gbl_panel.rowHeights = new int[]{0, 0};
				gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
				gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
				panel.setLayout(gbl_panel);
				
				emailSenderTextField = new JTextField();
				emailSenderTextField.setText("");
				emailSenderTextField.setColumns(20);
				GridBagConstraints gbc_emailSenderTextField = new GridBagConstraints();
				gbc_emailSenderTextField.insets = new Insets(0, 0, 0, 5);
				gbc_emailSenderTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_emailSenderTextField.gridx = 0;
				gbc_emailSenderTextField.gridy = 0;
				panel.add(emailSenderTextField, gbc_emailSenderTextField);
				
				
				JLabel label_1 = new JLabel(Strings.getString("PreferencesView.restartMessage"));
				label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
				label_1.setForeground(Color.BLACK);
				GridBagConstraints gbc_label_1 = new GridBagConstraints();
				gbc_label_1.anchor = GridBagConstraints.SOUTHWEST;
				gbc_label_1.insets = new Insets(0, 0, 5, 0);
				gbc_label_1.gridx = 1;
				gbc_label_1.gridy = 13;
				add(label_1, gbc_label_1);
				
				gbc_EmailPrefPanel = new GridBagConstraints();
				gbc_EmailPrefPanel.fill = GridBagConstraints.BOTH;
				gbc_EmailPrefPanel.gridx = 1;
				gbc_EmailPrefPanel.gridy = 14;

	}
	
	public void addEmailPref(JPanel panel){
		add(panel, gbc_EmailPrefPanel);
	}

	public JSpinner getUpdateIntervalSpinner() {
		return updateIntervalSpinner;
	}

	public JTextField getDBPathField() {
		return dbPathTextField;
	}

	public JRadioButton getRadioScientific() {
		return doubleScientificButton;
	}

	public JRadioButton getRadioDecimal() {
		return doubleDecimalButton;
	}

	@SuppressWarnings("unchecked")
	public JComboBox<String> getLanguageComboBox() {
		return languageComboBox;
	}
	
	public JTextField getEmailSenderField() {
		return emailSenderTextField;
	}

}
