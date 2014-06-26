package logic.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.Exchange;
import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;

/**
 * 
 * This class provides a persistence & library functionality for all Exchanges b
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */

public class ExchangeController extends Observable implements Observer {

	private static ExchangeController instance = null;
	
	private ExchangeController() {
	}
	
	public static ExchangeController getInstance() {
		if (instance == null) {
			instance = new ExchangeController();
		}
		return instance;
	}


	private final List<Exchange> exchangeList = new ArrayList<Exchange>();
	public ExchangeController exLib;

	public void addToList(final Exchange e) {
		exchangeList.add(e);
		setChanged();
		notifyObservers(new ObserverObjectWrapper(e, ActionType.ADD));
	}

	/**
	 * 
	 * @param A
	 *            String with the Exchange Name (case insensitive)
	 * @return The Exchange object, if not found returns null
	 */
	public synchronized Exchange getExchange(String toGet) {
		toGet = toGet.toUpperCase();
		for (final Exchange e : exchangeList) {
			if (e.getExchangeName().toUpperCase().equals(toGet)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param A
	 *            int with the Exchange ID
	 * @return The Exchange object, if not found returns null
	 */
	public Exchange getExchange(final int id) {
		for (final Exchange e : exchangeList) {
			if (e.getExchangeID() == id) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param String
	 *            with the Exchangename to check (case insensitive)
	 * @return True or false if Exchangename is in Library
	 */
	public boolean exchangeExists(String toGet) {
		toGet = toGet.toUpperCase();
		for (final Exchange e : exchangeList) {
			if (e.getExchangeName().equals(toGet)) {
				return true;
			}
		}
		return false;
	}

	public List<Exchange> getAllExchanges() {
		return exchangeList;
	}


	@Override
	public void update(final Observable o, final Object arg) {
	}

}
