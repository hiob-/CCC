package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import logic.exchange.ExchangeController;
import logic.preferences.PreferencesHandler;
import model.Exchange;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ui.main.Loader;

import extensions.exchangeImplementations.BTCE_Exchange_Implementation.BTCE_EXCHANGE;

public class ExchangeTest {

	private BTCE_EXCHANGE BTCE = new BTCE_EXCHANGE(0, "BTC-E",
			"http://btc-e.com");

	@Before
	public void initialize() throws SQLException, IOException {
		Loader.initLogging();
		PreferencesHandler.init();
		Loader.initAll(true);
	}

	@After
	public void deinitialize() {
		Loader.deinitAll();
	}

	@Test
	public void testGetExchange() {
		Exchange search = null;

		search = ExchangeController.getInstance().getExchange("BTC-E");

		assertEquals(search.getExchangeName(), BTCE.getExchangeName());
	}

	@Test
	public void testExchangeExists() {
		assertFalse(ExchangeController.getInstance().exchangeExists("DUMMY"));
		assertTrue(ExchangeController.getInstance().exchangeExists("BTC-E"));
	}

	@Test
	public void testGetUnkownExchange() {
		@SuppressWarnings("unused")
		Exchange search = ExchangeController.getInstance().getExchange("DUMMY");
		assertEquals(search, null);
	}
}
