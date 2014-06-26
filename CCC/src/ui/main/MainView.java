package ui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ui.ColorScheme;
import ui.internationalization.Strings;

public class MainView extends JFrame {

	/**
	 * @author Diego Etter
	 * @version 1.0
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	// Main Panels
	private JPanel JPanel_Main;
	private JPanel JPanel_ExchangeList;
	private JPanel JPanel_OptionPanel;
	private JPanel JPanel_CurrencyInfoPanel;

	private JPanel JPanel_CurrencyPairChoosePanel;
	private JPanel JPanel_CurrencyPairControllPanel;

	private JButton JButton_AddCurrencyPair;
	private JButton JButton_HelpMenue;
	private JButton JButton_Preferences;
	private JButton JButton_CurrencyPairValue;
	private JButton JButton_CurrencyPairRule;
	private JButton JButton_CurrencyPairRemove;

	private JScrollPane JScrollPane_CurrencyPair;
	private JButton JButton_Indicator;
	private JLabel cccLogoLabel;



	// init JFrame
	public MainView() {
		URL logoGif = MainView.class.getResource("/ui/images/logo.gif");
		if(logoGif == null){
			System.err.println("Logo missing in MainView");
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(logoGif));
		setTitle("Coin Control Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setMinimumSize(new Dimension(900, 700));
		
		initLogo();
		initExchangeList();
		initOptionPanel();
		initCurrencyPairControlPanel();
		initCurrencyInfoPanel();
		initMainView();
		setContentPane(JPanel_Main);
		setVisible(true);
	}

	private void initLogo() {
		BufferedImage cccLogoPNG = null;
		
		URL cccpng = MainView.class.getResource("/ui/images/ccc.png");
		if(cccpng == null){
			System.err.println("CCC.png missing in MainView");
		}
		
		try {
			cccLogoPNG = ImageIO.read(cccpng);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cccLogoLabel = new JLabel(new ImageIcon(cccLogoPNG));
	}

	// init UI
	private void initMainView() {
		JPanel_Main = new JPanel();
		JPanel_Main.setLayout(new BorderLayout());
		JPanel_Main.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel_Main.add(JPanel_ExchangeList, BorderLayout.WEST);
		JPanel_Main.add(JPanel_CurrencyInfoPanel, BorderLayout.CENTER);
		JPanel_Main.add(JPanel_OptionPanel, BorderLayout.NORTH);

	}

	// init Exchangechoose-view
	private void initExchangeList() {
		JPanel_ExchangeList = new JPanel();
		JPanel_ExchangeList.setLayout(new BorderLayout());
		JPanel_CurrencyPairChoosePanel = new JPanel();
		BoxLayout exchangeListLayout = new BoxLayout(
				JPanel_CurrencyPairChoosePanel, BoxLayout.PAGE_AXIS);
		JPanel_CurrencyPairChoosePanel.setLayout(exchangeListLayout);
		JScrollPane_CurrencyPair = new JScrollPane(
				JPanel_CurrencyPairChoosePanel);
		JScrollPane_CurrencyPair.setPreferredSize(new Dimension(150, 0));
		JPanel_ExchangeList.add(JScrollPane_CurrencyPair, BorderLayout.CENTER);
		JButton_AddCurrencyPair = new JButton("+");
		JPanel_ExchangeList.add(JButton_AddCurrencyPair, BorderLayout.SOUTH);
	}
	

	private void initOptionPanel() {
		// Layout
		GridBagLayout OptionPanelLayout = new GridBagLayout();
		OptionPanelLayout.columnWidths = new int[] { 30, 30, 30, 0, 0 };
		OptionPanelLayout.columnWeights = new double[] { Double.MIN_VALUE,
				Double.MIN_VALUE, Double.MIN_VALUE, 1.0, 0.0 };
		OptionPanelLayout.rowHeights = new int[] { 30 };
		OptionPanelLayout.rowWeights = new double[] { Double.MIN_VALUE };
		// Components
		JPanel_OptionPanel = new JPanel();
		JPanel_OptionPanel.setLayout(OptionPanelLayout);

		GridBagConstraints HelpMenueButton = new GridBagConstraints();
		HelpMenueButton.insets = new Insets(0, 0, 0, 5);
		HelpMenueButton.fill = GridBagConstraints.BOTH;
		HelpMenueButton.gridx = 0;
		HelpMenueButton.gridy = 0;
		JButton_HelpMenue = new JButton("?");
		JButton_HelpMenue.setIcon(new ImageIcon());
		JPanel_OptionPanel.add(JButton_HelpMenue, HelpMenueButton);

		GridBagConstraints PreferencesButton = new GridBagConstraints();
		PreferencesButton.insets = new Insets(0, 0, 0, 5);
		PreferencesButton.fill = GridBagConstraints.BOTH;
		PreferencesButton.gridx = 1;
		PreferencesButton.gridy = 0;
		JButton_Preferences = new JButton(
				Strings.getString("MainView.settings")); //$NON-NLS-1$
		JButton_Preferences.setIcon(new ImageIcon());
		JPanel_OptionPanel.add(JButton_Preferences, PreferencesButton);
		
		JButton_Indicator = new JButton(Strings.getString("MainView.btnNewButton.text"));
		GridBagConstraints gbc_JButton_Indicator = new GridBagConstraints();
		gbc_JButton_Indicator.gridx = 4;
		gbc_JButton_Indicator.gridy = 0;
		JPanel_OptionPanel.add(JButton_Indicator, gbc_JButton_Indicator);
	}

	private void initCurrencyPairControlPanel() {
		GridBagLayout currencyPairControlLayout = new GridBagLayout();
		currencyPairControlLayout.columnWidths = new int[] { 30, 30, 30, 30 };
		currencyPairControlLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
		currencyPairControlLayout.rowHeights = new int[] { 30 };
		currencyPairControlLayout.rowWeights = new double[] { 0.0 };
		JPanel_CurrencyPairControllPanel = new JPanel();
		JPanel_CurrencyPairControllPanel.setLayout(currencyPairControlLayout);

		GridBagConstraints currencyPairValueButton = new GridBagConstraints();
		currencyPairValueButton.fill = GridBagConstraints.BOTH;
		currencyPairValueButton.gridx = 0;
		currencyPairValueButton.gridy = 0;
		JButton_CurrencyPairValue = new JButton();
		JButton_CurrencyPairValue.setText(Strings
				.getString("MainView.tradingPrices")); //$NON-NLS-1$
		JPanel_CurrencyPairControllPanel.add(JButton_CurrencyPairValue,
				currencyPairValueButton);

		GridBagConstraints CurrencyPairRuleButton = new GridBagConstraints();
		CurrencyPairRuleButton.fill = GridBagConstraints.BOTH;
		CurrencyPairRuleButton.gridx = 1;
		CurrencyPairRuleButton.gridy = 0;
		JButton_CurrencyPairRule = new JButton();
		JButton_CurrencyPairRule.setText(Strings.getString("MainView.rules")); //$NON-NLS-1$
		JPanel_CurrencyPairControllPanel.add(JButton_CurrencyPairRule,
				CurrencyPairRuleButton);

		GridBagConstraints CurrencyPairRemoveButton = new GridBagConstraints();
		CurrencyPairRemoveButton.fill = GridBagConstraints.BOTH;
		CurrencyPairRemoveButton.gridx = 3;
		CurrencyPairRemoveButton.gridy = 0;
		JButton_CurrencyPairRemove = new JButton();
		JButton_CurrencyPairRemove.setText(Strings
				.getString("MainView.unsubscribeCurrencyPair")); //$NON-NLS-1$
		JPanel_CurrencyPairControllPanel.add(JButton_CurrencyPairRemove,
				CurrencyPairRemoveButton);
	}

	private void initCurrencyInfoPanel() {
		JPanel_CurrencyInfoPanel = new JPanel();
		JPanel_CurrencyInfoPanel.setLayout(new BorderLayout());
		JPanel_CurrencyInfoPanel.add(cccLogoLabel);
	}

	public JButton addExchangeButton(String title, ActionListener AL) {
		JButton button = new JButton(title);
		button.setHorizontalTextPosition(SwingConstants.LEFT);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		button.addActionListener(AL);
		JPanel_CurrencyPairChoosePanel.add(button);
		JPanel_CurrencyPairChoosePanel.revalidate();
		return button;
	}
	
	public void removeCurrencyPair(JButton toDelButton){
		JPanel_CurrencyPairChoosePanel.remove(toDelButton);
		JPanel_CurrencyPairChoosePanel.revalidate();
	}


	public void setCurrPairView(JPanel newView) {
		JPanel_CurrencyInfoPanel.removeAll();
		JPanel_CurrencyInfoPanel.add(JPanel_CurrencyPairControllPanel,
				BorderLayout.NORTH);
		JPanel_CurrencyInfoPanel.add(newView, BorderLayout.CENTER);
		JPanel_CurrencyInfoPanel.updateUI();
	}
	
	public void setView(JPanel newView){
		JPanel_CurrencyInfoPanel.removeAll();
		JPanel_CurrencyInfoPanel.add(newView, BorderLayout.CENTER);
		JPanel_CurrencyInfoPanel.updateUI();
	}
	
	public void setEmptyView(){
		JPanel_CurrencyInfoPanel.removeAll();
		JPanel_CurrencyInfoPanel.add(cccLogoLabel);
		JPanel_CurrencyPairChoosePanel.updateUI();
		JPanel_CurrencyInfoPanel.updateUI();
	}

	public void setEditView(JPanel newView) {
		JPanel_CurrencyInfoPanel.removeAll();
		JPanel_CurrencyInfoPanel.add(newView, BorderLayout.CENTER);
		JPanel_CurrencyInfoPanel.updateUI();
	}

	public void setButtonFunction(JButton button, ActionListener AL) {
		button.addActionListener(AL);
	}

	public JPanel getJPanel_CurrencyInfoPanel() {
		return JPanel_CurrencyInfoPanel;
	}

	public JButton getJButton_AddCurrencyPair() {
		return JButton_AddCurrencyPair;
	}

	public JButton getJButton_HelpMenue() {
		return JButton_HelpMenue;
	}

	public JButton getJButton_Preferences() {
		return JButton_Preferences;
	}

	public JButton getJButton_CurrencyPairValue() {
		return JButton_CurrencyPairValue;
	}

	public JButton getJButton_CurrencyPairRule() {
		return JButton_CurrencyPairRule;
	}
	public JButton getJButton_Indicator() {
		return JButton_Indicator;
	}
	
	public JButton getJButton_CurrencyPairRemove() {
		return JButton_CurrencyPairRemove;
	}
	
	public void highlightRuleButton(){
		JButton_CurrencyPairRule.setBackground(ColorScheme.GOLD);
	}
	
	public void resetHightRuleButton(){
		JButton_CurrencyPairRule.setBackground(null);
	}
	
	public void highlightValueButton(){
		JButton_CurrencyPairValue.setBackground(ColorScheme.GOLD);
	}
	
	public void resetHightValueButton(){
		JButton_CurrencyPairValue.setBackground(null);
	}

}
