package logic.updater;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;
import model.currency.CurrencyPair;

/**
 * 
 * Saves the results of the update thread in order for the GUI to display information about said updates
 * 
 * @author Luca Tännler, Oussama Zgheb
 *
 */
public class UpdateResultStatus extends Observable {
	private final Map<String, Integer> resultStates = new ConcurrentHashMap<>();

	public UpdateResultStatus() {
	}

	/**
	 * Adds a new State entry. pState: 0: Everythings fine 1: No Internet /
	 * Unknown Host 2: Timeout / OR other...
	 * */
	public synchronized void addState(final CurrencyPair pCurrencyPair,
			final int pState) {
		resultStates.put(pCurrencyPair.toString(), pState);
		setChanged();
		notifyObservers(new ObserverObjectWrapper(
				resultStates, ActionType.ADD));
	}

	public synchronized void resetList() {
		resultStates.clear();
	}
	
	public synchronized void removeEntry(CurrencyPair pCurrencyPair){
		resultStates.remove(pCurrencyPair.toString());
		setChanged();
		notifyObservers(new ObserverObjectWrapper(resultStates, ActionType.REMOVE));
	}
}
