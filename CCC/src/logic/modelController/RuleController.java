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
import model.currency.CurrencyPair;
import model.rule.Rule;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;


/**
 * RuleLibrary: Manages {@link Rule} and provides some basic search
 * functionality.
 * 
 * @author Luca Tännler
 * @version 1.0
 * */
public class RuleController extends Observable implements Observer {
	/** Stores the Instance of the RuleLibrary */
	private Dao<Rule, Integer> ruleDao;
	private final ArrayList<Rule> ruleList = new ArrayList<Rule>();
	private static RuleController instance = null;
	
	/**
	 * Constructor. Use method {@link RuleController#getInstance()} to get an
	 * Instance.
	 * */
	private RuleController() {
		initialize();
	}
	
	public static RuleController getInstance() {
		if (instance == null) {
			instance = new RuleController();
		}
		return instance;
	}

	private void initialize() {
		// Init DB Connection for Rules
		try {
			ruleDao = DaoManager.createDao(DbHandler.getInstance(), Rule.class);
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		// Get Objects and add them to the library
		List<Rule> all = null;
		try {
			all = ruleDao.queryForAll();
			Logger.write("[RuleCollection] Loaded "+all.size()+" Rules from DB ", LogLevel.info);
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		for (final Rule rule : all) {
			// link cP from ID
			rule.setCurrencyPair(CurrencyPairController.getInstance()
					.getCurrencyPair(rule.getCurrencyPairID()));
			ruleList.add(rule);
			rule.setRuleType(rule.getRuleType());
			rule.addObserver(this);
		}

	}

	/**
	 * Adds a Rule to the library.
	 * 
	 * @param pRule
	 *            {@link Rule} that has to be added to the Library.
	 * */
	public boolean addRule(final Rule pRule) {
		// We want to observe all Rules in our Collection
		pRule.addObserver(this);
		// So the app is informed about the new rule
		setChanged();
		notifyObservers(new ObserverObjectWrapper(pRule, ActionType.ADD));
		return ruleList.add(pRule);
	}

	/**
	 * Deletes a Rule from the Library
	 * 
	 * @param pRule
	 *            {@link Rule} that has to be deleted from the library.
	 * @return The deleted Rule. Null if the Rule wasn't found in library.
	 * */
	public boolean removeRule(final Rule pRule) {
		if (pRule == null) {
			throw new IllegalArgumentException(
					"RuleLibrary.removeRule: Parameter can't not be null.");
		}
		if (!ruleList.contains(pRule)) {
			return false;
		}
		setChanged();
		notifyObservers(new ObserverObjectWrapper(pRule, ActionType.REMOVE));
		// Delete in DB
		final DeleteBuilder<Rule, Integer> db = getRuleDao().deleteBuilder();
		try {
			db.where().eq("ruleId", pRule.getRuleId());
			getRuleDao().delete(db.prepare());
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return ruleList.remove(pRule);
	}

	public ArrayList<Rule> getRuleList() {
		return ruleList;
	}

	public ArrayList<Rule> getRuleListByCurrencyPair(
			final CurrencyPair pCurrencyPair) {
		final ArrayList<Rule> filteredRuleList = new ArrayList<Rule>();
		if (!ruleList.isEmpty()) {
			for (final Rule rule : ruleList) {
				if (rule.getCurrencyPair().equals(pCurrencyPair)) {
					filteredRuleList.add(rule);
				}
			}
		}
		return filteredRuleList;
	}

	public Dao<Rule, Integer> getRuleDao() {
		return ruleDao;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof Rule) {
			if (arg instanceof ObserverObjectWrapper) {
				final ObserverObjectWrapper wrap = (ObserverObjectWrapper) arg;
				final Rule observRule = (Rule) wrap.getObject();
				switch (wrap.getActionType()) {
				case ADD:
					try {
						ruleDao.create(observRule);
						Logger.write("Created new Rule", LogLevel.info);
					} catch (final SQLException e) {
						System.err.println("Could not update DB with new Rule");
						e.printStackTrace();
					}
					break;
				case MODIFICATION:
					try {
						ruleDao.update(observRule);
					} catch (final SQLException e) {
						System.err
								.println("Could not update DB with modifcationed Rule");
						e.printStackTrace();
					}
					break;
				case REMOVE:
					removeRule(observRule);
					break;
				default:
					break;
				}
			}
		}
	}

}
