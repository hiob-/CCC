package ui.currencyPairDetail;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import model.PriceValues;

import org.jfree.chart.ChartPanel;

import ui.ColorScheme;
import ui.currencyPairPriceChart.CurrencyPairPriceChart;
import ui.internationalization.Strings;

/**
 * CurrencyPair Detail View
 * 
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * */

public class CurrencyPairDetailView extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTextField Textfield_Exchange;
	private final JTextField Textfield_BaseCurrency;
	private final JTextField Textfield_QuotedCurrency;
	private final ValueTagPriceTabelModel valueTagPriceListModel;
	private final JScrollPane scrollPane;
	private final JTable valueTagPricesTable;


	private final JPanel graphPanel;

	/**
	 * Create the panel.
	 */
	public CurrencyPairDetailView() {

		setSize(437, 420);
		setMinimumSize(getSize());

		// LAYOUT
		// ------------------------------------------

		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 10, 10, 10, 10, 0 };
		gridBagLayout.rowHeights = new int[] { 15, 0, 0, 0, 0, 150, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		// LABELS EXPLAINING TEXTFIELDS
		// ------------------------------------------

		final JLabel lblExchange = new JLabel(
				Strings.getString("CurrencyPairDetailView.exchange")); //$NON-NLS-1$
		lblExchange.setFont(new Font("Tahoma", Font.BOLD, 13));
		final GridBagConstraints gbc_lblExchange = new GridBagConstraints();
		gbc_lblExchange.insets = new Insets(0, 0, 5, 5);
		gbc_lblExchange.gridx = 1;
		gbc_lblExchange.gridy = 1;
		add(lblExchange, gbc_lblExchange);

		final JLabel lblBaseCurrency = new JLabel(
				Strings.getString("CurrencyPairDetailView.baseCurrency"));
		lblBaseCurrency.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBaseCurrency.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_lblBaseCurrency = new GridBagConstraints();
		gbc_lblBaseCurrency.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_lblBaseCurrency.insets = new Insets(0, 0, 5, 5);
		gbc_lblBaseCurrency.gridx = 2;
		gbc_lblBaseCurrency.gridy = 1;
		add(lblBaseCurrency, gbc_lblBaseCurrency);

		final JLabel lblQuotedCurrency = new JLabel(
				Strings.getString("CurrencyPairDetailView.quotedCurrency"));
		lblQuotedCurrency.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblQuotedCurrency.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_lblQuotedCurrency = new GridBagConstraints();
		gbc_lblQuotedCurrency.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuotedCurrency.gridx = 3;
		gbc_lblQuotedCurrency.gridy = 1;
		add(lblQuotedCurrency, gbc_lblQuotedCurrency);

		Textfield_Exchange = new JTextField();
		Textfield_Exchange.setEnabled(false);
		Textfield_Exchange.setDisabledTextColor(ColorScheme.GOLD_DARK);
		Textfield_Exchange.repaint();
		Textfield_Exchange.setHorizontalAlignment(SwingConstants.CENTER);

		final GridBagConstraints gbc_Textfield_Exchange = new GridBagConstraints();
		gbc_Textfield_Exchange.insets = new Insets(0, 0, 5, 5);
		gbc_Textfield_Exchange.fill = GridBagConstraints.HORIZONTAL;
		gbc_Textfield_Exchange.gridx = 1;
		gbc_Textfield_Exchange.gridy = 3;
		add(Textfield_Exchange, gbc_Textfield_Exchange);
		Textfield_Exchange.setColumns(10);

		Textfield_BaseCurrency = new JTextField();
		Textfield_BaseCurrency.setDisabledTextColor(ColorScheme.FONTBLACK);
		Textfield_BaseCurrency.setHorizontalAlignment(SwingConstants.CENTER);
		Textfield_BaseCurrency.setEnabled(false);
		Textfield_BaseCurrency.setColumns(10);
		final GridBagConstraints gbc_Textfield_BaseCurrency = new GridBagConstraints();
		gbc_Textfield_BaseCurrency.insets = new Insets(0, 0, 5, 5);
		gbc_Textfield_BaseCurrency.fill = GridBagConstraints.HORIZONTAL;
		gbc_Textfield_BaseCurrency.gridx = 2;
		gbc_Textfield_BaseCurrency.gridy = 3;
		add(Textfield_BaseCurrency, gbc_Textfield_BaseCurrency);

		Textfield_QuotedCurrency = new JTextField();
		Textfield_QuotedCurrency.setDisabledTextColor(ColorScheme.FONTBLACK);
		Textfield_QuotedCurrency.setHorizontalAlignment(SwingConstants.CENTER);
		Textfield_QuotedCurrency.setEnabled(false);
		Textfield_QuotedCurrency.setColumns(10);
		final GridBagConstraints gbc_Textfield_QuotedCurrency = new GridBagConstraints();
		gbc_Textfield_QuotedCurrency.insets = new Insets(0, 0, 5, 5);
		gbc_Textfield_QuotedCurrency.fill = GridBagConstraints.HORIZONTAL;
		gbc_Textfield_QuotedCurrency.gridx = 3;
		gbc_Textfield_QuotedCurrency.gridy = 3;
		add(Textfield_QuotedCurrency, gbc_Textfield_QuotedCurrency);

		graphPanel = new JPanel();
		final GridBagConstraints gbc_graphPanel = new GridBagConstraints();
		gbc_graphPanel.gridwidth = 3;
		gbc_graphPanel.insets = new Insets(0, 0, 5, 5);
		gbc_graphPanel.fill = GridBagConstraints.BOTH;
		gbc_graphPanel.gridx = 1;
		gbc_graphPanel.gridy = 5;
		add(graphPanel, gbc_graphPanel);
		graphPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 6;
		add(scrollPane, gbc_scrollPane);

		valueTagPriceListModel = new ValueTagPriceTabelModel();
		valueTagPricesTable = new JTable(valueTagPriceListModel);
		scrollPane.setViewportView(valueTagPricesTable);

		
		valueTagPricesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		valueTagPricesTable.getColumnModel().getColumn(0).setMinWidth(100);
		valueTagPricesTable.getColumnModel().getColumn(0).setMaxWidth(100);

		valueTagPricesTable.getColumnModel().getColumn(1).setPreferredWidth(60);
		valueTagPricesTable.getColumnModel().getColumn(1).setMinWidth(60);
		valueTagPricesTable.getColumnModel().getColumn(1).setMaxWidth(60);

		valueTagPricesTable.getColumnModel().getColumn(2).setMinWidth(120);
		valueTagPricesTable.getColumnModel().getColumn(2).setPreferredWidth(160);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		valueTagPricesTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		
	}

	public JTextField getTextfield_Exchange() {
		return Textfield_Exchange;
	}

	public void setTextfield_Exchange(final String textfield_Exchange) {
		Textfield_Exchange.setText(textfield_Exchange);
	}

	public void setTextfield_BaseCurrency(final String textfield_BaseCurrency) {
		Textfield_BaseCurrency.setText(textfield_BaseCurrency);
	}

	public void setTextfield_QuotedCurrency(
			final String textfield_QuotedCurrency) {
		Textfield_QuotedCurrency.setText(textfield_QuotedCurrency);
	}

	public void addValueTagPrice(final PriceValues pV) {
		valueTagPriceListModel.addTagValuePrice(pV);
	}

	public void refreshPriceTagTable() {
		valueTagPriceListModel.fireTableDataChanged();
	}

	public void addChart(final ChartPanel pChartPanel) {
		graphPanel.add(pChartPanel, BorderLayout.CENTER);
	}
	
	public ValueTagPriceTabelModel getValueTagPriceListModel() {
		return valueTagPriceListModel;
	}

	public void addCurrencyPairPriceChart(
			final CurrencyPairPriceChart pCurrencyPairPriceChart) {
		graphPanel.add(pCurrencyPairPriceChart, BorderLayout.CENTER);
	}
	
	public JTable getValueTagPricesTable() {
		return valueTagPricesTable;
	}
}
