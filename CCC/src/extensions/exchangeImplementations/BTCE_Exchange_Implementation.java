package extensions.exchangeImplementations;

import java.util.ArrayList;
import java.util.List;

import logic.exchange.HttpGet;
import logic.exchange.JsonParser;
import model.Exchange;
import model.PriceValues;
import model.ValueTag;



/**
 * 
 * This class contains all the specific implementations for BTC-E.com
 * 
 * @author Oussama Zgheb
 * @version 1.1
 * 
 */

public class BTCE_Exchange_Implementation {

	public static class BTCE_EXCHANGE extends Exchange {


		public BTCE_EXCHANGE(final int ID, final String exchangeName,
				final String exchangeURL) {
			super(ID, exchangeName, exchangeURL);
		}

		/**
		 * @param A Currencypair which is traded on btc.e
		 * @return A List with PriceValues (Value Tag + Price)
		 */
		@Override
		public synchronized List<PriceValues> getPrice(final String pBaseCurrencyShort, final String pQuotedCurrencyShort)
				throws Exception {
			// build GET url
			final String preURL = "https://btc-e.com/api/2/";
			final String postURL = "/ticker";
			final String from = pBaseCurrencyShort.toLowerCase();
			final String to = pQuotedCurrencyShort.toLowerCase();
			final String reqURL = preURL + from + "_" + to + postURL;
			String retHTML = "";
			final HttpGet hG = new HttpGet();
				retHTML = hG.getHTML(reqURL);				
			final List<PriceValues> pValList = new ArrayList<PriceValues>();
			for (final ValueTag valueTag : getSupportedValueTags()) {
				final JsonParser json = new JsonParser(retHTML);
				pValList.add(new PriceValues(valueTag, json
						.getValueBTCE(valueTagMapping.get(valueTag))));
			}
			return pValList;

		}

		@Override
		public synchronized List<ValueTag> getSupportedValueTags() {
			final List<ValueTag> supVT = new ArrayList<ValueTag>(
					valueTagMapping.keySet());
			return supVT;
		}

		@Override
		protected void initValueTagMapping() {
			valueTagMapping.put(ValueTag.PRICE_LAST, "last");
			valueTagMapping.put(ValueTag.PRICE_HIGH, "high");
			valueTagMapping.put(ValueTag.PRICE_LOW, "low");
			valueTagMapping.put(ValueTag.VOLUME, "vol");
			valueTagMapping.put(ValueTag.AVERAGE, "avg");
		}

	}

}