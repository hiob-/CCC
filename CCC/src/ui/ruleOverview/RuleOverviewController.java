package ui.ruleOverview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import logic.modelController.RuleController;
import model.ObserverObjectWrapper;
import model.currency.CurrencyPair;
import model.rule.Rule;

/**
 * Controller for Rule Overview (UI)
 * 
 * @author Oussama Zgheb
 * @version 1.1
 * */

public class RuleOverviewController implements Observer {

	private RuleOverview ruleOverview;
	private CurrencyPair cP;
	private ArrayList<Rule> CurrPairRuleList;

	/**
	 * Gets all rules from RuleLib
	 */
	public RuleOverviewController() {
		initView();
		initListeners();
		this.cP = null;
	}

	/**
	 * @param RuleOverview
	 *            for one CurrencyPair
	 */
	public RuleOverviewController(CurrencyPair cP) {
		this.cP = cP;
		cP.addObserver(this);
		RuleController.getInstance().addObserver(this);
		initView();
		initListeners();

	}

	private void initListeners() {
		ruleOverview.getButton_RemoveRule().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						RuleController.getInstance().removeRule(
								getSelectedRule());
					}
				});

		// Deactivate the open button if no row is selected
		ListSelectionListener tableListener = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (ruleOverview.getRuleOverviewTable().getSelectedRow() <= -1) {
					setButtonStates(false);
				} else {
					setButtonStates(true);
				}
			}
		};
		ruleOverview.getRuleOverviewTable().getSelectionModel()
				.addListSelectionListener(tableListener);

	}

	private void setButtonStates(boolean state) {
		ruleOverview.getButton_RemoveRule().setEnabled(state);
		ruleOverview.getButton_EditRule().setEnabled(state);
	}

	public Rule getSelectedRule() {
		int selRow = ruleOverview.getRuleOverviewTable().getSelectedRow();
		return ruleOverview.getRuleOverviewTableModel().getRuleAt(selRow);
	}

	private void initView() {
		ruleOverview = new RuleOverview();
		CurrPairRuleList = new ArrayList<Rule>();

		CurrPairRuleList = RuleController.getInstance()
				.getRuleListByCurrencyPair(cP);

		for (Rule rule : CurrPairRuleList) {
			ruleOverview.getRuleOverviewTableModel().addRule(rule);
			rule.addObserver(this);
		}
		setButtonStates(false);
	}

	public void setNewRuleListener(ActionListener AL){
		ruleOverview.getButton_NewRule().addActionListener(AL);
	}
	
	public void setEditRuleListener(ActionListener AL){
		ruleOverview.getButton_EditRule().addActionListener(AL);
	}

	public void DeleteObserver() {
		cP.deleteObserver(this);
		RuleController.getInstance().deleteObserver(this);
		for (Rule rule : CurrPairRuleList) {
			rule.deleteObserver(this);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof ObserverObjectWrapper) {
			ObserverObjectWrapper wrap = (ObserverObjectWrapper) arg;
			if (wrap.getObject() instanceof Rule) {
				Rule r = (Rule) wrap.getObject();
				switch (wrap.getActionType()) {
				case ADD:
					ruleOverview.getRuleOverviewTableModel().addRule(r);
					break;
				case REMOVE:
					ruleOverview.getRuleOverviewTableModel().removeRule(r);
					break;
				case MODIFICATION:
					int selRow = ruleOverview.getRuleOverviewTable().getSelectedRow();
					ruleOverview.getRuleOverviewTableModel().fireTableDataChanged();
					if(selRow>=0){ // reselect the row. . .
						ruleOverview.getRuleOverviewTable().setRowSelectionInterval(selRow, selRow);						
					}
					break;
				default:
					break;
				}
			}
		}

	}
	
	public JPanel getPanel() {
		return ruleOverview;
	}


}
