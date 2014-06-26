package model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * This class defines the abstract implementation of an exchange
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */

public abstract class Exchange {


	private int id;
	private final String name;
	private final String url;
	protected final Map<ValueTag, String> valueTagMapping;


	public Exchange(final int ID, final String exchangeName,
			final String exchangeURL) {
		id = ID;
		name = exchangeName;
		url = exchangeURL;
		valueTagMapping = new ConcurrentHashMap<ValueTag, String>();
		initValueTagMapping();
	}
	


	public synchronized String getExchangeName() {
		return name;
	}

	public String getExchangeNameString() {
		return name.toString();
	}

	public int getExchangeID() {
		return id;
	}

	public String getURL() {
		return url;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * This is implemented differently by every exchange
	 * 
	 * @return A double representing the desired value
	 * @param A
	 *            currencypair and a tagValue ('last', 'high' ..)
	 */
	public abstract java.util.List<PriceValues> getPrice(final String pBaseCurrencyShort, final String pQuotedCurrencyShort)
			throws Exception;

	/**
	 * This is implemented differently by every exchange In this method the
	 * valueTagMapping-List will be created
	 */
	protected abstract void initValueTagMapping();


	public abstract java.util.List<ValueTag> getSupportedValueTags();

}
