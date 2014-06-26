package testing.models;

import static org.junit.Assert.assertEquals;
import model.currency.CurrencyPair;
import model.rule.Rule;
import model.rule.ValueCompareBigger;
import model.rule.ValueCompareDeltaDown;
import model.rule.ValueCompareDeltaUp;
import model.rule.ValueCompareSmaller;
import model.rule.Rule.RuleType;

import org.junit.Before;
import org.junit.Test;

public class TestRule {

	private final double delta = 0.000001;
	Rule rule1;
	Rule rule2;
	CurrencyPair cp1 = new CurrencyPair();
	CurrencyPair cp2 = new CurrencyPair();
	double valuetarget1 = 1.0;
	double valuetarget2 = 2.0;
	double delta1 = 0.1;
	double delta2 = 0.2;

	@Before
	public void initTest() {
		rule1 = new Rule(cp1, true, true, valuetarget1, delta1,
				RuleType.PriceUpLimit, true,true);
		rule2 = new Rule(cp2, false, false, valuetarget2, delta2,
				RuleType.PriceDownLimit, false,false);
	}
	
	@Test
	public void TestRuleDelta() {
		rule1.setDelta(1.0);
		rule2.setDelta(1.2);
		assertEquals(rule1.getDelta(),1.0,delta);
	}


	@Test
	public void TestCurrencyPairGetter() {
		assertEquals(cp1, rule1.getCurrencyPair());
		assertEquals(cp2, rule2.getCurrencyPair());
	}


	

	@Test
	public void TestRuleTypeGetter() {
		assertEquals(RuleType.PriceUpLimit, rule1.getRuleType());
		assertEquals(RuleType.PriceDownLimit, rule2.getRuleType());
	}

	@Test
	public void TestValueTargeGetter() {
		assertEquals(valuetarget1, rule1.getValueTarget(), delta);
		assertEquals(valuetarget2, rule2.getValueTarget(), delta);
	}

	@Test
	public void TestIsActive() {
		assertEquals(true, rule1.isActive());
		assertEquals(false, rule2.isActive());
	}

	@Test
	public void TestisPopupActive() {
		assertEquals(true, rule1.isPopupActive());
		assertEquals(false, rule2.isPopupActive());
	}

	@Test
	public void TestRuleIdGetter() {
		assertEquals(0, rule1.getRuleId());
		assertEquals(0, rule2.getRuleId());
	}

	@Test
	public void TestValueCompareGetter() {
		assertEquals(ValueCompareBigger.class, rule1.getValueCompare()
				.getClass());
		assertEquals(ValueCompareSmaller.class, rule2.getValueCompare()
				.getClass());
	}


	@Test
	public void TestRuleTypeSetter() {
		rule1.setRuleType(RuleType.PriceUpDelta);
		assertEquals(RuleType.PriceUpDelta, rule1.getRuleType());
		rule2.setRuleType(RuleType.PriceDownDelta);
		assertEquals(RuleType.PriceDownDelta, rule2.getRuleType());
	}

	@Test
	public void TestValueCompare() {
		rule1.setRuleType(RuleType.PriceUpDelta);
		assertEquals(RuleType.PriceUpDelta, rule1.getRuleType());
		rule2.setRuleType(RuleType.PriceDownDelta);
		assertEquals(RuleType.PriceDownDelta, rule2.getRuleType());
		assertEquals(ValueCompareDeltaUp.class, rule1.getValueCompare()
				.getClass());
		assertEquals(ValueCompareDeltaDown.class, rule2.getValueCompare()
				.getClass());
	}

	@Test
	public void TestValueTargetSetter() {
		rule1.setValueTarget(valuetarget2);
		assertEquals(valuetarget2, rule1.getValueTarget(), delta);
		rule2.setValueTarget(valuetarget1);
		assertEquals(valuetarget1, rule2.getValueTarget(), delta);
	}

	@Test
	public void TestIsActiveSetter() {
		rule1.setActive(false);
		assertEquals(false, rule1.isActive());
		rule2.setActive(true);
		assertEquals(true, rule2.isActive());
	}

	@Test
	public void TestIsPopupActiveSetter() {
		rule1.setPopupActive(false);
		assertEquals(false, rule1.isPopupActive());
		rule2.setPopupActive(true);
		assertEquals(true, rule2.isPopupActive());
	}

	@Test
	public void TestRuleIdSetters() {
		rule1.setRuleId(1);
		assertEquals(1, rule1.getRuleId());
		rule2.setRuleId(2);
		assertEquals(2, rule2.getRuleId());
	}

}
