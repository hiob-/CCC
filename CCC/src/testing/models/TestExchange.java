package testing.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.Exchange;
import model.PriceValues;
import model.ValueTag;
import model.currency.CurrencyPair;

import org.junit.Before;
import org.junit.Test;




public class TestExchange {
	
	Exchange exchange1;
	Exchange exchange2;
	
	@Before
	public void initTest(){
		exchange1 = new DummyExchange(1, "TestExchange1", "TestURL1");
		exchange2 = new DummyExchange(2, "TestExchange2", "TestURL2");
		
	}
	
	@Test
	public void TestToString(){
		assertEquals("TestExchange1",exchange1.toString());
		assertEquals("TestExchange2",exchange2.toString());	
	}
	
	
	@Test
	public void TestURLGetter(){
		assertEquals("TestURL1", exchange1.getURL());
		assertEquals("TestURL2", exchange2.getURL());
	}
	
	@Test
	public void TestExchangeNameString(){
		assertEquals("TestExchange1",exchange1.getExchangeNameString());
		assertEquals("TestExchange2",exchange2.getExchangeNameString());
	}
	
	@Test
	public void TestExchangeNameGetter(){
		assertEquals("TestExchange1", exchange1.getExchangeName());
		assertEquals("TestExchange2", exchange2.getExchangeName());
	}
	
	@Test
	public void TestExchangeIDgetter(){
		assertEquals(1, exchange1.getExchangeID());
		assertEquals(2, exchange2.getExchangeID());
	}
	
	@Test
	public void TestIdGetter(){
		assertEquals(1, exchange1.getId());
		assertEquals(2, exchange2.getId());
	}
	
	@Test
	public void TestSetters(){
		exchange1.setId(3);
		assertEquals(3, exchange1.getId());
		assertEquals(3, exchange1.getExchangeID());
	}
	

	class DummyExchange extends Exchange{

		List<PriceValues> priceValues = new ArrayList<PriceValues>();;
		List<ValueTag> supportedValueTags = new ArrayList<ValueTag>();
		
		public DummyExchange(int ID, String exchangeName, String exchangeURL) {
			super(ID, exchangeName, exchangeURL);
			
			PriceValues pv = new PriceValues(ValueTag.PRICE_LAST, 1.1);
			PriceValues pv2 = new PriceValues(ValueTag.PRICE_LAST, 2.2);
			PriceValues pv3 = new PriceValues(ValueTag.PRICE_LAST, 2.3);
			
			priceValues.add(pv);
			priceValues.add(pv2);
			priceValues.add(pv3);
			
			supportedValueTags.add(ValueTag.PRICE_LAST);
			supportedValueTags.add(ValueTag.AVERAGE);
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
