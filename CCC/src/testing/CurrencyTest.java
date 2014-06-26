package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import logic.modelController.CurrencyController;
import logic.preferences.PreferencesHandler;
import model.currency.CCC_Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ui.main.Loader;

public class CurrencyTest {
	
	private CCC_Currency  doge = new CCC_Currency("DogeCoin","doge", "D", false);
	@SuppressWarnings("unused")
	private CCC_Currency  mincoin = new CCC_Currency("Mincoin","mnc", "", false);

	
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
	public void testGetCurrency() {
		CCC_Currency search = null;
		search = CurrencyController.getInstance().getCurrency("DOGE");
		assertEquals(search.getCurrencyName(),doge.getCurrencyName());
	}
	
	@Test()
	public void testGetUnkownCurrency() {
		@SuppressWarnings("unused")
		CCC_Currency search = CurrencyController.getInstance().getCurrency("DUMMY");
		assertEquals(search,null);
	}
	
	@Test()
	public void testEquals() {
		CCC_Currency ltc = CurrencyController.getInstance().getCurrency("LTC");
		CCC_Currency ltc2 = CurrencyController.getInstance().getCurrency("LTC");
		CCC_Currency doge = CurrencyController.getInstance().getCurrency("DOGE");

		assertTrue(ltc.equals(ltc2));
		assertFalse(ltc.equals(doge));
	}

}

