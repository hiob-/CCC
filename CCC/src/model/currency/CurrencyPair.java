package model.currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;
import model.Exchange;
import model.PriceValues;
import model.ValueTag;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * 
 * This class combines two currencies into a pair. We use Currencypairs to get
 * the price at an exchange.
 * 
 * @author Oussama Zgheb
 * @version 1.3
 * 
 */

@DatabaseTable(tableName = "CurrencyPairs")
public class CurrencyPair extends Observable implements Observer {

	@DatabaseField(columnName = "id", generatedId = true)
	private int id;
	@DatabaseField(columnName = "baseCurrency_ID")
	private int baseCurrency_ID;
	@DatabaseField(columnName = "quotedCurrency_ID")
	private int quotedCurrency_ID;
	@DatabaseField(columnName = "exchange_ID")
	private int exchange_ID;
	private Exchange exchange;

	private CCC_Currency baseCurrency;
	private CCC_Currency quotedCurrency;
	private final ConcurrentHashMap<ValueTag, PriceValues> priceValueMap = new ConcurrentHashMap<>();

	private CurrencyPairHistory cpHistory;

	@DatabaseField(columnName = "subscribed")
	private boolean subscribed;

	// For OR Mapper - empty CTOR
	public CurrencyPair() {
	}
	
	public void createHistory(){
		if(cpHistory==null){
			cpHistory = new CurrencyPairHistory(this);			
			updateHistoryPointAdder();
		}
	}

	public CurrencyPair(final CCC_Currency baseCurrency,
			final CCC_Currency quotedCurrency, final Exchange exchange) {
		if (baseCurrency == null || quotedCurrency == null || exchange == null) {
			throw new IllegalArgumentException(
					"BaseCurrency, QuotedCurreny and Exchange must not be null.");
		}
		this.baseCurrency = baseCurrency;
		baseCurrency_ID = baseCurrency.getId();
		this.quotedCurrency = quotedCurrency;
		quotedCurrency_ID = quotedCurrency.getId();
		this.exchange = exchange;
		this.exchange_ID = exchange.getExchangeID();
		cpHistory = new CurrencyPairHistory(this);
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(final boolean suscribed) {
		subscribed = suscribed;
		this.createHistory();
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public List<PriceValues> getPriceValueList() {
		return new ArrayList<PriceValues>(priceValueMap.values());
	}

	public Map<ValueTag, PriceValues> getPriceValueMap() {
		return priceValueMap;
	}

	public synchronized double getPrice(final ValueTag tagValue) {
		if (priceValueMap.containsKey(tagValue)) {
			return priceValueMap.get(tagValue).getValue();
		}
		return -1;
	}

	public synchronized void setPrice(final ValueTag pValueTag,
			final double pValue) {
		setPrice(pValueTag, pValue, 0);
	}

	public synchronized void setPrice(final ValueTag pValueTag,
			final double pValue, final double pOldValue) {
		if (priceValueMap.containsKey(pValueTag)) {
			priceValueMap.get(pValueTag).setValue(pValue);
			priceValueMap.get(pValueTag).setOldValue(pOldValue);
		} else {
			priceValueMap.put(pValueTag, new PriceValues(pValueTag, pValue,
					pOldValue));
		}
		doNotify(new CurrencyPairPriceUpdateWrapper(pValueTag,
				priceValueMap.get(pValueTag)));
	}

	public synchronized CCC_Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(final CCC_Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
		baseCurrency_ID = baseCurrency.getId();
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public synchronized CCC_Currency getQuotedCurrency() {
		return quotedCurrency;
	}

	public void setQuotedCurrency(final CCC_Currency quotedCurrency) {
		this.quotedCurrency = quotedCurrency;
		quotedCurrency_ID = quotedCurrency.getId();
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public boolean equals(final CurrencyPair cpR) {
		if (cpR == null) {
			return false;
		}
		if (cpR.getBaseCurrency().getCurrencyName()
				.equals(getBaseCurrency().getCurrencyName())
				&& cpR.getQuotedCurrency().getCurrencyName()
						.equals(getQuotedCurrency().getCurrencyName())
				&& cpR.getExchange_ID()==this.getExchange_ID()) {
			return true;
		}
		return false;
	}

	public synchronized Exchange getExchange() {
		return exchange;
	}

	public void setExchange(final Exchange exchange) {
		this.exchange = exchange;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public void setBaseCurrency_ID(final int baseCurrency_ID) {
		this.baseCurrency_ID = baseCurrency_ID;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public void setQuotedCurrency_ID(final int quotedCurrency_ID) {
		this.quotedCurrency_ID = quotedCurrency_ID;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public int getExchange_ID() {
		return exchange_ID;
	}

	public void setExchange_ID(final int exchange_ID) {
		this.exchange_ID = exchange_ID;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	@Override
	public String toString() {
		return exchange.getExchangeNameString() + ": "
				+ baseCurrency.getShortName() + "/"
				+ quotedCurrency.getShortName();
	}

	public void doNotify(final Object pParameter) {
		setChanged();
		notifyObservers(pParameter);
	}

	/**
	 * We need to call this after creating a new rule, because we can't do this
	 * in the ctor it self, as its not known to the collection and the
	 * collection won't do a db create elsewhise
	 */
	public void triggerNew() {
		doNotify(new ObserverObjectWrapper(this, ActionType.ADD));
	}

	@Override
	public void update(final Observable arg0, final Object arg1) {
		if (arg1 instanceof PriceValues) {
			final PriceValues priceValues = ((PriceValues) arg1);
			setPrice(priceValues.getValueTag(), priceValues.getValue(),
					priceValues.getOldValue());
			if (priceValues.getValueTag() == ValueTag.PRICE_LAST
					|| priceValues.getValueTag() == ValueTag.VOLUME) {
				updateHistoryPointAdder();
			}
		}
	}

	private void updateHistoryPointAdder() {
		double volume = 0.0;
		double price = 0.0;
		if (priceValueMap.keySet().contains(ValueTag.VOLUME)) {
			volume = priceValueMap.get(ValueTag.VOLUME).getValue();
		}
		if (priceValueMap.keySet().contains(ValueTag.PRICE_LAST)) {
			price = priceValueMap.get(ValueTag.PRICE_LAST).getValue();
		}
		cpHistory.addHistoryPoint(price, volume);
	}

	public int getBaseCurrency_ID() {
		return baseCurrency_ID;
	}

	public int getQuotedCurrency_ID() {
		return quotedCurrency_ID;
	}

	public CurrencyPairHistory getCpHistory() {
		return cpHistory;
	}

}
