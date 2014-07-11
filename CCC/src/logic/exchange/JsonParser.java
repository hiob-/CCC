package logic.exchange;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * This class contains the JSON parsing for every single supported exchange
 * 
 * @author Oussama Zgheb
 * @version 1.1
 * 
 */

public class JsonParser {
	private String jsonString;

	public JsonParser(String jsonString) {
		this.jsonString = jsonString;
	}

	public double getValueBTCE(String tagName) throws JSONException {
		if (jsonString == null) {
			return -1;
		}
		JSONObject obj = new JSONObject(jsonString);
		// get ticker string
		String ticker = obj.getString("ticker");
		obj = new JSONObject(ticker);
		// get 'last' value
		double lastVal = obj.getDouble(tagName);
		return lastVal;
	}

	public double getValueCRYPTSY(String tagName, String fromCurrency)
			throws JSONException {
		if (jsonString == null) {
			return -1;
		}
		JSONObject obj = new JSONObject(jsonString);

		String returnS = obj.getString("return");
		obj = new JSONObject(returnS);

		String markets = obj.getString("markets");
		obj = new JSONObject(markets);

		String currency = obj.getString(fromCurrency.toUpperCase());
		obj = new JSONObject(currency);

		if (tagName.equals("high") || tagName.equals("low")
				|| tagName.equals("avg")) {
			return cryptsyGetAdditional(tagName, obj);
		} else {
			return obj.getDouble(tagName);
		}

	}

	private double cryptsyGetAdditional(String tagName, JSONObject obj)
			throws JSONException {
		double tradedPrice;
		double targetPrice = 0.0;
		
		String recenttradesS = obj.getString("recenttrades");
		JSONArray recenttradesARR = new JSONArray(recenttradesS);
		
		 // the first value has to be an extreme, for it to be overwritten
		targetPrice = setDefaultTargetPrice(tagName, targetPrice);
		for (int i = 0; i < recenttradesARR.length(); ++i) {
			JSONObject rec = recenttradesARR.getJSONObject(i);
			tradedPrice = rec.getDouble("price");
			targetPrice = updateTargetPrice(tagName, tradedPrice, targetPrice);
		}
		return  (tagName.equals("avg")?(targetPrice/recenttradesARR.length()):(targetPrice));
	}

	private double updateTargetPrice(String tagName, double tradedPrice,
			double targetPrice) {
		
		if (tagName.equals("high")) {
			if (tradedPrice > targetPrice) {
				targetPrice = tradedPrice;
			}
		} else if (tagName.equals("low")) {
			if (tradedPrice < targetPrice) {
				targetPrice = tradedPrice;
			}
		} else if (tagName.equals("avg")) {
			return targetPrice + tradedPrice;
		}
		return targetPrice;
	}

	private double setDefaultTargetPrice(String tagName, double targetPrice) {
		if (tagName.equals("high")) {
			targetPrice = Double.MIN_VALUE;
		} else if (tagName.equals("low")){
			targetPrice = Double.MAX_VALUE;
		} else if (tagName.equals("avg")){
			targetPrice = 0;
		}
		return targetPrice;
	}
}
