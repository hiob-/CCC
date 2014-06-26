package model.rule;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;
import model.currency.CurrencyPair;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Rule Domain Object Describes a Rule. Everytime a setter is called, the rule
 * informs its observers (the library) this telling it to propagate those
 * changes to the DB.
 * 
 * @author Luca TÃ¤nnler
 * @version 1.1
 * */
@DatabaseTable(tableName = "Rules")
public class Rule extends Observable implements Observer {
	public enum RuleType {
		PriceUpLimit(">"), PriceDownLimit("<"), PriceUpDelta("▲"), PriceDownDelta(
				"▼");
		private RuleType(final String mathSym) {
			this.mathSym = mathSym;
		}
		private final String mathSym;

		@Override
		public String toString() {
			return mathSym;
		}
	}

	/**
	 * valueTarget defines the currency pair rate where a Limit-Rule matches. If
	 * its a Delta-Rule, valueTarget defines the rate when the Rule was created.
	 * */
	@DatabaseField(columnName = "ruleId", generatedId = true)
	private int ruleId;
	@DatabaseField(columnName = "valueTarget")
	private volatile double valueTarget;
	@DatabaseField(columnName = "delta")
	private double delta;
	@DatabaseField(columnName = "createDate")
	private Date createDate;
	@DatabaseField(columnName = "email")
	private boolean email;
	@DatabaseField(columnName = "popup")
	private boolean popup;
	@DatabaseField(columnName = "ruleType")
	private RuleType ruleType;
	@DatabaseField(columnName = "active")
	private boolean active;
	@DatabaseField(columnName = "currencyPair_ID")
	private int currencyPair_ID;

	private CurrencyPair currencyPair;
	private volatile ValueCompare valueCompare;
	@DatabaseField(columnName = "ruleTriggered")
	private boolean ruleTriggered;


	// For OR-Mapper, empty CTOR
	public Rule() {
	}

	// Manual CTOR
	public Rule(final CurrencyPair pCurrencyPair, final boolean email,
			final boolean popup, final double pValueTarget, final double delta,
			final RuleType pRuleType, final boolean pActive, final boolean pruleTriggered) {
		createDate = new Date();
		valueTarget = pValueTarget;
		active = pActive;
		this.delta = delta;
		this.popup = popup;
		this.email = email;
		setCurrencyPair(pCurrencyPair);
		this.delta = 0;
		setRuleType(pRuleType);
		this.ruleTriggered=pruleTriggered;

	}

	private void setCompareBigger() {
		valueCompare = new ValueCompareBigger();
	}

	private void setCompareSmaller() {
		valueCompare = new ValueCompareSmaller();
	}

	private void setCompareDeltaUp() {
		valueCompare = new ValueCompareDeltaUp();
	}

	private void setCompareDeltaDown() {
		valueCompare = new ValueCompareDeltaDown();
	}

	public void setRuleType(final RuleType pRuleType) {
		ruleType = pRuleType;
		switch (pRuleType) {
		case PriceUpLimit:
			setCompareBigger();
			break;
		case PriceDownLimit:
			setCompareSmaller();
			break;
		case PriceUpDelta:
			setCompareDeltaUp();
			break;
		case PriceDownDelta:
			setCompareDeltaDown();
			break;
		}
	}

	/**
	 * Checks if the Rule matches the current price Value
	 * 
	 * @return boolean Whether the Rule matches or not
	 * */
	public synchronized boolean matchCheck() {
		return valueCompare.compare(this);
	}

	public boolean isPopupActive() {
		return popup;
	}

	public void setPopupActive(final boolean popup) {
		this.popup = popup;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(final int ruleId) {
		this.ruleId = ruleId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public synchronized double getValueTarget() {
		return valueTarget;
	}

	public synchronized void setValueTarget(final double valueTarget) {
		this.valueTarget = valueTarget;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public ValueCompare getValueCompare() {
		return valueCompare;
	}

	public void setValueCompare(final ValueCompare valueCompare) {
		this.valueCompare = valueCompare;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public synchronized boolean isActive() {
		return active;
	}

	public synchronized void setActive(final boolean active) {
		this.active = active;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public synchronized CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(final CurrencyPair currencyPair) {
		this.currencyPair = currencyPair;
		currencyPair.addObserver(this);
		currencyPair_ID = currencyPair.getId();
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public int getCurrencyPairID() {
		return currencyPair_ID;
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(final double delta) {
		this.delta = delta;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	public boolean emailAlertActive() {
		return email;
	}

	// Ozzi magic
	@Override
	public String toString() {
		final CurrencyPair cP = getCurrencyPair();
		String deltaStr = "";

		if (ruleType == RuleType.PriceDownDelta
				|| ruleType == RuleType.PriceDownDelta) {
			deltaStr = " (" + delta + "%) ";
		}
		return "1 " + cP.getBaseCurrency().getShortName() +" "+ruleType +" "+ deltaStr
				+ valueTarget +" " +cP.getQuotedCurrency().getShortName();
	}

	/**
	 * 
	 * @return A human readable long explanation of the rule
	 */
	public String toText() {
		final String baseCurrencyName = getCurrencyPair().getBaseCurrency()
				.getCurrencyName();
		final String quotedCurrencyName = getCurrencyPair().getQuotedCurrency()
				.getCurrencyName();
		final String ruleOperation = getRuleType().toString();
		return baseCurrencyName + "/" + quotedCurrencyName + ruleOperation
				+ getValueTarget();
	}

	public void setEmail(final boolean email) {
		this.email = email;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}
	
	public boolean isRuleTriggered() {
		return ruleTriggered;
	}

	public void setRuleTriggered(boolean ruleTriggered) {
		this.ruleTriggered = ruleTriggered;
		doNotify(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	/**
	 * We need to call this after creating a new rule, because we can't do this
	 * in the ctor it self, as its not known to the collection and the
	 * collection won't do a db create elsewhise
	 */
	public void triggerNew() {
		doNotify(new ObserverObjectWrapper(this, ActionType.ADD));
	}

	private void doNotify(final Object pParameter) {
		setChanged();
		notifyObservers(pParameter);
	}

	@Override
	public void update(final Observable o, final Object arg) {
		if (o instanceof CurrencyPair) {
			if (arg instanceof ObserverObjectWrapper) {
				final ObserverObjectWrapper oow = (ObserverObjectWrapper) arg;
				if (oow.getActionType() == ActionType.UNSUBSCRIBE) {
					setChanged();
					notifyObservers(new ObserverObjectWrapper(this,
							ActionType.REMOVE));
				} else if (oow.getActionType() == ActionType.REMOVE) {
					setChanged();
					notifyObservers(new ObserverObjectWrapper(this,
							ActionType.REMOVE));
				}
			}
		}
	}

}
