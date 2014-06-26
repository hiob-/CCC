package model.rule;

import model.ValueTag;

public class ValueCompareDeltaUp implements ValueCompare {

	@Override
	public boolean compare(Rule pRule) {
		double targetPrice = pRule.getValueTarget() + ((pRule.getCurrencyPair().getPrice(ValueTag.PRICE_LAST)/100)*pRule.getDelta());
		return (pRule.getCurrencyPair().getPrice(ValueTag.PRICE_LAST) >= targetPrice);
	}

}
