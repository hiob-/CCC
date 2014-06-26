package ui.currencyPairDetail;

import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import ui.internationalization.Strings;

import logic.preferences.PreferencesHandler;
import model.PriceValues;
import model.ValueTag;


/**
 * Tablemodel for Rule Overview list
 * 
 * @author Oussama Zgheb
 * @version 1.1
 * */

public class ValueTagPriceTabelModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	// Constant for getValueAt Switch
	public static final int VALUETAG = 0;
	public static final int PRICE = 2;
	public static final int DIRECTION = 1;

	protected String[] columnNames = { Strings.getString("ValueTagPriceTabelModel.valueTag"), Strings.getString("ValueTagPriceTabelModel.indicator"), Strings.getString("ValueTagPriceTabelModel.price") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	@SuppressWarnings("rawtypes")
	protected Class[] columnClasses = { String.class, String.class, String.class };

	private final Vector<PriceValues> priceValueList;
	private DecimalFormat decimalFormater10;

	public ValueTagPriceTabelModel() {
		priceValueList = new Vector<PriceValues>();
		decimalFormater10 = new DecimalFormat("#");
		decimalFormater10.setMaximumFractionDigits(10);
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
		return priceValueList.size();
	}

	@Override
	public final Object getValueAt( int row, final int column) {
		PriceValues selPV;
		if(row<priceValueList.size()){
			selPV = priceValueList.get(row);	
		}else{
			selPV = new PriceValues(ValueTag.OTHER_1, -1);
		}
		switch (column) {
		case VALUETAG:
			return selPV.getValueTag();
		case PRICE:
			if(PreferencesHandler.getRepresentation() == PreferencesHandler.decimalRepresentation){
				return decimalFormater10.format(selPV.getValue());				
			}
			return selPV.getValue();
		case DIRECTION:
			switch(selPV.valueBigger()){
			case -1:
				return Strings.getString("ValueTagPriceTabelModel.up");
			case 1:
				return Strings.getString("ValueTagPriceTabelModel.down");
			case 0:
				return "=";
			}
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

	public PriceValues getTagValuePriceAt(final int row) {
		return priceValueList.get(row);
	}

	public void addTagValuePrice(final PriceValues pV) {
		if (priceValueList.contains(pV)) {
			priceValueList.remove(pV);
		}
		priceValueList.add(pV);
		fireTableDataChanged();
	}

	public int indexOf(final PriceValues pV) {
		return priceValueList.indexOf(pV);
	}
}
