package extensions.exchangeImplementations;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import logic.exchange.ExchangeController;
import logic.exchange.HttpGet;
import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.modelController.CurrencyController;
import logic.modelController.CurrencyPairController;
import logic.persistence.DbHandler;
import model.currency.CCC_Currency;
import model.currency.CryptsyMarketID;
import model.currency.CurrencyPair;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;


/**
 * This Class checks if there are new/modificated/deleted CurrencyPairs of the
 * Cryptsy-Exchange and edits the Currencypairs accordingly.
 * 
 * @author Luca Tännler
 * @version 1.1
 * */

public class CryptsyCurrencyPairUpdater {
	private HashMap<String, HashMap<String, String>> currencyPairResultMap;

	public CryptsyCurrencyPairUpdater() {

	}

	public void update() {
		Logger.write("[CryptsyCurrencyPairUpdater] Make API-Call",LogLevel.info);
		try {
			getCurrencyPairsFromResult();
			Logger.write("[CryptsyCurrencyPairUpdater] API-Call finished. Starting modification",LogLevel.info);
			modifyCurrencyPairs();
			Logger.write("[CryptsyCurrencyPairUpdater] Modification complete",LogLevel.info);
		} catch (UnknownHostException e) {
			Logger.write("[CryptsyCurrencyPairUpdater] No Internet connection? Failed to Update Cryptsy Exchange:"+e.getMessage(),LogLevel.warning);
		} catch (JSONException e){
			Logger.write("[CryptsyCurrencyPairUpdater] JSON parsing error. Failed to Update Cryptsy Exchange:"+e.getMessage(),LogLevel.warning);
		} catch (Exception e){
			Logger.write("[CryptsyCurrencyPairUpdater] Something went really wrong. Failed to Update Cryptsy Exchange:"+e.getMessage(),LogLevel.error);
		}
		
	}

	// Makes API call & loads data into resultMap (CurrencyPairs of Cryptsy)
	private void getCurrencyPairsFromResult() throws Exception {
		final String reqURL = "http://pubapi.cryptsy.com/api.php?method=marketdatav2";
		String retHTML = "";

		final HttpGet hG = new HttpGet();
		retHTML = hG.getHTML(reqURL);
		
			final JSONObject CPResultObj = (new JSONObject(retHTML))
					.getJSONObject("return").getJSONObject("markets");
			// System.out.println(resultObj.toString());
			final HashMap<String, HashMap<String, String>> out = new HashMap<String, HashMap<String, String>>();
			final Iterator<String> keys = CPResultObj.keys();
			while (keys.hasNext()) {
				final String key = keys.next();
				final HashMap<String, String> values = new HashMap<String, String>();

				final JSONObject subResultObj = CPResultObj.getJSONObject(key);
				final Iterator<String> subKeys = subResultObj.keys();
				while (subKeys.hasNext()) {
					final String subKey = subKeys.next();
					final String value = subResultObj.getString(subKey);
					values.put(subKey, value);
				}
				out.put(key, values);
			}

			currencyPairResultMap = out;
	}


