package testing.models;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.Exchange;
import model.PriceValues;
import model.ValueTag;
import model.currency.CCC_Currency;
import model.currency.CurrencyPair;

import org.junit.Before;
import org.junit.Test;



public class TestCurrencyPair {
	
	private static final double delta = 0.000001;
	
	CCC_Currency dogecoin;
	CCC_Currency mincoin;
	CCC_Currency usdollar;
	CCC_Currency euro;
	
	Exchange exchange1;
	Exchange exchange2;
	
	CurrencyPair currPair1;
	CurrencyPair currPair2;
	CurrencyPair currPair3;
	
	List<ValueTag> tags;
	List<PriceValues> values1 = null;
	List<PriceValues> values2 = null;
	List<PriceValues> prices;
	Double testValue_1= 456.789;
	Double testValue_2= 567.891;
	
	
	
	@Before
	public void initTest() throws Exception{
		dogecoin = new CCC_Currency("DogeCoin","doge", "D", false);
		mincoin = new CCC_Currency("Minecoin","mnc", "", false);
		usdollar = new CCC_Currency("US-Dollar", "USD", "$", true);
		euro = new CCC_Currency("Euro", "EUR", "E", true);
		dogecoin.setId(1);
		mincoin.setId(2);
		usdollar.setId(3);
		euro.setId(4);
		
		exchange1 = new DummyExchange(1, "TestExchange1", "TestURL1");
		exchange2 = new DummyExchange(2, "TestExchange2", "TestURL2");
		
		currPair1 = new CurrencyPair(usdollar, dogecoin, exchange1);
		currPair2 = new CurrencyPair(euro, mincoin, exchange2);
		currPair3 = new CurrencyPair(euro, mincoin, exchange2);
		
		tags = currPair1.getExchange().getSupportedValueTags();
		values1 = exchange1.getPrice(currPair1.getBaseCurrency().getShortName(), currPair1.getQuotedCurrency().getShortName());
		values2 = exchange2.getPrice(currPair2.getBaseCurrency().getShortName(), currPair2.getQuotedCurrency().getShortName());
		
		prices = currPair1.getPriceValueList();
		prices.add(new PriceValues(ValueTag.PRICE_LOW,testValue_1));
		prices.add(new PriceValues(ValueTag.AVERAGE, testValue_2));
		
	}
	
	@Test
	public void TestEquals(){
		assertEquals(true, currPair2.equals(currPair3));
		assertEquals(false, currPair2.equals(currPair1));
	}
	
	@Test
	public void TestBaseCurrencyGetter(){
		assertEquals(usdollar,currPair1.getBaseCurrency());
		assertEquals(euro, currPair2.getBaseCurrency());
	}
	
	@Test
	public void TestQuotedCurrencyGetter(){
		assertEquals(dogecoin, currPair1.getQuotedCurrency());
		assertEquals(mincoin, currPair2.getQuotedCurrency());
	}
	
	@Test
	public void TestisSubscribed(){
		assertEquals(false, currPair1.isSubscribed());
		assertEquals(false, currPair2.isSubscribed());
	}
	
	@Test
	public void TestIdGetter(){
		assertEquals(0, currPair1.getId());
		assertEquals(0, currPair2.getId());
	}
	
	@Test
	public void TestExchangeGetter(){
		assertEquals(exchange1, currPair1.getExchange());
		assertEquals(exchange2, currPair2.getExchange());
	}
	
	@Test
	public void TestExchangeIdGetter(){
		assertEquals(1, currPair1.getExchange_ID());
		assertEquals(2, currPair2.getExchange_ID());
	}
	
	@Test
	public void TestToString(){
		assertEquals("TestExchange1: USD/doge", currPair1.toString());
		assertEquals("TestExchange2: EUR/mnc", currPair2.toString());
	}

	@Test
	public void TestTagGetter(){
		assertEquals(ValueTag.AVERAGE, tags.get(0));
		assertEquals(ValueTag.PRICE_LAST, tags.get(1));
	}
	
