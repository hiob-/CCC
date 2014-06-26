package logic.modelController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.persistence.DbHandler;
import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;
import model.currency.CCC_Currency;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;


/**
 * 
 * This class provides a persistence & library functionality for all Currencies
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */

public class CurrencyController extends Observable implements Observer {

	
	private static CurrencyController instance=null;
	
	private CurrencyController() {
		initialize();
	}
	
	public static CurrencyController getInstance() {
		if (instance == null) {
			instance = new CurrencyController();
		}
		return instance;
	}


	// Library
	private List<CCC_Currency> CurrencyList;
	// SQL
	public Dao<CCC_Currency, Integer> CCC_Currency_Dao;

	/* Initializes the Library with all currencies available */
	public void initialize() {

		CurrencyList = new ArrayList<CCC_Currency>();

		try {
			CCC_Currency_Dao = DaoManager.createDao(DbHandler.getInstance(),
					CCC_Currency.class);
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		// Get Objects and add them to the library
		List<CCC_Currency> all = null;
		try {
			all = CCC_Currency_Dao.queryForAll();
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		for (final CCC_Currency ccc_Currency : all) {
			addToList(ccc_Currency);
		}
		Logger.write("[CurrencyCollection] Loaded "+all.size()+" Currencies from DB ", LogLevel.info);

	}

	public synchronized void addToList(final CCC_Currency e) {
		CurrencyList.add(e);
		e.addObserver(this);
		setChanged();
		notifyObservers(new ObserverObjectWrapper(e, ActionType.ADD));
	}

	/**
	 * @param Call with Currency shortname, case insensitive
	 **/
	public synchronized CCC_Currency getCurrency(String toGet) {
		toGet = toGet.toUpperCase();
		for (final CCC_Currency c : CurrencyList) {
			if (c.getShortName().toUpperCase().equals(toGet)) {
				return c;
			}
		}
		return null;
	}

	public CCC_Currency getCurrency(final int toGet) {
		for (final CCC_Currency c : CurrencyList) {
			if (c.getId() == toGet) {
				return c;
			}
		}
		return null;
	}

	public List<CCC_Currency> getAllCurrencies() {
		return CurrencyList;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof CCC_Currency) {
			if (arg instanceof ObserverObjectWrapper) {
				final ObserverObjectWrapper wrap = (ObserverObjectWrapper) arg;
				final CCC_Currency observCurr = (CCC_Currency) wrap.getObject();
				switch (wrap.getActionType()) {
				case ADD:
					try {
						CCC_Currency_Dao.create(observCurr);
						Logger.write("Created new Currency", LogLevel.info);
					} catch (final SQLException e) {
						System.err
								.println("Could not update DB with new Currency");
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
			}
		}
	}

}
