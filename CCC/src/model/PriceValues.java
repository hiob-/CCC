package model;

/**
 * 
 * This class combines a valuetag (last,low,high) with a double (its price) and
 * the old price (for indicators - up / down)
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */
public class PriceValues {

	private final ValueTag valueTag;
	private double value;
	private double oldValue;

	public PriceValues(final ValueTag valueTag, final double value) {
		this(valueTag, value, 0);
	}

	public PriceValues(final ValueTag valueTag, final double value,
			final double pOldValue) {
		this.valueTag = valueTag;
		this.value = value;
		oldValue = pOldValue;
	}

	public double getOldValue() {
		return oldValue;
	}

	public void setOldValue(final double oldValue) {
		this.oldValue = oldValue;
	}

	public ValueTag getValueTag() {
		return valueTag;
	}

	public double getValue() {
		return value;
	}

	public void setValue(final double pValue) {
		value = pValue;
	}

	public boolean equals(final PriceValues pPriceValues) {
		return valueTag == pPriceValues.getValueTag();
	}

	/**
	 * If the values are nearly the same (delta for doubles): 0
	 * If the oldValue is bigger than the new value: 1
	 * If the value is bigger than the old value: -1 
	 * @return
	 */
	public int valueBigger() {
		final float PRECISION_LEVEL = 0.001f;
	    if(Math.abs(value - oldValue) < PRECISION_LEVEL){
			return 0;
		}
		if(oldValue>value){
			return 1;
		}else{
			return -1;
		}
	}


}
