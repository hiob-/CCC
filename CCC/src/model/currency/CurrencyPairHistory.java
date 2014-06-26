package model.currency;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;


public class CurrencyPairHistory extends Observable {
	public static int DATA_EXPIRE_AFTER = 180000; // Time in ms
	private final CurrencyPair currencyPair;
	private final ArrayList<CurrencyPairHistoryPoint> historyPointList;

	public CurrencyPairHistory(final CurrencyPair pCurrencyPair) {
		historyPointList = new ArrayList<CurrencyPairHistoryPoint>();
		currencyPair = pCurrencyPair;
	}

	public void addHistoryPoint(final double pPrice, final double pVolume) {
		addHistoryPoint(pPrice, pVolume, new Date());
	}

	public void addHistoryPoint(final double pPrice, final double pVolume,
			final Date pDate) {
		final CurrencyPairHistoryPoint newCPHP = new CurrencyPairHistoryPoint(
				pPrice, pVolume, pDate);
		historyPointList.add(newCPHP);
		setChanged();
		notifyObservers(new ObserverObjectWrapper(newCPHP, ActionType.ADD));
	}

	public ArrayList<CurrencyPairHistoryPoint> getHistoryPointList() {
		return historyPointList;
	}

	public CurrencyPair getCurrencyPair() {
		return currencyPair;
	}
}
