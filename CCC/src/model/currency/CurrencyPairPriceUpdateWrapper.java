package model.currency;

import model.PriceValues;
import model.ValueTag;



public class CurrencyPairPriceUpdateWrapper {
	private ValueTag valueTag;
	private PriceValues priceValue;
	
	public CurrencyPairPriceUpdateWrapper(ValueTag pValueTag, PriceValues pPriceValue){
		this.valueTag = pValueTag;
		this.priceValue = pPriceValue;
	}

	public ValueTag getValueTag() {
		return valueTag;
	}

	public PriceValues getPriceValue() {
		return priceValue;
	}
}
