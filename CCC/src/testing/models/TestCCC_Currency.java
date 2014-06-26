package testing.models;

import model.currency.CCC_Currency;

import org.junit.*;

import static org.junit.Assert.*;

public class TestCCC_Currency {

	
	CCC_Currency dogecoin;
	CCC_Currency mincoin;
	CCC_Currency usdollar;
	CCC_Currency usdollar2;
	
	@Before
	public void initTest(){
		dogecoin = new CCC_Currency("DogeCoin","doge", "D", false);
		mincoin = new CCC_Currency("Minecoin","mnc", "", false);
		usdollar = new CCC_Currency("US-Dollar", "USD", "$", true);
		usdollar2 = new CCC_Currency("US-Dollar", "USD", "$", true);
		dogecoin.setId(1234);
		mincoin.setId(2345);
	}
	

	
	@Test
	public void TestCurrencyNameGetter(){
		assertEquals("DogeCoin", dogecoin.getCurrencyName());
		assertEquals("US-Dollar", usdollar.getCurrencyName());
	}
	
	@Test
	public void TestShortNameGetter(){
		assertEquals("doge", dogecoin.getShortName());
		assertEquals("USD", usdollar.getShortName());
	}
	
	@Test
	public void TestSymbolGetter(){
		assertEquals("D", dogecoin.getSymbol());
		assertEquals("$", usdollar.getSymbol());
	}
	
	@Test
	public void TestisFiatGetter(){
		assertEquals(false, dogecoin.isFiat());
		assertEquals(true, usdollar.isFiat());
	}
	
	@Test
	public void TestIdGetter(){
		assertEquals(1234, dogecoin.getId());
		assertEquals(2345, mincoin.getId());
	}
	
	
	//Setter Testing
	@Test
	public void TestCurrencyNameSetter(){
		dogecoin.setCurrencyName("newName");
		assertEquals("newName", dogecoin.getCurrencyName());
	}
	
	@Test
	public void TestShortNameSetter(){
		dogecoin.setShortName("nN");
		assertEquals("nN", dogecoin.getShortName());
	}
	
	@Test
	public void TestSymbolSetter(){
		dogecoin.setSymbol("N");
		assertEquals("N", dogecoin.getSymbol());
	}
	
	@Test
	public void TestFiatSetter(){
		dogecoin.setFiat(true);
		assertEquals(true, dogecoin.isFiat());
	}
	
	@Test
	public void TestIdSetter(){
		dogecoin.setId(0000);
		assertEquals(0000, dogecoin.getId());
	}


	
	@Test
	public void TestEqual(){
		assertEquals(true, usdollar.equals(usdollar2));
		assertEquals(false, dogecoin.equals(mincoin));
	}
	
	@Test
	public void TestToString(){
		assertEquals("US-Dollar (USD)", usdollar.toString());
		assertEquals("Minecoin (mnc)", mincoin.toString());
	}
	
	@Test
	public void TestDefaultConstructor(){
		CCC_Currency emptyCurrency = new CCC_Currency();
		assertEquals(null, emptyCurrency.getCurrencyName());
		assertEquals(null, emptyCurrency.getShortName());
		assertEquals(null, emptyCurrency.getSymbol());
		assertEquals(0, emptyCurrency.getId());
	}
	
}
