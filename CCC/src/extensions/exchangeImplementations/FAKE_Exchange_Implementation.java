package extensions.exchangeImplementations;

import java.util.ArrayList;
import java.util.List;

import logic.exchange.ExchangeController;
import logic.modelController.CurrencyController;
import logic.modelController.CurrencyPairController;
import model.Exchange;
import model.PriceValues;
import model.ValueTag;


/**
 * 
 * This is a fake Exchange implementation used only for testing.
 * It returns random value courses.
 * 
 * @author Luca T�nnler
 * @version 1.0
 * 
 */
public class FAKE_Exchange_Implementation {
	public static class FAKE_EXCHANGE extends Exchange {

		public FAKE_EXCHANGE(final int ID, final String exchangeName,
				final String exchangeURL) {
			super(ID, exchangeName, exchangeURL);
		}

		@Override
		public synchronized List<PriceValues> getPrice(final String pBaseCurrencyShort, final String pQuotedCurrencyShort)
				throws Exception {

			final List<PriceValues> pValList = new ArrayList<PriceValues>();
			for (final ValueTag valueTag : getSupportedValueTags()) {
				double newPrice = 0.0;
				final double oldPrice = CurrencyPairController.getInstance().getCurrencyPair(
						CurrencyController.getInstance().getCurrency(pBaseCurrencyShort),
						CurrencyController.getInstance().getCurrency(pQuotedCurrencyShort),
						ExchangeController.getInstance().getExchange(this.getExchangeID())).getPrice(ValueTag.PRICE_LAST);
				if (oldPrice > 0) {
					// Down(0.3-0.6) or Up(0.6-1) or Same(0-0.299)
					double randomVal = Math.random();
					if (randomVal >= 0.3 && randomVal <= 0.6 && oldPrice > 0) {
						newPrice = oldPrice - (Math.random() * 10);
					} else if(randomVal > 0.6 && oldPrice > 0) {
						newPrice = oldPrice + (Math.random() * 10);
					} else{
						newPrice = oldPrice;
					}
				} else {
					newPrice = Math.random() * 100 + Math.random() * 100;
				}
				pValList.add(new PriceValues(valueTag, newPrice));
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
