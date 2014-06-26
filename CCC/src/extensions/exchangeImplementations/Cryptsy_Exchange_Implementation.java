package extensions.exchangeImplementations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.exchange.HttpGet;
import logic.exchange.JsonParser;
import logic.persistence.DbHandler;
import model.Exchange;
import model.PriceValues;
import model.ValueTag;
import model.currency.CryptsyMarketID;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;

/**
 * 
 * This class contains all the specific implementations for Cryptsy.com
 * 
 * @author Oussama Zgheb
 * @version 1.2
 * 
 */
public class Cryptsy_Exchange_Implementation {

	public static class CRYPTSY_EXCHANGE extends Exchange {

		public CRYPTSY_EXCHANGE(final int ID, final String exchangeName,
				final String exchangeURL) {
			super(ID, exchangeName, exchangeURL);
		}

		public synchronized String getMarketID(final String pBaseCurrencyShort, final String pQuotedCurrencyShort) {

			final String fromShort = pBaseCurrencyShort.toUpperCase();
			final String toShort = pQuotedCurrencyShort.toUpperCase();
			final String currencyKey = fromShort + "/" + toShort;

			Dao<CryptsyMarketID, String> CryptsyMarketID_Dao = null;
			try {
				CryptsyMarketID_Dao = DaoManager.createDao(
						DbHandler.getInstance(), CryptsyMarketID.class);
			} catch (final SQLException e) {
				e.printStackTrace();
			}

			return queryDBforID(CryptsyMarketID_Dao, currencyKey);

		}

		public synchronized String queryDBforID(
				final Dao<CryptsyMarketID, String> CryptsyMarketID_Dao,
				final String currencyKey) {
			GenericRawResults<String[]> rawResults = null;
			List<String[]> results = null;
			try {
				rawResults = CryptsyMarketID_Dao
						.queryRaw("select * from CryptsyMarketID where currencyKey = \""
								+ currencyKey + "\"");
				results = rawResults.getResults();
			} catch (final SQLException e) {
				e.printStackTrace();
			}

			String code = "";
			if (results.size() == 0) {
				throw new IllegalArgumentException(
						"CRYPTSY_GET MARKET ID_ UNKOWN CURRENCY PAIR");
			} else {
				final String[] resultArray = results.get(0);
				code = resultArray[1];
			}

			return code.toString();
		}

		/**
		 * @param A Currencypair which is traded on btc.e
		 * @return A List with PriceValues (Value Tag + Price)
		 */
		@Override
		public synchronized List<PriceValues> getPrice(final String pBaseCurrencyShort, final String pQuotedCurrencyShort)
				throws Exception {

			final String preURL = "http://pubapi.cryptsy.com/api.php?method=singlemarketdata&marketid=";
			final String from = pBaseCurrencyShort.toLowerCase();
			final String id = getMarketID(pBaseCurrencyShort,pQuotedCurrencyShort);
			final String reqURL = preURL + id;
			String retHTML = "";

			final HttpGet hG = new HttpGet();
			retHTML = hG.getHTML(reqURL);				

			// int retCode = hG.getHTTPResponseCode();

			final List<PriceValues> pValList = new ArrayList<PriceValues>();
			for (final ValueTag valueTag : getSupportedValueTags()) {
				final JsonParser json = new JsonParser(retHTML);

				pValList.add(new PriceValues(valueTag, json.getValueCRYPTSY(
						valueTagMapping.get(valueTag), from)));

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
			valueTagMapping.put(ValueTag.PRICE_LAST, "lasttradeprice");
			valueTagMapping.put(ValueTag.VOLUME, "volume");
			valueTagMapping.put(ValueTag.PRICE_HIGH, "high");
			valueTagMapping.put(ValueTag.PRICE_LOW, "low");
			valueTagMapping.put(ValueTag.AVERAGE, "avg");
		}

	}

}