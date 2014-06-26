package logic.updater;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import model.PriceValues;
import model.ValueTag;
import model.currency.CurrencyPair;


/**
 * Concurrent / Threadsafe Currencypair wrapper.
 * 
 * @author Luca Tännler
 * @version 1.0
 * */
public class ConcurrencyPair extends Observable {
	private final CurrencyPair currencyPair;
	private final Map<ValueTag, Double> values = new ConcurrentHashMap<>();

	public ConcurrencyPair(final CurrencyPair pCurrencyPair) {
		currencyPair = pCurrencyPair;
		addObserver(currencyPair);
	}

	public synchronized void setValue(final ValueTag pTag, final double pValue) {
		final PriceValues newPriceValue = new PriceValues(pTag, pValue);
		if (values.containsKey(pTag)) {
			newPriceValue.setOldValue(values.get(pTag));
		}
		values.put(pTag, pValue);
		notifyObservers(newPriceValue);
		setChanged();
	}

	public synchronized double getValue(final String pTag) {
		return values.get(pTag);
	}

	public synchronized Map<ValueTag, Double> getValueMap() {
		return new HashMap<ValueTag, Double>(values);
	}

	public synchronized CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

}
