package testing;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import logic.exchange.ExchangeController;
import logic.modelController.CurrencyController;
import logic.modelController.CurrencyPairController;
import logic.preferences.PreferencesHandler;
import model.currency.CurrencyPair;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ui.main.Loader;

public class CurrencyPairTest {
	

	
	@Before
	public void initialize() throws SQLException, IOException {
		Loader.initLogging();
		PreferencesHandler.init();
		Loader.initAll(true);
	}
	
	@After
	public void deinitalize(){
		Loader.deinitAll();
	}

	@Test
	public void testEquals() {
		CurrencyPair cP_LTC_USD_BTCE = CurrencyPairController.getInstance().getCurrencyPair(
				CurrencyController.getInstance().getCurrency("LTC"),
				CurrencyController.getInstance().getCurrency("USD"),
				ExchangeController.getInstance().getExchange("BTC-E"));
		
		CurrencyPair cP_LTC_USD_BTCE_2 = CurrencyPairController.getInstance().getCurrencyPair(
				CurrencyController.getInstance().getCurrency("LTC"),
				CurrencyController.getInstance().getCurrency("USD"),
				ExchangeController.getInstance().getExchange("BTC-E"));
		
		CurrencyPair cP_LTC_BTC_BTCE = CurrencyPairController.getInstance().getCurrencyPair(
				CurrencyController.getInstance().getCurrency("LTC"),
				CurrencyController.getInstance().getCurrency("BTC"),
				ExchangeController.getInstance().getExchange("BTC-E"));
		
		
		CurrencyPair cP_DOGE_LTC_Cryptsy = CurrencyPairController.getInstance().getCurrencyPair(
				CurrencyController.getInstance().getCurrency("DOGE"),
				CurrencyController.getInstance().getCurrency("LTC"),
				ExchangeController.getInstance().getExchange("CRYPTSY"));
		
		
		assertTrue(cP_LTC_USD_BTCE.equals(cP_LTC_USD_BTCE_2));
		assertFalse(cP_LTC_USD_BTCE.equals(cP_LTC_BTC_BTCE));
		assertFalse(cP_LTC_USD_BTCE.equals(cP_DOGE_LTC_Cryptsy));

	}
	
	
	

}