	@Test
	public void TestValuesGetter(){
		for(int i = 0; i<tags.size(); i++){
			currPair1.setPrice(tags.get(i), values1.get(i).getValue());
			currPair2.setPrice(tags.get(i), values2.get(i).getValue());
		}
		
			assertEquals(testValue_1, prices.get(0).getValue(), delta);
		
			assertEquals(testValue_2, prices.get(1).getValue(), delta);
	}
	
	@Test
	public void TestPriceGetter(){
//		assertEquals(1.1, currPair1.getPrice(tags.get(0)), delta);
//		assertEquals(2.2, currPair1.getPrice(tags.get(1)),delta);
//		assertEquals(1.1, currPair2.getPrice(tags.get(0)), delta);
//		assertEquals(2.2, currPair2.getPrice(tags.get(1)),delta);
	}
	
	@Test
	public void TestPriceSetter(){
		currPair1.setPrice(tags.get(0), 5.5);
		currPair2.setPrice(tags.get(1), 7.7);
		assertEquals(5.5, currPair1.getPrice(tags.get(0)), delta);
		//assertEquals(1.1, currPair2.getPrice(tags.get(0)), delta);
		assertEquals(7.7, currPair2.getPrice(tags.get(1)),delta);
	}
	
	@Test
	public void TestPriceWithInvalideTags(){
		assertEquals(-1, currPair1.getPrice(ValueTag.OTHER_1), delta);
		assertEquals(-1, currPair2.getPrice(ValueTag.OTHER_1), delta);
	}
	
	@Test
	public void TestExchange_IDSetters(){
		currPair1.setExchange_ID(1);
		assertEquals(1, currPair1.getExchange_ID());
		currPair2.setExchange_ID(2);
		assertEquals(2, currPair2.getExchange_ID());
	}
	
	@Test
	public void TestExchangeSetter(){
		currPair1.setExchange(exchange2);
		assertEquals(exchange2, currPair1.getExchange());
		currPair2.setExchange(exchange2);
		assertEquals(exchange2, currPair2.getExchange());
	}
	
	@Test
	public void TestBaseCurrencySetter(){
		currPair1.setBaseCurrency(euro);
		assertEquals(euro,currPair1.getBaseCurrency());
		currPair2.setBaseCurrency(usdollar);
		assertEquals(usdollar, currPair2.getBaseCurrency());
	}
	
	@Test
	public void TestIdSetter(){
		currPair1.setId(1);
		assertEquals(1, currPair1.getId());
		currPair2.setId(2);
		assertEquals(2, currPair2.getId());
	}
	
	@Test
	public void TestSubscribedSetter(){
		currPair1.setSubscribed(true);
		assertEquals(true, currPair1.isSubscribed());
		currPair2.setSubscribed(false);
		assertEquals(false, currPair2.isSubscribed());
	}
	
	@Test
	public void TestDefaultConstructor(){
		CurrencyPair cp = new CurrencyPair();
		assertEquals(null, cp.getBaseCurrency());
		assertEquals(null, cp.getExchange());
		assertEquals(null, cp.getQuotedCurrency());
	}

	
	class DummyExchange extends Exchange{

		List<PriceValues> priceValues = new ArrayList<PriceValues>();;
		List<ValueTag> supportedValueTags = new ArrayList<ValueTag>();
		
		public DummyExchange(int ID, String exchangeName, String exchangeURL) {
			super(ID, exchangeName, exchangeURL);
			
			PriceValues pv = new PriceValues(ValueTag.PRICE_LAST, 1.1);
			PriceValues pv2 = new PriceValues(ValueTag.PRICE_LAST, 2.2);
			
			priceValues.add(pv);
			priceValues.add(pv2);
			
			supportedValueTags.add(ValueTag.AVERAGE);
			supportedValueTags.add(ValueTag.PRICE_LAST);
		}

		@Override
		public List<ValueTag> getSupportedValueTags() {
			return supportedValueTags;
		}

		@Override
		protected void initValueTagMapping() {
		}

		@Override
		public List<PriceValues> getPrice(String pBaseCurrencyShort,
				String pQuotedCurrencyShort) throws Exception {
			return priceValues;
		}
		
	}
	
	
}
