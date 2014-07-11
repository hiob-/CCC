package testing;

import java.io.IOException;

import logic.exchange.DisableCertCheck;
import logic.exchange.HttpGet;
import logic.preferences.PreferencesHandler;

import org.junit.Before;
import org.junit.Test;

import ui.main.Loader;
import static org.junit.Assert.*;

/**
 * 
 * This class tests HTTP Get & HTTPS Get
 * @author Oussama Zgheb
 * @version 1.0
 *
 */

public class TestHTTPGetLive {
	
	@Before
	public void disableCertCheck() throws Exception{
		Loader.initLogging();
		PreferencesHandler.init();
		Loader.initAll(true);
	}
	
	@Test
	public void testGetHTTP() throws Exception{
		int retCode=-1;
		String retHTML="";
		HttpGet a = new HttpGet();
		try {
			retHTML= a.getHTML("http://www.fsf.org/"); // ;)
			retCode = a.getHTTPResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(retCode, 200); 
		assertTrue(retHTML.length()>5); // just check if we received something, hard to check since its dynamic
	}
	
	
	@Test
	public void testGetHTTPS_BTCE() throws Exception{
		String retHTML="";
		HttpGet a = new HttpGet();
		try {
			retHTML= a.getHTML("https://btc-e.com/api/2/ltc_usd/ticker");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(retHTML.length()>5); 
	}
	

	
	

	
	
}

