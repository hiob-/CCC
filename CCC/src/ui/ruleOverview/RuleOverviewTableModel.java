package ui.ruleOverview;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import model.ValueTag;
import model.rule.Rule;
import ui.internationalization.Strings;

/**
 * Tablemodel for Rule Overview list
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * */

public class RuleOverviewTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	// Constant for getValueAt Switch
	public static final int RULE_TARGETVALUE = 0;
	public static final int RULE_ACTUALVALUE = 1;
	public static final int RULE_ACTIVE = 2;
	public static final int RULE_NOTIFICATION_MAIL = 3;
	public static final int RULE_NOTIFICATION_POPUP = 4;

	protected String[] columnNames = {
				Strings.getString("RuleOverviewTableModel.rule"), 
				Strings.getString("RuleOverviewTableModel.lastPrice"), 
				Strings.getString("RuleOverviewTableModel.active"),
				"\u2709", 
				"\u2750"
			}; 

	@SuppressWarnings("rawtypes")
	protected Class[] columnClasses = { 
		String.class,
		Double.class,
		Boolean.class,
		Boolean.class,
		Boolean.class		
	};

	private final Vector<Rule> rules;

	public RuleOverviewTableModel() {
		rules = new Vector<Rule>();
	}

	@Override
	public final String getColumnName(final int column) {
		return columnNames[column];
	}

	@Override
	public final int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public final int getRowCount() {
		return rules.size();
	}

	@Override
	public final Object getValueAt(final int row, final int column) {
		final Rule selRule = rules.get(row);
		switch (column) {
		case RULE_TARGETVALUE:
			return selRule;
		case RULE_ACTUALVALUE:
			return selRule.getCurrencyPair().getPrice(ValueTag.PRICE_LAST);
		case RULE_ACTIVE:
			return selRule.isActive();
		case RULE_NOTIFICATION_MAIL:
			return selRule.emailAlertActive();
		case RULE_NOTIFICATION_POPUP:
			return selRule.isPopupActive();
		default:
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	// We need this so the sorter knows, what type is in a certain column
	public Class getColumnClass(final int pColumn) {
		if (getRowCount() >= 0) {
			return columnClasses[pColumn];
		} else {
			return String.class;
		}
	}

	// Rule_Active toggle
	@Override
	public void setValueAt(final Object value, final int rowIndex,
			final int columnIndex) {
		final Rule selRule = rules.get(rowIndex);
		if (value instanceof Boolean) {
			boolean valueSelected=((Boolean) value).booleanValue();
			switch(columnIndex){
			case(RULE_ACTIVE):
				selRule.setActive(valueSelected);				
				break;
			case(RULE_NOTIFICATION_MAIL):
				selRule.setEmail(valueSelected);				
				break;
			case(RULE_NOTIFICATION_POPUP):
				selRule.setPopupActive(valueSelected);				
				break;
			}
		}
	}

	// Rule_Active is editable
	@Override
	public boolean isCellEditable(final int row, final int col) {
		if (col == RULE_ACTIVE || col== RULE_NOTIFICATION_MAIL || col==RULE_NOTIFICATION_POPUP) {
			return true;
		} else {
			return false;
		}
	}

	public Rule getRuleAt(final int row) {
		return rules.get(row);
	}

	public void addRule(final Rule prule) {
		rules.add(prule);
	}

	public void removeRule(final Rule prule) {
		rules.remove(prule);
		fireTableDataChanged();
	}

	public int indexOf(final Rule prule) {
		return rules.indexOf(prule);
	}
}
