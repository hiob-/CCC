package ui.currencyPairEdit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;

import model.Exchange;
import model.currency.CCC_Currency;

import ui.ColorScheme;
import ui.JCustomTextField;
import ui.internationalization.Strings;

/**
 * View for CurrencyPairEdit
 * 
 * @author Luca T�nnler
 * @version 1.0
 * */
public class CurrencyPairEditView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JPanel dataPanel;
	private JPanel acceptPanel;
	
	private JList<Exchange> exchangeList;
	private JList<CCC_Currency> baseCurrencyList;
	private JList<CCC_Currency> quotedCurrencyList;
	
	private JLabel lblExchange;
	private JLabel lblBaseCurrency;
	private JLabel lblQuotedCurrency;
	
	private JCustomTextField txtExchange;
	private JCustomTextField txtBaseCurrency;
	private JCustomTextField txtQuotedCurrency;
	
	private JButton btnDeleteExchange;
	private JButton btnDeleteBaseCurrency;
	private JButton btnDeleteQuotedCurrency;
	private JButton btnCancel;
	private JButton btnSave;
	private JScrollPane exchangeScrollPane;
	private JScrollPane baseCurrencyScrollPane;
	private JScrollPane quotedCurrencyScrollPane;

	public CurrencyPairEditView(){

		setSize(700, 500);
		setMinimumSize(getSize());
		setLocation(100, 100);
		
		initDataPanel();
		initAcceptPanel();
		setLayout(new BorderLayout());
		add(dataPanel, BorderLayout.CENTER);
		add(acceptPanel, BorderLayout.SOUTH);
	}
	

	
	private void initAcceptPanel(){		
		

		GridBagLayout acceptPanelLayout = new GridBagLayout();
		acceptPanelLayout.columnWidths = new int[]{0,70,70};
		acceptPanelLayout.columnWeights = new double[]{1.0,0.0,0.0};
		acceptPanelLayout.rowHeights = new int[]{30};
		acceptPanelLayout.rowWeights = new double[]{0.0};
		acceptPanel = new JPanel();
		acceptPanel.setLayout(acceptPanelLayout);
		
		GridBagConstraints btnCancelConstraints = new GridBagConstraints();
		btnCancelConstraints.insets = new Insets(0, 0, 20, 0);
		btnCancelConstraints.fill = GridBagConstraints.BOTH;
		btnCancelConstraints.gridx = 1;
		btnCancelConstraints.gridy = 0;
		btnCancel = new JButton(Strings.getString("CurrencyPairEditView.cancel")); //$NON-NLS-1$
		acceptPanel.add(btnCancel, btnCancelConstraints);
		
		GridBagConstraints btnSaveConstraints = new GridBagConstraints();
		btnSaveConstraints.insets = new Insets(0, 0, 20, 0);
		btnSaveConstraints.fill = GridBagConstraints.BOTH;
		btnSaveConstraints.gridy = 0;
		btnSaveConstraints.gridx = 2;
		btnSave = new JButton(Strings.getString("CurrencyPairEditView.subscribe")); //$NON-NLS-1$
		acceptPanel.add(btnSave, btnSaveConstraints);	
	}
	
	private void initDataPanel(){
		GridBagLayout dataPanelLayout = new GridBagLayout();
		dataPanelLayout.columnWidths = new int[]{30,170,50,170,50,170,50,0};
		dataPanelLayout.columnWeights = new double []{0.0,0.0,0.0,1.0,0.0,1.0,0.0,1.0};
		dataPanelLayout.rowHeights = new int[]{30,30,300,30,0};
		dataPanelLayout.rowWeights = new double[]{1.0,0.0,1.0,0.0,1.0};
		dataPanel = new JPanel();
		dataPanel.setLayout(dataPanelLayout);
		
		//ChoosenFieldText
		GridBagConstraints lblExchangeConstraints = new GridBagConstraints();
		lblExchangeConstraints.insets = new Insets(20, 0, 5, 5);
		lblExchangeConstraints.anchor = GridBagConstraints.WEST;
		lblExchangeConstraints.gridwidth = 2;
		lblExchangeConstraints.gridx = 1;
		lblExchangeConstraints.gridy = 0;
		lblExchange = new JLabel(Strings.getString("CurrencyPairEditView.exchange")); //$NON-NLS-1$
		dataPanel.add(lblExchange, lblExchangeConstraints);
		
		GridBagConstraints lblBaseCurrencyConstraints = new GridBagConstraints();
		lblBaseCurrencyConstraints.insets = new Insets(20, 0, 5, 5);
		lblBaseCurrencyConstraints.anchor = GridBagConstraints.WEST;
		lblBaseCurrencyConstraints.gridwidth = 2;
		lblBaseCurrencyConstraints.gridy = 0;
		lblBaseCurrencyConstraints.gridx = 3;
		lblBaseCurrency = new JLabel(Strings.getString("CurrencyPairEditView.baseCurrency")); //$NON-NLS-1$
		dataPanel.add(lblBaseCurrency, lblBaseCurrencyConstraints);
		
		GridBagConstraints lblQuotedCurrencyConstraints = new GridBagConstraints();
		lblQuotedCurrencyConstraints.insets = new Insets(20, 0, 5, 5);
		lblQuotedCurrencyConstraints.anchor = GridBagConstraints.WEST;
		lblQuotedCurrencyConstraints.gridwidth = 2;
		lblQuotedCurrencyConstraints.gridy = 0;
		lblQuotedCurrencyConstraints.gridx = 5;
		lblQuotedCurrency = new JLabel(Strings.getString("CurrencyPairEditView.quotedCurrency")); //$NON-NLS-1$
		dataPanel.add(lblQuotedCurrency, lblQuotedCurrencyConstraints);
		
		//ChoosenField
		GridBagConstraints txtExchangeConstraints = new GridBagConstraints();
		txtExchangeConstraints.insets = new Insets(0, 0, 10, 5);
		txtExchangeConstraints.anchor = GridBagConstraints.WEST;
		txtExchangeConstraints.fill = GridBagConstraints.BOTH;
		txtExchangeConstraints.gridwidth = 1;
		txtExchangeConstraints.gridy = 1;
		txtExchangeConstraints.gridx = 1;
		txtExchange = new JCustomTextField(Strings.getString("CurrencyPairEditView.empty")); //$NON-NLS-1$
		txtExchange.setBackground(Color.white);
		txtExchange.setOpaque(true);
		txtExchange.setBorder(new LineBorder(new Color(0, 0, 0)));
		dataPanel.add(txtExchange, txtExchangeConstraints);
		
		GridBagConstraints txtBaseCurrencyConstraints = new GridBagConstraints();
		txtBaseCurrencyConstraints.insets = new Insets(0, 0, 10, 5);
		txtBaseCurrencyConstraints.anchor = GridBagConstraints.WEST;
		txtBaseCurrencyConstraints.fill = GridBagConstraints.BOTH;
		txtBaseCurrencyConstraints.gridwidth = 1;
		txtBaseCurrencyConstraints.gridy = 1;
		txtBaseCurrencyConstraints.gridx = 3;
		txtBaseCurrency = new JCustomTextField(Strings.getString("CurrencyPairEditView.empty2")); //$NON-NLS-1$
		txtBaseCurrency.setBackground(Color.white);
		txtBaseCurrency.setOpaque(true);
		txtBaseCurrency.setBorder(new LineBorder(new Color(0, 0, 0)));
		dataPanel.add(txtBaseCurrency, txtBaseCurrencyConstraints);
		
		GridBagConstraints txtQuotedCurrencyConstraints = new GridBagConstraints();
		txtQuotedCurrencyConstraints.insets = new Insets(0, 0, 10, 5);
		txtQuotedCurrencyConstraints.anchor = GridBagConstraints.WEST;
		txtQuotedCurrencyConstraints.fill = GridBagConstraints.BOTH;
		txtQuotedCurrencyConstraints.gridwidth = 1;
		txtQuotedCurrencyConstraints.gridy = 1;
		txtQuotedCurrencyConstraints.gridx = 5;
		txtQuotedCurrency = new JCustomTextField(Strings.getString("CurrencyPairEditView.empty3")); //$NON-NLS-1$
		txtQuotedCurrency.setBackground(Color.white);
		txtQuotedCurrency.setOpaque(true);
		txtQuotedCurrency.setBorder(new LineBorder(new Color(0, 0, 0)));
		dataPanel.add(txtQuotedCurrency, txtQuotedCurrencyConstraints);
		
		//Scroll Pane & Lists
		exchangeList = new JList<Exchange>();
		exchangeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		exchangeList.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		baseCurrencyList = new JList<CCC_Currency>();
		baseCurrencyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		baseCurrencyList.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		quotedCurrencyList = new JList<CCC_Currency>();
		quotedCurrencyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		quotedCurrencyList.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		exchangeScrollPane = new JScrollPane();
		GridBagConstraints gbc_exchangeScrollPane = new GridBagConstraints();
		gbc_exchangeScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_exchangeScrollPane.fill = GridBagConstraints.BOTH;
		gbc_exchangeScrollPane.gridx = 1;
		gbc_exchangeScrollPane.gridy = 2;
		exchangeScrollPane.setViewportView(exchangeList);
		dataPanel.add(exchangeScrollPane, gbc_exchangeScrollPane);
		
		baseCurrencyScrollPane = new JScrollPane();
		GridBagConstraints gbc_baseCurrencyScrollPane = new GridBagConstraints();
		gbc_baseCurrencyScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_baseCurrencyScrollPane.fill = GridBagConstraints.BOTH;
		gbc_baseCurrencyScrollPane.gridx = 3;
		gbc_baseCurrencyScrollPane.gridy = 2;
		baseCurrencyScrollPane.setViewportView(baseCurrencyList);
		dataPanel.add(baseCurrencyScrollPane, gbc_baseCurrencyScrollPane);
		
		quotedCurrencyScrollPane = new JScrollPane();
		GridBagConstraints gbc_quotedCurrencyScrollPane = new GridBagConstraints();
		gbc_quotedCurrencyScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_quotedCurrencyScrollPane.fill = GridBagConstraints.BOTH;
		gbc_quotedCurrencyScrollPane.gridx = 5;
		gbc_quotedCurrencyScrollPane.gridy = 2;
		quotedCurrencyScrollPane.setViewportView(quotedCurrencyList);
		dataPanel.add(quotedCurrencyScrollPane, gbc_quotedCurrencyScrollPane);
	}
	
	/* exchangeList */
	public void setExchangeListModel(ListModel<Exchange> pListModel){
		this.exchangeList.setModel(pListModel);
	}
	
	public Exchange getSelectedExchange(){
		return exchangeList.getSelectedValue();
	}
	
	public void setSelectedExchange(Exchange pExchange){
		exchangeList.setSelectedValue(pExchange, true);
	}
	
	public void addExchangeListLSL(ListSelectionListener pListSelectionListener){
		this.exchangeList.addListSelectionListener(pListSelectionListener);
	}
	
	public void updateExchangeList(){
		exchangeList.revalidate();
	}
	
	public void resetExchangeListSelection(){
		exchangeList.clearSelection();
	}
	
	/* baseCurrencyList */
	public void setBaseCurrencyListModel(ListModel<CCC_Currency> pListModel){
		this.baseCurrencyList.setModel(pListModel);
	}
	
	public CCC_Currency getSelectedBaseCurrency(){
		return this.baseCurrencyList.getSelectedValue();
	}
	
	public void setSelectedBaseCurrency(CCC_Currency pBaseCurrency){
		baseCurrencyList.setSelectedValue(pBaseCurrency, true);
	}
	
	public void addBaseCurrencyListLSL(ListSelectionListener pListSelectionListener){
		this.baseCurrencyList.addListSelectionListener(pListSelectionListener);
	}
	
	public void setBaseCurrencyEnabled(boolean pEnabled){
		this.baseCurrencyList.setEnabled(pEnabled);
		this.txtBaseCurrency.setEnabled(pEnabled);
	}
	
	public void updateBaseCurrencyList(){
		baseCurrencyList.revalidate();
	}
	
	public void resetBaseCurrencyListSelection(){
		baseCurrencyList.clearSelection();
	}
	
	/* quotedCurrencyList */
	public void setQuotedCurrencyListModel(ListModel<CCC_Currency> pListModel){
		this.quotedCurrencyList.setModel(pListModel);
	}
	
	public CCC_Currency getSelectedQuotedCurrency(){
		return this.quotedCurrencyList.getSelectedValue();
	}
	
	public void setSelectedQuotedCurrency(CCC_Currency pQuotedCurrency){
		quotedCurrencyList.setSelectedValue(pQuotedCurrency, true);
	}
	
	public void addQuotedCurrencyListLSL(ListSelectionListener pListSelectionListener){
		this.quotedCurrencyList.addListSelectionListener(pListSelectionListener);
	}
	
	public void setQuotedCurrencyEnabled(boolean pEnabled){
		this.quotedCurrencyList.setEnabled(pEnabled);
		this.txtQuotedCurrency.setEnabled(pEnabled);
	}
	
	public void updateQuotedCurrencyList(){
		quotedCurrencyList.revalidate();
	}
	
	public void resetQuotedCurrencyListSelection(){
		quotedCurrencyList.clearSelection();
	}
	
	/* txtExchange */
	public String getTxtExchangeText(){
		return this.txtExchange.getText();
	}
	
	public void setTxtExchangeText(String pText){
		this.txtExchange.setText(pText);
	}
	
	public void addTxtExchangeKeyListener(KeyListener pKeyListener){
		this.txtExchange.addKeyListener(pKeyListener);
	}
	
	public void addTxtExchangeDocumentListener(DocumentListener pDocumentListener){
		this.txtExchange.addDocumentListener(pDocumentListener);
	}
	
	public void setTxtExchangeStyleValid(){
		this.txtExchange.setBackground(ColorScheme.ACTIVE_GREEN);
	}
	
	public void setTxtExchangeStyleNormal(){
		this.txtExchange.setBackground((new JTextField()).getBackground());
	}
	
	/* txtBaseCurrency */
	public String getBaseCurrencyText(){
		return this.txtBaseCurrency.getText();
	}
	
	public void setBaseCurrencyText(String pText){
		this.txtBaseCurrency.setText(pText);
	}
	
	public void addTxtBaseCurrencyKeyListener(KeyListener pKeyListener){
		this.txtBaseCurrency.addKeyListener(pKeyListener);
	}
	
	public void addTxtBaseCurrencyDocumentListener(DocumentListener pDocumentListener){
		this.txtBaseCurrency.addDocumentListener(pDocumentListener);
	}
	
	public void setTxtBaseCurrencyStyleValid(){
		this.txtBaseCurrency.setBackground(ColorScheme.ACTIVE_GREEN);
	}
	
	public void setTxtBaseCurrencyStyleNormal(){
		this.txtBaseCurrency.setBackground((new JTextField()).getBackground());
	}
	
	
	/* txtQuotedCurrency */
	public String getQuotedCurrencyText(){
		return this.txtQuotedCurrency.getText();
	}
	
	public void setQuotedCurrencyText(String pText){
		this.txtQuotedCurrency.setText(pText);
	}
	
	public void addTxtQuotedCurrencyKeyListener(KeyListener pKeyListener){
		this.txtQuotedCurrency.addKeyListener(pKeyListener);
	}
	
	public void addTxtQuotedCurrencyDocumentListener(DocumentListener pDocumentListener){
		this.txtQuotedCurrency.addDocumentListener(pDocumentListener);
	}
	
	public void setTxtQuotedCurrencyStyleValid(){
		this.txtQuotedCurrency.setBackground(ColorScheme.ACTIVE_GREEN);
	}
	
	public void setTxtQuotedCurrencyStyleNormal(){
		this.txtQuotedCurrency.setBackground((new JTextField()).getBackground());
	}
	
	/* btnDeleteExchange */
	public void addBtnDeleteExchangeAL(ActionListener pActionListener){
		btnDeleteExchange.addActionListener(pActionListener);
	}
	
	/* btnDeleteQuotedCurrency */
	public void addBtnDeleteQuotedCurrencyAL(ActionListener pActionListener){
		btnDeleteQuotedCurrency.addActionListener(pActionListener);
	}
	
	/* btnDeleteBaseCurrency */
	public void addBtnDeleteBaseCurrencyAL(ActionListener pActionListener){
		btnDeleteBaseCurrency.addActionListener(pActionListener);
	}
	
	/* btnCancel */
	public void addBtnCancelAL(ActionListener pActionListener){
		btnCancel.addActionListener(pActionListener);
	}
	
	/* btnSave */
	public void addBtnSaveAL(ActionListener pActionListener){
		btnSave.addActionListener(pActionListener);
	}
	
	public void setBtnSaveEnabled(boolean pEnabled){
		btnSave.setEnabled(pEnabled);
	}
}
