package model.currency;

import java.util.Date;

public class CurrencyPairHistoryPoint {
	private final double price;
	private final double volume;
	private final Date date;

	public CurrencyPairHistoryPoint(final double pPrice, final double pVolume,
			final Date pDate) {
		price = pPrice;
		volume = pVolume;
		date = pDate;
	}

	public double getPrice() {
		return price;
	}

	public double getVolume() {
		return volume;
	}

	public Date getDate() {
		return date;
	}

}
