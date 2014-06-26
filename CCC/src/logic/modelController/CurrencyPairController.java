package logic.modelController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logic.exchange.ExchangeController;
import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.persistence.DbHandler;
import model.Exchange;
import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;
import model.currency.CCC_Currency;
import model.currency.CurrencyPair;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;


/**
 * This class provides a persistence & library functionality for all Currency Pairs.
 * 
 * @author Oussama Zgheb
 * @version 1.2
 * 
 */
public class CurrencyPairController extends Observable implements Observer {

	private static CurrencyPairController instance= null; 
	private CurrencyPairController() {
		initialize();	
	}
	
	public static CurrencyPairController getInstance(){
		if(instance==null){
			instance= new CurrencyPairController();
		}
		return instance;
	}

	private final List<CurrencyPair> SubscribedCurrencyPairList = new ArrayList<CurrencyPair>();
	private final List<CurrencyPair> CurrencyPairList = new ArrayList<CurrencyPair>();

	private Dao<CurrencyPair, Integer> CurrencyPair_Dao;

	public void initialize() {
		try {
			CurrencyPair_Dao = DaoManager.createDao(DbHandler.getInstance(),
					CurrencyPair.class);
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		// Get Objects and add them to the library
		List<CurrencyPair> all = null;
		try {
			all = CurrencyPair_Dao.queryForAll();
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		for (final CurrencyPair cP : all) {
			cP.setExchange(ExchangeController.getInstance().getExchange(
					cP.getExchange_ID()));
			if (cP.isSubscribed()) {
				addSuscribedCurrencyPair(cP);
			}

			// Link Currencies to CP
			final CCC_Currency base = CurrencyController.getInstance()
					.getCurrency(cP.getBaseCurrency_ID());
			final CCC_Currency quoted = CurrencyController.getInstance()
					.getCurrency(cP.getQuotedCurrency_ID());
			cP.setBaseCurrency(base);
			cP.setQuotedCurrency(quoted);

			CurrencyPairList.add(cP);
			cP.addObserver(this);
			setChanged();
			notifyObservers(new ObserverObjectWrapper(cP, ActionType.ADD));
		}
		Logger.write("[CurrencyPairCollection] Loaded "+all.size()+" CurrencyPairs from DB ", LogLevel.info);

	}

	public synchronized CurrencyPair getCurrencyPair(
			final CCC_Currency baseCurrency, final CCC_Currency quotedCurrency,
			final Exchange exchange) {
		for (final CurrencyPair cP : CurrencyPairList) {
			if (cP.getBaseCurrency().equals(baseCurrency)
					&& cP.getQuotedCurrency().equals(quotedCurrency)
					&& cP.getExchange_ID() == exchange.getExchangeID()) {

				return cP;
			}
		}
		return null;
	}

	public void addSuscribedCurrencyPair(final CurrencyPair cP) {
		if(!SubscribedCurrencyPairList.contains(cP)){
			SubscribedCurrencyPairList.add(cP);
			cP.setSubscribed(true);
			setChanged();
			notifyObservers(new ObserverObjectWrapper(cP, ActionType.SUBSCRIBE));			
		}else{
			Logger.write("Tried to subscribed a CP which is already subscribed: "+cP, LogLevel.warning);
		}
	}

	public synchronized void createCurrencyPair(
			final CCC_Currency pBaseCurrency,
			final CCC_Currency pQuotedCurrency, final Exchange pExchange) {
		CurrencyPair cp = getCurrencyPair(pBaseCurrency, pQuotedCurrency,
				pExchange);
		if (cp == null) {
			cp = new CurrencyPair(pBaseCurrency, pQuotedCurrency, pExchange);
			CurrencyPairList.add(cp);
			cp.addObserver(this);
			cp.triggerNew();
			notifyObservers(new ObserverObjectWrapper(cp, ActionType.ADD));
		}
	}

	public List<CurrencyPair> getCurrencyPairList() {
		return CurrencyPairList;
	}

	public void removeSuscribedCurrencyPair(final CurrencyPair cP) {
		SubscribedCurrencyPairList.remove(cP);
		cP.setSubscribed(false);
		setChanged();
		notifyObservers(new ObserverObjectWrapper(cP, ActionType.UNSUBSCRIBE));
		cP.notifyObservers(new ObserverObjectWrapper(cP, ActionType.UNSUBSCRIBE));
	}

	public synchronized void removeCurrencyPair(final CurrencyPair pCurrencyPair) {
		if (!CurrencyPairList.contains(pCurrencyPair)) {
			return;
		}

		if (pCurrencyPair.isSubscribed()) {
			removeSuscribedCurrencyPair(pCurrencyPair);
		}

		CurrencyPairList.remove(pCurrencyPair);

		pCurrencyPair.doNotify(new ObserverObjectWrapper(pCurrencyPair,
				ActionType.REMOVE));
		// Delete in DB
		final DeleteBuilder<CurrencyPair, Integer> db = CurrencyPair_Dao
				.deleteBuilder();
		try {
			db.where().eq("id", pCurrencyPair.getId());
			CurrencyPair_Dao.delete(db.prepare());
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		setChanged();
		notifyObservers(new ObserverObjectWrapper(pCurrencyPair,
				ActionType.REMOVE));
	}

	public List<CurrencyPair> getAllCurrencyPairs() {
		return CurrencyPairList;
	}

	public synchronized List<CurrencyPair> getAllCurrencyPairs(
			final Exchange pExchange) {
		final List<CurrencyPair> filteredList = new ArrayList<>();
		if (!CurrencyPairList.isEmpty()) {
			for (final CurrencyPair cp : CurrencyPairList) {
				if (cp.getExchange().equals(pExchange)) {
					filteredList.add(cp);
				}
			}
		}
		return filteredList;
	}

	public List<CurrencyPair> getAllSubsribedCurrencyPairs() {
		return SubscribedCurrencyPairList;
	}

	public CurrencyPair getCurrencyPair(final int id) {
		for (final CurrencyPair cP : CurrencyPairList) {
			if (cP.getId() == id) {
				return cP;
			}
		}
		return null;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof CurrencyPair) {
			if (arg instanceof ObserverObjectWrapper) {
				final ObserverObjectWrapper wrap = (ObserverObjectWrapper) arg;
				final CurrencyPair observCurrPair = (CurrencyPair) wrap
						.getObject();
				switch (wrap.getActionType()) {
				case ADD:

					try {
						CurrencyPair_Dao.create(observCurrPair);
						Logger.write("Created new CurrencyPair", LogLevel.info);
					} catch (final SQLException e) {
						System.out
								.println("Could not update DB with new CurrencyPair");
						e.printStackTrace();
					}
					break;
				case MODIFICATION:
					try {
						CurrencyPair_Dao.update(observCurrPair);
					} catch (final SQLException e) {
						System.err.println("Could not update DB with new CP");
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
