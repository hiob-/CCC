package testing;

import java.io.IOException;

import logic.exchange.DisableCertCheck;
import logic.exchange.HttpGet;

import org.junit.Before;
import org.junit.Test;


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
		DisableCertCheck.doIt();
	}
	
	@Test
	public void testGetHTTP() throws Exception{
		String newLine = System.getProperty("line.separator");

		int retCode=-1;
		String retHTML="";
		HttpGet a = new HttpGet();
		try {
			retHTML= a.getHTML("http://zgheb.com:80/static.html");
			retCode = a.getHTTPResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(retCode, 200); 
		assertEquals(retHTML,"<html>"+newLine+"	\"Static\" <br>"+newLine+"	Content"+newLine+"</html>");
	}
	
	
	@Test
	public void testGetHTTPS() throws Exception{
		String retHTML="";
		HttpGet a = new HttpGet();
		try {
			retHTML= a.getHTML("https://btc-e.com/api/2/ltc_usd/ticker");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(retHTML.length()>5); // just check if we received something, hard to check since its dynamic
	}
	

	
	

	
	
}

