package model.rule;

import model.ValueTag;

public class ValueCompareSmaller implements ValueCompare {

	@Override
	public boolean compare(Rule pRule) {
		return (pRule.getCurrencyPair().getPrice(ValueTag.PRICE_LAST) <= pRule.getValueTarget());
	}

}
