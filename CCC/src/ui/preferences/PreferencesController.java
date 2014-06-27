package ui.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.preferences.PreferencesHandler;


import ui.ColorScheme;
import ui.internationalization.Strings;

public class PreferencesController {
	private PreferencesView viewObj;
	private JTextField dbPathField;
	private JSpinner updateIntervalSpinner;
	private JComboBox<String> languageCombobox;
	private JTextField emailSenderField;
	private EmailPreferencesController emailPrefCont;
	private JPanel emailPrefView;
	private JRadioButton doubleScientificButton;
	private JRadioButton doubleDecimalButton;

	public PreferencesController() {
		this.viewObj = new PreferencesView();
		dbPathField = viewObj.getDBPathField();
		updateIntervalSpinner = viewObj.getUpdateIntervalSpinner();
		languageCombobox = viewObj.getLanguageComboBox();
		emailSenderField = viewObj.getEmailSenderField();
		doubleScientificButton = viewObj.getRadioScientific(); 
		doubleDecimalButton = viewObj.getRadioDecimal();
		
		this.initListeners();
		this.initFields();
		validateDBPath();
		validateEmailSender();
		initEmailPref();
	}

	private void initEmailPref() {
		emailPrefCont= new EmailPreferencesController();
		emailPrefView = emailPrefCont.getView();
		viewObj.addEmailPref(emailPrefView);
	}

	private void initFields() {
		dbPathField.setText(PreferencesHandler.getDbPath());
		updateIntervalSpinner.setValue(PreferencesHandler.getUpdateInterval());
		if (PreferencesHandler.getLanguage().equals("_de_DE")) {
			languageCombobox.setSelectedItem("Deutsch");
		} else {
			languageCombobox.setSelectedItem("English");
		}
		emailSenderField.setText(PreferencesHandler.getEmailSender());
		switch(PreferencesHandler.getRepresentation()){
			case PreferencesHandler.decimalRepresentation:
				doubleDecimalButton.setSelected(true);
				break;
			case PreferencesHandler.scientificRepresentation:
				doubleScientificButton.setSelected(true);
				break;
		}
	}

	private void validateDBPath() {
		String dbPath_inGui = dbPathField.getText();
		PreferencesHandler.setDbPath(dbPath_inGui);
		if (!PreferencesHandler.checkDBPathValid()) {
			dbPathField.setBackground(ColorScheme.INACTIVE_ORANGE);
		} else {
			dbPathField.setBackground(ColorScheme.ACTIVE_GREEN);
		}
	}
	
	private void validateEmailSender(){
		String emailSenderText_GUI = emailSenderField.getText();
		if(emailSenderText_GUI.length()>0){
			PreferencesHandler.setEmailSender(emailSenderText_GUI);
			emailSenderField.setBackground(ColorScheme.ACTIVE_GREEN);
		}else{
			emailSenderField.setBackground(ColorScheme.INACTIVE_ORANGE);
		}
	}

	private void initListeners() {
		updateIntervalSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				PreferencesHandler
						.setUpdateInterval((int) updateIntervalSpinner
								.getValue());
			}
		});
		
		doubleScientificButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreferencesHandler.setRepresentation(PreferencesHandler.scientificRepresentation);
			}
		});
		
		doubleDecimalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreferencesHandler.setRepresentation(PreferencesHandler.decimalRepresentation);
			}
		});

		dbPathField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent arg0) {
				validateDBPath();
			}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});

		emailSenderField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				validateEmailSender();
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});

		final JComboBox<String> combobox = viewObj.getLanguageComboBox();
		combobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (combobox.getSelectedItem().toString()) {
				case "German":
					PreferencesHandler.setLanguage("_de_DE");
					break;
				case "Deutsch":
					PreferencesHandler.setLanguage("_de_DE");
					break;	
				case "English":
					PreferencesHandler.setLanguage("_en_EN");
					break;
				case "Englisch":
					PreferencesHandler.setLanguage("_en_EN");
					break;					
				default:
					PreferencesHandler.setLanguage("_en_EN");
					break;
				}
			}
		});
	}

	public PreferencesView getPanel() {
		return viewObj;
	}
}
