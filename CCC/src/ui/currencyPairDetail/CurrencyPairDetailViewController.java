package ui.currencyPairDetail;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.TableRowSorter;

import ui.currencyPairPriceChart.CurrencyPairPriceChart;
import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import model.ObserverObjectWrapper;
import model.PriceValues;
import model.ValueTag;
import model.currency.CurrencyPair;
import model.currency.CurrencyPairPriceUpdateWrapper;


/**
 * Controller for CurrencyPair Detail View
 * 
 * @author Oussama Zgheb
 * @version 1.1
 * */

public class CurrencyPairDetailViewController implements Observer {
	private final CurrencyPair cP;
	private CurrencyPairDetailView currencyPairDetailView;
	private TableRowSorter<ValueTagPriceTabelModel> sorter;
	private final CurrencyPairPriceChart cpPriceChart;

	public CurrencyPairDetailViewController(final CurrencyPair cP) {
		this.cP = cP;
		this.cP.addObserver(this);
		cpPriceChart = new CurrencyPairPriceChart("", this.cP.getCpHistory());
		initView();
		initListeners();
		initSorters();
	}

	private void initSorters() {
		ValueTagPriceTabelModel tableModel = currencyPairDetailView
				.getValueTagPriceListModel();
		sorter = new TableRowSorter<>(
				tableModel);
		currencyPairDetailView.getValueTagPricesTable().setRowSorter(sorter);

		// SORT PRICE VALUE TAGS
		sorter.setComparator(0, new Comparator<ValueTag>() {
			@Override
			public int compare(ValueTag vtL, ValueTag vtR) {
				return vtL.toString().compareTo(vtR.toString());
			}
		});
		
		// indicator
		sorter.setComparator(1, new Comparator<String>() {
			@Override
			public int compare(String vtL, String vtR) {
				return vtL.compareTo(vtR);
			}
		});
		
		// values not sortable
		sorter.setSortable(2, false);


		sorter.setSortsOnUpdates(true);

	}

	private void initListeners() {
		// open browser with URL of exchange when clicking on exchange label
		currencyPairDetailView.getTextfield_Exchange().addMouseListener(
				new MouseAdapter() {
					@Override
					public void mouseClicked(final MouseEvent arg0) {
						final String url = cP.getExchange().getURL();
						try {
							java.awt.Desktop.getDesktop().browse(
									java.net.URI.create(url));
						} catch (final IOException e) {
							Logger.write(
									"[CurrencyPairDetailViewController] Failed with getDestkop.browse(): "
											+ e.getMessage(), LogLevel.error);
						}
					}
				});
	}

	private void initView() {
		currencyPairDetailView = new CurrencyPairDetailView();
		currencyPairDetailView.setTextfield_BaseCurrency(cP.getBaseCurrency()
				.toString());
		currencyPairDetailView.setTextfield_QuotedCurrency(cP
				.getQuotedCurrency().toString());
		currencyPairDetailView.setTextfield_Exchange(cP.getExchange()
				.toString());

		final List<PriceValues> priceValueList = cP.getPriceValueList();
		for (final PriceValues priceValue : priceValueList) {
			currencyPairDetailView.addValueTagPrice(priceValue);
		}

		// currencyPairDetailView.addChart(cP.getCpHistory().getCpPriceChart().getChartPanel());
		currencyPairDetailView.addCurrencyPairPriceChart(cpPriceChart);
	}

	public CurrencyPairDetailView getPanel() {
		return currencyPairDetailView;
	}

	@Override
	public void update(final Observable arg0, final Object arg1) {
		if (arg1 instanceof CurrencyPairPriceUpdateWrapper) {
			final CurrencyPairPriceUpdateWrapper cppuw = (CurrencyPairPriceUpdateWrapper) arg1;
			if (cppuw != null) {
				currencyPairDetailView.addValueTagPrice(cppuw.getPriceValue());
			}
		} else if (arg0 instanceof CurrencyPair) {
			if (arg1 instanceof ObserverObjectWrapper) {
				currencyPairDetailView.updateUI();
			}
		}
		sorter.sort(); // we need to tell the tablemodel to sort stuff again, as there might be a new valuetag in there!
	}

	public void deleteObserver() {
		cP.deleteObserver(this);
	}

}
