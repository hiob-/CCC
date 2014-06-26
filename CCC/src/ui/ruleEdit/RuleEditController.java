package ui.ruleEdit;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import logic.modelController.RuleController;
import model.currency.CurrencyPair;
import model.rule.Rule;
import model.rule.Rule.RuleType;
import ui.ColorScheme;
import ui.internationalization.Strings;

/**
 * Controler for RuleEdit (UI)
 * 
 * @author Luca T�nnler, Oussama Zgheb
 * @version 1.1
 * */
public class RuleEditController {
	private RuleEditView viewObj;
	// protected RuleEditModel modelObj;

	private final boolean newRule;
	private Rule rule;
	private RuleType selectedRuleType;
	private final CurrencyPair currencyPair;

	/**
	 * Create new Rule
	 */
	public RuleEditController(final CurrencyPair Cp) {
		setViewObj(new RuleEditView());
		getViewObj().setBtnSaveText("Erstellen");
		newRule = true;
		currencyPair = Cp;

		initComponents();
		initListeners();

		setCurrencyLabel(currencyPair);
	}

	/**
	 * 
	 * @param pRule
	 *            Edit this Rule
	 */
	public RuleEditController(final Rule pRule) {
		setViewObj(new RuleEditView());
		rule = pRule;
		newRule = false;
		currencyPair = pRule.getCurrencyPair();

		initComponents();
		initListeners();
		initFields();
		setCurrencyLabel(rule.getCurrencyPair());
	}
	
	private void setCurrencyLabel(CurrencyPair cp){
		String desc= currencyPair.getBaseCurrency().getShortName()+" / "+currencyPair.getQuotedCurrency().getShortName();
		getViewObj().getCurrencyLabel().setText(desc);
	}
	

	/** Set Rule Values */
	private void initFields() {

		getViewObj().setSelectedRuleType(rule.getRuleType());
		getViewObj().setRuleValueTriggerText(String.valueOf(rule.getValueTarget()));
		getViewObj().setRuleDeltaTriggerText(String.valueOf(rule.getDelta()));


		switch (rule.getRuleType()) {
		case PriceDownDelta:
		case PriceUpDelta:
			getViewObj().setDeltaVisible(true);
			break;
		case PriceDownLimit:
		case PriceUpLimit:
			getViewObj().setDeltaVisible(false);
			break;
		}

		if (rule.isPopupActive()) {
			getViewObj().setChckbxPopUpEnabled(true);
		}
		if (rule.emailAlertActive()) {
			getViewObj().setChckbxEmailEnabled(true);
		}
	}

	/** Initialize Components */
	private void initComponents() {

		// Fill RuleComboBox
		getViewObj().fillRuleTypeCB();
		cbRuleTypeItemChanged();

		// Check PopUpAlert & Disable Selection
		getViewObj().setChckbxPopUpSelected(true);
	}

	/** Creates & assigns all Listeners for specific view Elements */
	private void initListeners() {

		// RuleType CB SL
		final ItemListener cbRuleTypeIL = new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				cbRuleTypeItemChanged();
			}
		};
		getViewObj().addRuleTypeCBIL(cbRuleTypeIL);

	}
	
	public void setSaveListener(ActionListener AL){
		getViewObj().addBtnSaveActionListener(AL);
	}
	
	public void setCancelListener(ActionListener AL){
		getViewObj().addBtnCancelActionListener(AL);
	}
	
	public void SaveError(){
		JOptionPane.showMessageDialog(null,
				Strings.getString("RuleEditController.validationMessage"),
				"CCC", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Sets the selected CurrencyPair status to subscribed & add it to the
	 * Library (subscribed list)
	 */
	public boolean doSave() {
		final RuleController ruleLib = RuleController.getInstance();
		if (validateInput()) {
			if (newRule) {
				rule = new Rule();
			}

			rule.setRuleType(selectedRuleType);
			rule.setCurrencyPair(currencyPair);
			rule.setActive(true);

			rule.setPopupActive(getViewObj().isChckbcPopUpSelected());
			rule.setEmail(getViewObj().isChckbcEmailSelected());
			
			rule.setValueTarget(Double.valueOf(getViewObj().getRuleValueTrigger().getText()));
			double deltaValue = (getViewObj().getRuleDeltaTrigger().getText().length()>0 && (selectedRuleType.equals(RuleType.PriceDownDelta) || selectedRuleType.equals(RuleType.PriceUpDelta))) ? Double.valueOf(getViewObj().getRuleDeltaTrigger().getText()) : 0;
			rule.setDelta(deltaValue);

			if (newRule) {
				ruleLib.addRule(rule);
				rule.triggerNew();
			}

			return true;
		}

		return false;
	}

	/** Validates the selected Input */
	private boolean validateInput() {
		// TODO refactor this, after se2 project, also keylistener-> validate after every input
		boolean error = false;
		if (selectedRuleType == null) {
			error = true;
			viewObj.cbRuleType.setBackground(ColorScheme.INACTIVE_ORANGE);
		} else {
			viewObj.cbRuleType.setBackground(Color.GRAY);
			if (getViewObj().getRuleValueTrigger().getText().isEmpty()) {
				error = true;
				viewObj.cbRuleType.setBackground(ColorScheme.INACTIVE_ORANGE);
			}else{
				viewObj.cbRuleType.setBackground(Color.GRAY);
			}
		}
		if((viewObj.getSelectedRuleType().equals(RuleType.PriceDownDelta)
				|| viewObj.getSelectedRuleType().equals(RuleType.PriceUpDelta))
			&& !viewObj.getRuleDeltaTrigger().getText().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")){
			error = true;
			viewObj.getRuleDeltaTrigger().setBackground(ColorScheme.INACTIVE_ORANGE);
		}else{
			viewObj.getRuleDeltaTrigger().setBackground(Color.WHITE);
		}
		if(!viewObj.getRuleValueTrigger().getText().matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")){
			error = true;
			viewObj.getRuleValueTrigger().setBackground(ColorScheme.INACTIVE_ORANGE);
		}else{
			viewObj.getRuleValueTrigger().setBackground(Color.WHITE);
		}
		return !error;
	}



	/**
	 * Called when the Selectin of cbRuleType changed. It will make the correct
	 * Limit/Delta Box visible
	 */
	private void cbRuleTypeItemChanged() {
		selectedRuleType = getViewObj().getSelectedRuleType();

		switch (selectedRuleType) {
		case PriceUpDelta:
		case PriceDownDelta:
			getViewObj().setDeltaVisible(true);
			break;
		case PriceDownLimit:
		case PriceUpLimit:
			getViewObj().setDeltaVisible(false);
			break;
		default:
			break;
		}
	}



	public RuleEditView getViewObj() {
		return viewObj;
	}

	public void setViewObj(RuleEditView viewObj) {
		this.viewObj = viewObj;
	}

}
