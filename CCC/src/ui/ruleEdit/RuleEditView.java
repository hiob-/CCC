package ui.ruleEdit;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import model.rule.Rule.RuleType;

import ui.internationalization.Strings;

/**
 * View for RuleEdit
 * 
 * @author Luca Tännler
 * @version 1.0
 * */
public class RuleEditView extends JPanel {
	private static final long serialVersionUID = 1L;
	protected JTextField ruleValueTrigger;
	protected JTextField ruleDeltaTrigger;
	protected JComboBox<RuleType> cbRuleType;
	protected JPanel alertPanel;
	protected JPanel ruleTypePanel;
	protected JCheckBox chckbxEmail;
	protected JCheckBox chckbxPopup;
	protected JPanel buttonPanel;
	protected JButton btnCancel;
	protected JButton btnSave;
	private JLabel baseCurrencyLabel;
	private JLabel deltaInfoLabel;

	/**
	 * Create the panel.
	 */
	public RuleEditView() {
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 360, 300, 0 };
		gridBagLayout.rowHeights = new int[] { 216, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		ruleTypePanel = new JPanel();
		final GridBagConstraints gbc_ruleTypePanel = new GridBagConstraints();
		gbc_ruleTypePanel.fill = GridBagConstraints.BOTH;
		gbc_ruleTypePanel.insets = new Insets(10, 10, 10, 10);
		gbc_ruleTypePanel.gridx = 0;
		gbc_ruleTypePanel.gridy = 0;
		add(ruleTypePanel, gbc_ruleTypePanel);
		final GridBagLayout gbl_ruleTypePanel = new GridBagLayout();
		gbl_ruleTypePanel.columnWidths = new int[] { 74, 67, 120, 0 };
		gbl_ruleTypePanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_ruleTypePanel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_ruleTypePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		ruleTypePanel.setLayout(gbl_ruleTypePanel);

		final JLabel lblRuletype = new JLabel(
				Strings.getString("RuleEditView.ruleType")); //$NON-NLS-1$
		final GridBagConstraints gbc_lblRuletype = new GridBagConstraints();
		gbc_lblRuletype.anchor = GridBagConstraints.WEST;
		gbc_lblRuletype.insets = new Insets(0, 0, 5, 5);
		gbc_lblRuletype.gridx = 1;
		gbc_lblRuletype.gridy = 0;
		ruleTypePanel.add(lblRuletype, gbc_lblRuletype);
		
		baseCurrencyLabel = new JLabel("BASE_CUR");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		ruleTypePanel.add(baseCurrencyLabel, gbc_label);

		cbRuleType = new JComboBox<RuleType>();
		final GridBagConstraints gbc_cbRuleType = new GridBagConstraints();
		gbc_cbRuleType.insets = new Insets(0, 0, 5, 5);
		gbc_cbRuleType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbRuleType.gridx = 1;
		gbc_cbRuleType.gridy = 1;
		ruleTypePanel.add(cbRuleType, gbc_cbRuleType);
		
		ruleValueTrigger = new JTextField();
		GridBagConstraints gbc_ruleValueTrigger = new GridBagConstraints();
		gbc_ruleValueTrigger.insets = new Insets(0, 0, 5, 0);
		gbc_ruleValueTrigger.gridx = 2;
		gbc_ruleValueTrigger.gridy = 1;
		ruleTypePanel.add(ruleValueTrigger, gbc_ruleValueTrigger);
		ruleValueTrigger.setColumns(10);
		
		deltaInfoLabel = new JLabel("Delta (%):");
		GridBagConstraints gbc_label1 = new GridBagConstraints();
		gbc_label1.insets = new Insets(0, 0, 5, 5);
		gbc_label1.anchor = GridBagConstraints.EAST;
		gbc_label1.gridx = 1;
		gbc_label1.gridy = 2;
		ruleTypePanel.add(deltaInfoLabel, gbc_label1);
		
		ruleDeltaTrigger= new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		ruleTypePanel.add(ruleDeltaTrigger, gbc_textField);
		ruleDeltaTrigger.setColumns(10);

		alertPanel = new JPanel();
		final GridBagConstraints gbc_alertPanel = new GridBagConstraints();
		gbc_alertPanel.insets = new Insets(10, 10, 10, 10);
		gbc_alertPanel.fill = GridBagConstraints.BOTH;
		gbc_alertPanel.gridx = 1;
		gbc_alertPanel.gridy = 0;
		add(alertPanel, gbc_alertPanel);
		final GridBagLayout gbl_alertPanel = new GridBagLayout();
		gbl_alertPanel.columnWidths = new int[] { 0, 0 };
		gbl_alertPanel.rowHeights = new int[] { 0, 0, 0, 52, 0 };
		gbl_alertPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_alertPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		alertPanel.setLayout(gbl_alertPanel);

		final JLabel lblAlerts = new JLabel(
				Strings.getString("RuleEditView.alerts")); //$NON-NLS-1$
		final GridBagConstraints gbc_lblAlerts = new GridBagConstraints();
		gbc_lblAlerts.anchor = GridBagConstraints.WEST;
		gbc_lblAlerts.insets = new Insets(0, 0, 5, 0);
		gbc_lblAlerts.gridx = 0;
		gbc_lblAlerts.gridy = 0;
		alertPanel.add(lblAlerts, gbc_lblAlerts);

		chckbxPopup = new JCheckBox(Strings.getString("RuleEditView.popUp")); //$NON-NLS-1$
		final GridBagConstraints gbc_chckbxPopup = new GridBagConstraints();
		gbc_chckbxPopup.anchor = GridBagConstraints.WEST;
		gbc_chckbxPopup.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxPopup.gridx = 0;
		gbc_chckbxPopup.gridy = 1;
		alertPanel.add(chckbxPopup, gbc_chckbxPopup);

		chckbxEmail = new JCheckBox(Strings.getString("RuleEditView.email")); //$NON-NLS-1$
		final GridBagConstraints gbc_chckbxEmail = new GridBagConstraints();
		gbc_chckbxEmail.anchor = GridBagConstraints.WEST;
		gbc_chckbxEmail.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxEmail.gridx = 0;
		gbc_chckbxEmail.gridy = 2;
		alertPanel.add(chckbxEmail, gbc_chckbxEmail);
		
				buttonPanel = new JPanel();
				GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
				gbc_buttonPanel.anchor = GridBagConstraints.WEST;
				gbc_buttonPanel.gridx = 0;
				gbc_buttonPanel.gridy = 3;
				alertPanel.add(buttonPanel, gbc_buttonPanel);
				final FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
				flowLayout.setAlignment(FlowLayout.RIGHT);
				
						btnCancel = new JButton(Strings.getString("RuleEditView.close")); //$NON-NLS-1$
						buttonPanel.add(btnCancel);
						
								btnSave = new JButton(Strings.getString("RuleEditView.save")); //$NON-NLS-1$
								buttonPanel.add(btnSave);
	}