	private void modifyCurrencyPairs() {
		final CurrencyPairController currencyPairCollection = CurrencyPairController.getInstance();
		final CurrencyController currencyCollection = CurrencyController.getInstance();
		final ExchangeController exchangeCollection = ExchangeController.getInstance();
		final List<CurrencyPair> storedCurrencyPairList = new ArrayList<CurrencyPair>(
				currencyPairCollection.getAllCurrencyPairs(exchangeCollection
						.getExchange("cryptsy")));

		// For testing remove..

		// currencyPairCollection.createCurrencyPair(
		// currencyCollection.getCurrency(1),
		// currencyCollection.getCurrency(1),
		// exchangeCollection.getExchange("cryptsy"));

		// Iterate through the 'fresh' CP-List received from Cryptsy
		for (final String key : currencyPairResultMap.keySet()) {
			CCC_Currency baseCurrency = currencyCollection
					.getCurrency(currencyPairResultMap.get(key).get(
							"primarycode"));
			CCC_Currency quotedCurrency = currencyCollection
					.getCurrency(currencyPairResultMap.get(key).get(
							"secondarycode"));

			if (baseCurrency == null) {
				System.out
						.println("CCPUpdater: BaseCurrency doesn't exist. Creating it. "
								+ currencyPairResultMap.get(key).get(
										"primarycode"));
				final CCC_Currency newCurrency = new CCC_Currency(
						currencyPairResultMap.get(key).get("primaryname"),
						currencyPairResultMap.get(key).get("primarycode"), "",
						false);
				currencyCollection.addToList(newCurrency);
				newCurrency.triggerNew();
				baseCurrency = newCurrency;
			}
			if (quotedCurrency == null) {
				System.out
						.println("CCPUpdater: QuotedCurrency doesn't exist. Creating it. "
								+ currencyPairResultMap.get(key).get(
										"secondarycode"));
				final CCC_Currency newCurrency = new CCC_Currency(
						currencyPairResultMap.get(key).get("secondaryname"),
						currencyPairResultMap.get(key).get("secondarycode"),
						"", false);
				currencyCollection.addToList(newCurrency);
				newCurrency.triggerNew();
				quotedCurrency = newCurrency;
			}

			final CurrencyPair currencyPair = currencyPairCollection
					.getCurrencyPair(baseCurrency, quotedCurrency,
							exchangeCollection.getExchange("cryptsy"));

			// Add new CurrencyPair or remove it from the temp. storedCPList
			if (currencyPair == null) {
				System.out
						.println("CCPUpdater: Adding new CurrencyPair: Cryptsy => "
								+ baseCurrency + "/" + quotedCurrency);
				currencyPairCollection.createCurrencyPair(baseCurrency,
						quotedCurrency,
						exchangeCollection.getExchange("cryptsy"));
				// Create Cryptsy ID Entry
				Dao<CryptsyMarketID, String> CryptsyMarketID_Dao = null;
				try {
					final CryptsyMarketID cMarketID = new CryptsyMarketID();
					cMarketID.setCurrencyKey(baseCurrency.getShortName()
							.toUpperCase()
							+ "/"
							+ quotedCurrency.getShortName().toUpperCase());
					cMarketID.setCryptsyID(Integer
							.parseInt(currencyPairResultMap.get(key).get(
									"marketid")));
					CryptsyMarketID_Dao = DaoManager.createDao(
							DbHandler.getInstance(), CryptsyMarketID.class);
					CryptsyMarketID_Dao.create(cMarketID);
					Logger.write(
							"Created new CryptsyMarketID for  new CurrencyPair",
							LogLevel.info);
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			} else {
				storedCurrencyPairList.remove(currencyPair);
			}
		}

		// Delete storedCPList CPs (CurrencyPairs which don't exist anymore on
		// the Exchange)
		if (!storedCurrencyPairList.isEmpty()) {
			for (final CurrencyPair currPair : storedCurrencyPairList) {
				System.out.println("CCPUpdater: Remove CurrencyPair: "
						+ currPair);
				currencyPairCollection.removeCurrencyPair(currPair);

				// Delete Cryptsy Market ID in DB
				Dao<CryptsyMarketID, String> CryptsyMarketID_Dao = null;
				try {
					CryptsyMarketID_Dao = DaoManager.createDao(
							DbHandler.getInstance(), CryptsyMarketID.class);
					final DeleteBuilder<CryptsyMarketID, String> db = CryptsyMarketID_Dao
							.deleteBuilder();
					db.where().eq(
							"currencyKey",
							currPair.getBaseCurrency().getShortName()
									.toUpperCase()
									+ "/"
									+ currPair.getQuotedCurrency()
											.getShortName().toUpperCase());
					CryptsyMarketID_Dao.delete(db.prepare());
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
