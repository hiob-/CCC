package logic.updater;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.modelController.RuleController;
import logic.notification.NotificationHandler;
import model.ObserverObjectWrapper;
import model.PriceValues;
import model.ValueTag;
import model.rule.Rule;

/**
 * Updates the subscribed currency pairs via a concurrency pair.
 * 1 Thread per CP. 
 * The associated rules are checked for trigger after the new prices were polled.
 * 
 * @author Luca T�nnler
 *
 */
public class CurrencyPairUpdateManagerThread extends Thread implements Observer {
	private final ConcurrencyPair concurrencyPair;
	private int updateInterval;
	private final CopyOnWriteArrayList<Rule> ruleList;

	public CurrencyPairUpdateManagerThread(ConcurrencyPair pConcurrencyPair) {
		if (pConcurrencyPair == null) {
			throw new IllegalArgumentException(
					"pConcurrencyPair must not be null.");
		}
		concurrencyPair = pConcurrencyPair;
		ruleList = new CopyOnWriteArrayList<Rule>(RuleController.getInstance().getRuleListByCurrencyPair(concurrencyPair.getCurrencyPair()));
		// Add this instance as Observer from RuleLibrary
		RuleController.getInstance().addObserver(this);
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				updatePriceValues(concurrencyPair);
				CurrencyPairUpdateManager.getInstance().addUpdateResultState(concurrencyPair.getCurrencyPair(), 0);
			} catch (final UnknownHostException e) {
				Logger.write("No connection to Exchange: "
						+ concurrencyPair.getCurrencyPair().getExchange(),
						LogLevel.warning);
				CurrencyPairUpdateManager.getInstance().addUpdateResultState(concurrencyPair.getCurrencyPair(), 1);
			} catch (final Exception e) {
				Logger.write("Exchange API Timeout: "
						+ concurrencyPair.getCurrencyPair().getExchange(),
						LogLevel.warning);
				CurrencyPairUpdateManager.getInstance().addUpdateResultState(concurrencyPair.getCurrencyPair(), 2);
			}
			
			if(!ruleList.isEmpty()){
				for (final Rule rule : ruleList) {
					checkRule(rule);
				}
			}
			
			sleep(updateInterval);
		}
		System.out.println("Thread dead");
	}

	/**
	 * Checks if a rule is Active and the value is matching and if so executes the alert
	 * The RuleTriggered is set according to its behaviour (see documentation)
	 * @param rule
	 */
	private void checkRule(final Rule rule) {
		final boolean matching = rule.matchCheck();
		if(matching){
			if(!rule.isRuleTriggered()){
				if(rule.isActive()){
					executeAlerts(rule);
				}
				rule.setRuleTriggered(true); 
			}
		}else{
			// So rules don't get reset if we got no HTTP response (returns 1.0)
			if(rule.getCurrencyPair().getPrice(ValueTag.PRICE_LAST)!=1.0){
				rule.setRuleTriggered(false); 				
			}
		}
	}
	
	/**
	 * Executes alerts
	 * */	
	public synchronized void executeAlerts(final Rule pRule) {
		if (pRule.isActive()) {
			if (pRule.isPopupActive()) {
				NotificationHandler.triggerPopUpAlert(pRule);
			}
			if (pRule.emailAlertActive()) {
				final Thread emailThread = new Thread() {
					@Override
					public void run() {
						super.run();
						NotificationHandler.triggerEmailAlert(pRule);
					}
				};
				emailThread.start();
			}
		}
	}

	private void updatePriceValues(final ConcurrencyPair ccp) throws Exception {
		final List<PriceValues> priceValueList = ccp.getCurrencyPair()
				.getExchange().getPrice(ccp.getCurrencyPair().getBaseCurrency().getShortName(),
						ccp.getCurrencyPair().getQuotedCurrency().getShortName());
		if (priceValueList != null && !priceValueList.isEmpty()) {
			for (final PriceValues priceValue : priceValueList) {
				ccp.setValue(priceValue.getValueTag(), priceValue.getValue());
			}
		}
	}

	public void stopThread() {
		interrupt();
		return;
	}

	private void sleep(final int pMilliSeconds) {
		try {
			Thread.sleep(pMilliSeconds);
		} catch (final InterruptedException e) {
			// e.printStackTrace();
		}
	}

	public int getUpdateInterval() {
		return updateInterval;
	}

	public void setUpdateInterval(final int updateInterval) {
		this.updateInterval = updateInterval;
	}

	private void handleRule(final ObserverObjectWrapper oow) {
		final Rule rule = (Rule) oow.getObject();
		if(rule != null && rule.getCurrencyPair() != null && rule.getCurrencyPair().equals(concurrencyPair.getCurrencyPair())){
			switch (oow.getActionType()) {
			case ADD:
				addRule(rule);
				break;
			case REMOVE:
				removeRule(rule);
				break;
			case MODIFICATION:
				break;
			default:
				break;
			}
		}
	}

	public void addRule(final Rule pRule) {
		if (!ruleList.contains(pRule)) {
			ruleList.add(pRule);
		}
	}

	public Rule removeRule(final Rule pRule) {
		ruleList.remove(pRule);
		return pRule;
	}

	@Override
	public void update(Observable o, Object arg) {
		// Add/Remove CurrencyPair to the Thread
		if (arg instanceof ObserverObjectWrapper
				&& (o instanceof RuleController)) {
			final ObserverObjectWrapper oow = (ObserverObjectWrapper) arg;
			if (oow.getObject() instanceof Rule) {
				handleRule(oow);
			}
		}
	}

}