	public JLabel getCurrencyLabel() {
		return baseCurrencyLabel;
	}

	public JTextField getRuleValueTrigger() {
		return ruleValueTrigger;
	}

	public void setRuleValueTriggerText(String trigger) {
		this.ruleValueTrigger.setText(trigger);
	}
	
	public void setRuleDeltaTriggerText(String trigger) {
		this.ruleDeltaTrigger.setText(trigger);
	}


	/* cbRuleType */
	public void fillRuleTypeCB() {
		cbRuleType.removeAllItems();
		for (final RuleType rt : RuleType.values()) {
			cbRuleType.addItem(rt);
		}
	}

	public void setSelectedRuleType(final RuleType pRuleType) {
		cbRuleType.setSelectedItem(pRuleType);
	}

	public void addRuleTypeCBIL(final ItemListener pItemListener) {
		cbRuleType.addItemListener(pItemListener);
	}

	public RuleType getSelectedRuleType() {
		return (RuleType) cbRuleType.getSelectedItem();
	}


	public void setDeltaVisible(boolean flag){
		ruleDeltaTrigger.setVisible(flag);
		deltaInfoLabel.setVisible(flag);
	}

	/* chckbxPopUp */
	public void setChckbxPopUpSelected(final boolean pSelected) {
		chckbxPopup.setSelected(pSelected);
	}

	public boolean isChckbcPopUpSelected() {
		return chckbxPopup.isSelected();
	}

	public void setChckbxPopUpEnabled(final boolean pEnabled) {
		chckbxPopup.setEnabled(pEnabled);
	}

	/* chckbxEmail */
	public void setChckbxEmailSelected(final boolean pSelected) {
		chckbxEmail.setSelected(pSelected);
	}

	public boolean isChckbcEmailSelected() {
		return chckbxEmail.isSelected();
	}

	public void setChckbxEmailEnabled(final boolean pEnabled) {
		chckbxEmail.setEnabled(pEnabled);
	}

	public void addChckbcEmailItemListener(final ItemListener pItemListener) {
		chckbxEmail.addItemListener(pItemListener);
	}

	/* btnCancel */
	public void addBtnCancelActionListener(final ActionListener pActionListener) {
		btnCancel.addActionListener(pActionListener);
	}

	/* btnSave */
	public void addBtnSaveActionListener(final ActionListener pActionListener) {
		btnSave.addActionListener(pActionListener);
	}

	public void setBtnSaveText(final String pText) {
		btnSave.setText(pText);
	}



	public JTextComponent getRuleDeltaTrigger() {
		return ruleDeltaTrigger;
	}
}
