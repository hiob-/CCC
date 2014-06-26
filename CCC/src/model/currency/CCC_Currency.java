package model.currency;

import java.util.Observable;

import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 
 * This class holds all defined currencies Content: Name and Symbol of the
 * Currency
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */

@DatabaseTable(tableName = "CCC_Currencies")
public class CCC_Currency extends Observable {

	@DatabaseField(columnName = "currencyName")
	private String currencyName;
	@DatabaseField(columnName = "shortName")
	private String shortName;
	@DatabaseField(columnName = "symbol")
	private String symbol;
	@DatabaseField(columnName = "isFiat")
	private boolean isFiat;
	@DatabaseField(generatedId = true)
	private int id;

	// For OR Mapper - empty CTOR
	public CCC_Currency() {
	}

	/**
	 * @param currencyName
	 *            (usd -> for US dollar)
	 * @param symbol
	 *            (z.B $)
	 */
	public CCC_Currency(final String currencyName, final String shortName,
			final String symbol, final boolean isFiat) {
		this.currencyName = currencyName;
		this.shortName = shortName;
		this.symbol = symbol;
		this.isFiat = isFiat;
	}

	public synchronized String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(final String currencyName) {
		this.currencyName = currencyName;
	}

	public synchronized String getShortName() {
		return shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(final String symbol) {
		this.symbol = symbol;
	}

	public boolean isFiat() {
		return isFiat;
	}

	public void setFiat(final boolean isFiat) {
		this.isFiat = isFiat;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return currencyName + " (" + shortName + ")";
	}

	public boolean equals(final CCC_Currency that) {
		if (shortName.equals(that.shortName)) {
			return true;
		}
		return false;
	}

	/**
	 * We need to call this after creating a new rule, because we can't do this
	 * in the ctor it self, as its not known to the collection and the
	 * collection won't do a db create elsewhise
	 */
	public synchronized void triggerNew() {
		doNotify(new ObserverObjectWrapper(this, ActionType.ADD));
	}

	private void doNotify(final Object pParameter) {
		setChanged();
		notifyObservers(pParameter);
	}

}
