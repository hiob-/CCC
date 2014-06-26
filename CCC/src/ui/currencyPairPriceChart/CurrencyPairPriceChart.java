package ui.currencyPairPriceChart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.ObserverObjectWrapper;
import model.currency.CurrencyPairHistory;
import model.currency.CurrencyPairHistoryPoint;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;

public class CurrencyPairPriceChart extends JPanel implements ChangeListener, Observer {

	private static final long serialVersionUID = 1L;
	private String chartTitle = "";
	private JFreeChart chart;
	private final ChartPanel chartPanel;
	private final JScrollBar scrollBar;
	private final JPanel scrollPanel;
	private final JCheckBox chkbxAutoScroll;
	private final int maxValuePointsPerPage = 20;
	private final CurrencyPairHistory cpHistory;
	private boolean autoScroll = true;

	// Price
	private final DefaultCategoryDataset priceDataSet = new DefaultCategoryDataset();
	private final SlidingCategoryDataset slidingPriceDataSet;

	// Volume
	private final DefaultCategoryDataset volumeDataSet = new DefaultCategoryDataset();
	private final SlidingCategoryDataset slidingVolumeDataSet;

	public CurrencyPairPriceChart(final String title, final CurrencyPairHistory pCurrencyPairHistory) {
		super(new BorderLayout());
		slidingPriceDataSet = new SlidingCategoryDataset(priceDataSet, 0,
				maxValuePointsPerPage);
		slidingVolumeDataSet = new SlidingCategoryDataset(volumeDataSet, 0,
				maxValuePointsPerPage);
		cpHistory = pCurrencyPairHistory;
		cpHistory.addObserver(this);
		
		final JFreeChart chart = createChart();
		chartPanel = new ChartPanel(chart);
		chartPanel.setPopupMenu(null);
		chartTitle = title;
		add(chartPanel);
		
		//ScrollBar/Panel
		
		scrollBar = new JScrollBar(SwingConstants.HORIZONTAL, 0, 1, 0, 1);
		scrollBar.getModel().addChangeListener(this);
		
		chkbxAutoScroll = new JCheckBox("Auto Scroll");
		setAutoScroll(true);
		
		ItemListener chkbxAutoScrollIL = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				autoScroll = chkbxAutoScroll.isSelected();
			}
		};
		chkbxAutoScroll.addItemListener(chkbxAutoScrollIL);
		
		scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.add(scrollBar, BorderLayout.CENTER);
		scrollPanel.add(chkbxAutoScroll, BorderLayout.EAST);
		scrollPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		scrollPanel.setBackground(Color.white);
		add(scrollPanel, BorderLayout.SOUTH);
		
		//Add Existing points
		if(!cpHistory.getHistoryPointList().isEmpty()){
			for(CurrencyPairHistoryPoint cphp : cpHistory.getHistoryPointList()){
				addCPHistoryPoints(cphp);
			}
		}
	}
	
	public void addCPHistoryPoints(CurrencyPairHistoryPoint cphp){
		if(cphp != null){
			addPriceValuePoint(cphp.getPrice(), "Price", new SimpleDateFormat(
					"H:mm").format(cphp.getDate()));
			addVolumeValuePoint(cphp.getVolume(), "Volume",
					new SimpleDateFormat("H:mm").format(cphp.getDate()));
			int totalColumnCount = (int) Math.ceil((priceDataSet.getColumnCount()+volumeDataSet.getColumnCount())/2);
			final int newScrollMaximum = (int) Math.ceil(totalColumnCount / maxValuePointsPerPage);
			scrollBar.setMaximum(newScrollMaximum);
			if(autoScroll){
				scrollBar.setValue(scrollBar.getMaximum());
			}
			//Following needed so the RangeAxis is displayed correctly in the beginning
			if(newScrollMaximum == 0){
				changeChartDataIndex(0);
			}
		}
	}

	private void addPriceValuePoint(final double pValue, final String pSeries,
			final String pType) {
		priceDataSet.addValue(pValue, pSeries, pType);
		chart.fireChartChanged();
	}

	private void addVolumeValuePoint(final double pValue, final String pSeries,
			final String pType) {
		volumeDataSet.addValue(pValue, pSeries, pType);
		chart.fireChartChanged();
	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart() {

		final CategoryPlot plot = new CategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		// ==== Price ====
		final CategoryItemRenderer renderer = new LineAndShapeRenderer();
		renderer.setBaseToolTipGenerator(new CategoryToolTipGenerator() {
			@Override
			public String generateToolTip(final CategoryDataset arg0,
					final int arg1, final int arg2) {
				final String toolTip = "<html>" + arg0.getColumnKey(arg2)
						+ "<br />Price: " + arg0.getValue(arg1, arg2)
						+ "<br />Volume: " + volumeDataSet.getValue(arg1, arg2)
						+ "</html>";
				return toolTip;
			}
		});
		renderer.setSeriesPaint(0, Color.BLUE);
		plot.setDataset(0, slidingPriceDataSet); 
		plot.setRenderer(0, renderer);
		final CategoryAxis domainAxis = new CategoryAxis("Time");
		plot.setDomainAxis(domainAxis);

		final NumberAxis priceNumberAxis = new NumberAxis("Value");
		priceNumberAxis.setStandardTickUnits(NumberAxis
				.createStandardTickUnits());
		priceNumberAxis.setAutoRange(true);
		priceNumberAxis.setAutoRangeIncludesZero(false);
		plot.setRangeAxis(0, priceNumberAxis);
		plot.mapDatasetToRangeAxis(0, 0);

		// ==== Volume ====

		// create the first renderer...
		final CategoryItemRenderer renderer2 = new BarRenderer();
		((BarRenderer) renderer2).setBarPainter(new StandardBarPainter());
		((BarRenderer) renderer2).setMaximumBarWidth(.01);
		((BarRenderer) renderer2).setShadowVisible(false);
		((BarRenderer) renderer2).setSeriesPaint(0, Color.LIGHT_GRAY);
		renderer2.setBaseToolTipGenerator(new CategoryToolTipGenerator() {
			@Override
			public String generateToolTip(final CategoryDataset arg0,
					final int arg1, final int arg2) {
				final String toolTip = "<html>" + arg0.getColumnKey(arg2)
						+ "<br />Price: " + priceDataSet.getValue(arg1, arg2)
						+ "<br />Volume: " + arg0.getValue(arg1, arg2)
						+ "</html>";
				return toolTip;
			}
		});
		// renderer.setLabelGenerator(generator);
		// renderer.setItemLabelsVisible(true);

		plot.setDataset(1, slidingVolumeDataSet); 
		plot.setRenderer(1, renderer2);

		final NumberAxis volumeNumberAxis = new NumberAxis("Volume");
		volumeNumberAxis.setStandardTickUnits(NumberAxis
				.createStandardTickUnits());
		volumeNumberAxis.setAutoRange(true);
		volumeNumberAxis.setAutoRangeIncludesZero(false);

		volumeNumberAxis.setUpperMargin(0.05);
		plot.setRangeAxis(1, volumeNumberAxis);

		plot.mapDatasetToRangeAxis(1, 1);

		// ==== GENERAL ====

		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);

		plot.getDomainAxis().setCategoryLabelPositions(
				CategoryLabelPositions.UP_90);

		chart = new JFreeChart(plot);
		chart.setTitle(chartTitle);
		return chart;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	public JPanel getScrollPanel() {
		return scrollPanel;
	}

	@Override
	public void stateChanged(final ChangeEvent arg0) {
		int scrollBarValue = scrollBar.getValue();
		
		if (priceDataSet.getColumnCount() > maxValuePointsPerPage
				&& volumeDataSet.getColumnCount() > maxValuePointsPerPage || autoScroll) {
			changeChartDataIndex(scrollBarValue);
		}
	}
	
	private void setAutoScroll(boolean pAutoScroll){
		autoScroll = pAutoScroll;
		chkbxAutoScroll.setSelected(pAutoScroll);
	}
	
	private void changeChartDataIndex(int pIndex){
		slidingPriceDataSet.setFirstCategoryIndex(pIndex);
		slidingVolumeDataSet.setFirstCategoryIndex(pIndex);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof ObserverObjectWrapper){
			ObserverObjectWrapper oow = (ObserverObjectWrapper) arg1;
			if(arg0 instanceof CurrencyPairHistory){
				CurrencyPairHistoryPoint newCPHP = (CurrencyPairHistoryPoint) oow.getObject();
				addCPHistoryPoints(newCPHP);
			}
		}
		
	}
}
