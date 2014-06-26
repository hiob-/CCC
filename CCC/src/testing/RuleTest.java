package testing;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import logic.modelController.CurrencyPairController;
import logic.modelController.RuleController;
import logic.preferences.PreferencesHandler;
import model.currency.CurrencyPair;
import model.rule.Rule;
import model.rule.Rule.RuleType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ui.main.Loader;

public class RuleTest {
	private Rule r1;

	@Before
	public void initialize() throws SQLException, IOException {
		Loader.initLogging();
		PreferencesHandler.init();
		Loader.initAll(true);
		final CurrencyPair cP =CurrencyPairController.getInstance()
				.getCurrencyPair(5);
		r1 = new Rule(cP, true, true, 450, 0.0, RuleType.PriceDownLimit, true,true);
		RuleController.getInstance().addRule(r1);
	}

	@After
	public void deinitialize() {
		RuleController.getInstance().removeRule(r1);
		Loader.deinitAll();
	}

	@Test
	public void rulesByCPTest() {
		final CurrencyPair cP =CurrencyPairController.getInstance()
				.getCurrencyPair(5);
		final ArrayList<Rule> cPRuleList = RuleController.getInstance()
				.getRuleListByCurrencyPair(cP);
		boolean found = false;
		for (final Rule rule : cPRuleList) {
			if (rule.getRuleId() == r1.getRuleId()) {
				found = true;
			}
		}
		assertTrue(found);
	}
}
