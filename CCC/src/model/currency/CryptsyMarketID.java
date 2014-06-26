package model.currency;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * This class contains the Market ID's for the Cryptsy Exchange
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */

@DatabaseTable(tableName = "CryptsyMarketID")
public class CryptsyMarketID {

	@DatabaseField(columnName = "currencyKey", canBeNull = false)
	private String currencyKey;

	@DatabaseField(columnName = "cryptsyID", canBeNull = false)
	private int cryptsyID;

	public CryptsyMarketID() {

	}

	public String getCurrencyKey() {
		return currencyKey;
	}

	public void setCurrencyKey(final String currencyKey) {
		this.currencyKey = currencyKey;
	}

	public int getCryptsyID() {
		return cryptsyID;
	}

	public void setCryptsyID(final int cryptsyID) {
		this.cryptsyID = cryptsyID;
	}

}
