package ui.currencyPairEdit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import logic.exchange.ExchangeController;
import logic.modelController.CurrencyController;
import logic.modelController.CurrencyPairController;
import model.Exchange;
import model.currency.CCC_Currency;
import model.currency.CurrencyPair;
import ui.internationalization.Strings;
import ui.main.MainController;

/**
 * Controler for CurrencyPairEditView (UI)
 * 
 * @author Luca T�nnler
 * @version 1.0
 * */
public class CurrencyPairEditController {
	private final CurrencyPairEditView viewObj;
	private ExchangeListModel exchangeListModel;
	private CurrencyListModel baseCurrencyListModel;
	private CurrencyListModel quotedCurrencyListModel;

	private ArrayList<Exchange> exchangeList;
	private ArrayList<CCC_Currency> baseCurrencyList;
	private ArrayList<CCC_Currency> quotedCurrencyList;

	private Exchange selectedExchange;
	private CCC_Currency selectedQuotedCurrency;
	private CCC_Currency selectedBaseCurrency;

	public CurrencyPairEditController() {
		viewObj = new CurrencyPairEditView();
		exchangeList = new ArrayList<Exchange>();
		baseCurrencyList = new ArrayList<CCC_Currency>();
		quotedCurrencyList = new ArrayList<CCC_Currency>();

		initData();
		initComponents();
		initListeners();
	}

	/** Initializes Components */
	void initComponents() {
		filterExchangeList("");
		filterBaseCurrencyList("");
		filterQuotedCurrencyList("");

		exchangeListModel = new ExchangeListModel(exchangeList);
		baseCurrencyListModel = new CurrencyListModel();
		quotedCurrencyListModel = new CurrencyListModel();

		viewObj.setExchangeListModel(exchangeListModel);
		viewObj.setBaseCurrencyListModel(baseCurrencyListModel);
		viewObj.setQuotedCurrencyListModel(quotedCurrencyListModel);

		viewObj.setBaseCurrencyEnabled(false);
		viewObj.setQuotedCurrencyEnabled(false);
		viewObj.setBtnSaveEnabled(false);

	}

	/** Creates & assigns all Listeners for specific view Elements */
	private void initListeners() {
		// Exchange DocumentListener
		final DocumentListener exchangeDocListener = new DocumentListener() {
			@Override
			public void changedUpdate(final DocumentEvent e) {
				changeOccured();
			}

			@Override
			public void removeUpdate(final DocumentEvent e) {
				changeOccured();
			}

			@Override
			public void insertUpdate(final DocumentEvent e) {
				changeOccured();
			}

			public void changeOccured() {
				// Check if entered Text matches an existing & valid Exchange
				final Exchange validExchange = getExchange(viewObj
						.getTxtExchangeText());
				if (validExchange != null) {
					viewObj.setTxtExchangeStyleValid();
					viewObj.setSelectedExchange(validExchange);
				} else {
					viewObj.setTxtExchangeStyleNormal();
					viewObj.resetExchangeListSelection();
					filterExchangeList(viewObj.getTxtExchangeText());
					exchangeListModel.overrideList(exchangeList);
					viewObj.updateExchangeList();
				}
			}
		};
		viewObj.addTxtExchangeDocumentListener(exchangeDocListener);

		// Exchange KeyListener
		final KeyListener exchangeKeyListener = new KeyListener() {
			@Override
			public void keyTyped(final KeyEvent arg0) {
			}

			@Override
			public void keyReleased(final KeyEvent arg0) {
				filterExchangeList(viewObj.getTxtExchangeText());
				exchangeListModel.overrideList(exchangeList);
				viewObj.updateExchangeList();
			}

			@Override
			public void keyPressed(final KeyEvent arg0) {
			}
		};
		viewObj.addTxtExchangeKeyListener(exchangeKeyListener);

		// BaseCurrency DocumentListener
		final DocumentListener baseCurrencyDocListener = new DocumentListener() {
			@Override
			public void changedUpdate(final DocumentEvent e) {
				changeOccured();
			}

			@Override
			public void removeUpdate(final DocumentEvent e) {
				changeOccured();
			}

			@Override
			public void insertUpdate(final DocumentEvent e) {
				changeOccured();
			}

			public void changeOccured() {
				// Check if entered Text matches an existing & valid
				// BaseCurrency
				final CCC_Currency validBaseCurrency = getBaseCurrency(viewObj
						.getBaseCurrencyText());
				if (validBaseCurrency != null) {
					viewObj.setTxtBaseCurrencyStyleValid();
					viewObj.setSelectedBaseCurrency(validBaseCurrency);
				} else {
					viewObj.setTxtBaseCurrencyStyleNormal();
					viewObj.resetBaseCurrencyListSelection();
					filterBaseCurrencyList(viewObj.getBaseCurrencyText());
					baseCurrencyListModel.overrideList(baseCurrencyList);
					viewObj.updateBaseCurrencyList();
				}
			}
		};
		viewObj.addTxtBaseCurrencyDocumentListener(baseCurrencyDocListener);

		// BaseCurrency KeyListener
		final KeyListener baseCurrencyKeyListener = new KeyListener() {
			@Override
			public void keyTyped(final KeyEvent arg0) {
			}

			@Override
			public void keyReleased(final KeyEvent arg0) {
				filterBaseCurrencyList(viewObj.getBaseCurrencyText());
				baseCurrencyListModel.overrideList(baseCurrencyList);
				viewObj.updateBaseCurrencyList();
			}

			@Override
			public void keyPressed(final KeyEvent arg0) {
			}
		};
		viewObj.addTxtBaseCurrencyKeyListener(baseCurrencyKeyListener);

		// QuotedCurrency DocumentListener
		final DocumentListener quotedCurrencyDocListener = new DocumentListener() {
			@Override
			public void changedUpdate(final DocumentEvent e) {
				changeOccured();
			}

			@Override
			public void removeUpdate(final DocumentEvent e) {
				changeOccured();
			}

			@Override
			public void insertUpdate(final DocumentEvent e) {
				changeOccured();
			}

			public void changeOccured() {
				// Check if entered Text matches an existing & valid
				// QuotedCurrency
				final CCC_Currency validQuotedCurrency = getQuotedCurrency(viewObj
						.getQuotedCurrencyText());
				if (validQuotedCurrency != null) {
					viewObj.setTxtQuotedCurrencyStyleValid();
					viewObj.setSelectedQuotedCurrency(validQuotedCurrency);
				} else {
					viewObj.setTxtQuotedCurrencyStyleNormal();
					viewObj.resetQuotedCurrencyListSelection();
					filterQuotedCurrencyList(viewObj.getQuotedCurrencyText());
					quotedCurrencyListModel.overrideList(quotedCurrencyList);
					viewObj.updateQuotedCurrencyList();
				}
			}
		};
		viewObj.addTxtQuotedCurrencyDocumentListener(quotedCurrencyDocListener);

		// QuotedCurrency KeyListener
		final KeyListener quotedCurrencyKeyListener = new KeyListener() {
			@Override
			public void keyTyped(final KeyEvent arg0) {
			}

			@Override
			public void keyReleased(final KeyEvent arg0) {
				filterQuotedCurrencyList(viewObj.getQuotedCurrencyText());
				quotedCurrencyListModel.overrideList(quotedCurrencyList);
				viewObj.updateQuotedCurrencyList();
			}

			@Override
			public void keyPressed(final KeyEvent arg0) {
			}
		};
		viewObj.addTxtQuotedCurrencyKeyListener(quotedCurrencyKeyListener);

		// exchangeList selectionListener
		final ListSelectionListener exchangeListLSL = new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				selectedExchangeChanged();
			}
		};
		viewObj.addExchangeListLSL(exchangeListLSL);

		// baseCurrencyList selectionListener
		final ListSelectionListener baseCurrencyListLSL = new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				selectedBaseCurrencyChanged();
			}
		};
		viewObj.addBaseCurrencyListLSL(baseCurrencyListLSL);

		// quotedCurrencyList selectionListener
		final ListSelectionListener quotedCurrencyListLSL = new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				selectedQuotedCurrencyChanged();
			}
		};
		viewObj.addQuotedCurrencyListLSL(quotedCurrencyListLSL);
	}
	
	
	public void setSaveCurrPairListener(ActionListener AL){
		viewObj.addBtnSaveAL(AL);
	}
	
	public void setCancelCurrPairListener(ActionListener AL){
		viewObj.addBtnCancelAL(AL);
	}
	
	public CurrencyPair SubscribeCurrPair(){
		final CurrencyPair cp = doSave();
		if (cp != null) {
			viewObj.updateExchangeList();
			viewObj.updateBaseCurrencyList();
			viewObj.updateQuotedCurrencyList();
			return cp;
		} else {
			JOptionPane.showMessageDialog(null,
					Strings.getString("RuleEditController.validationMessage"),
					"CCC", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	private void initData() {
		exchangeList.addAll(ExchangeController.getInstance()
				.getAllExchanges());
		baseCurrencyList.addAll(CurrencyController.getInstance()
				.getAllCurrencies());
		quotedCurrencyList.addAll(CurrencyController.getInstance()
				.getAllCurrencies());
	}

	public void filterExchangeList(final String pExchangeName) {
		exchangeList = new ArrayList<Exchange>();
		final ArrayList<Exchange> tempExchangeList = new ArrayList<Exchange>(
				ExchangeController.getInstance().getAllExchanges());
		if (!tempExchangeList.isEmpty()) {
			for (final Exchange ex : tempExchangeList) {
				if (ex.getExchangeName().toLowerCase()
						.contains(pExchangeName.toLowerCase())
						|| ex.toString().toLowerCase()
								.contains(pExchangeName.toLowerCase())) {
					exchangeList.add(ex);
				}
			}
		}
	}

	public void filterBaseCurrencyList(final String pBaseCurrencyName) {
		baseCurrencyList = new ArrayList<CCC_Currency>();
		if (selectedExchange != null) {
			final ArrayList<CurrencyPair> tempCurrencyPairList = new ArrayList<CurrencyPair>(
					CurrencyPairController.getInstance()
							.getAllCurrencyPairs());
			if (!tempCurrencyPairList.isEmpty()) {
				for (final CurrencyPair c : tempCurrencyPairList) {
					if (isValidBaseCurrency(c, pBaseCurrencyName)) {
						updateEnabledComponents();
						baseCurrencyList.add(c.getBaseCurrency());
					}
				}
			}
		}
	}
	
	public void filterQuotedCurrencyList(final String pQuotedCurrencyName) {
		quotedCurrencyList = new ArrayList<CCC_Currency>();
		if (selectedExchange != null && selectedBaseCurrency != null) {
			final ArrayList<CurrencyPair> tempCurrencyPairList = new ArrayList<CurrencyPair>(
					CurrencyPairController.getInstance()
							.getAllCurrencyPairs());
			
			if (!tempCurrencyPairList.isEmpty()) {
				for (final CurrencyPair c : tempCurrencyPairList) {
					if (isValidQuotedCurrency(c, pQuotedCurrencyName)) {
						quotedCurrencyList.add(c.getQuotedCurrency());
					}
				}
			}
		}
	}
	
	/**
	 * Check if the QuotedCurrency is valid for the ui-List
	 * */
	private boolean isValidQuotedCurrency(CurrencyPair pCurrencyPair, String pQuotedCurrencyName){
		if (!pCurrencyPair.isSubscribed()
				&& pCurrencyPair.getExchange() == selectedExchange
				&& pCurrencyPair.getBaseCurrency() == selectedBaseCurrency
				&& !quotedCurrencyList.contains(pCurrencyPair.getQuotedCurrency())
				&& compareCurrency(pCurrencyPair.getQuotedCurrency(), pQuotedCurrencyName)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the BaseCurrency is valid for the ui-List
	 * */
	private boolean isValidBaseCurrency(CurrencyPair pCurrencyPair, String pBaseCurrencyName){
		if (!pCurrencyPair.isSubscribed()
				&& pCurrencyPair.getExchange() == selectedExchange
				&& !baseCurrencyList.contains(pCurrencyPair.getBaseCurrency())
				&& compareCurrency(pCurrencyPair.getBaseCurrency(), pBaseCurrencyName)) {
			return true;
		}
		return false;
	}
	
	private boolean compareCurrency(CCC_Currency pCurrency1, String pCurrencyName2){
		pCurrencyName2 = (pCurrencyName2 == null)?"":pCurrencyName2;
		updateEnabledComponents();
		return (pCurrency1.getCurrencyName()
				.toLowerCase()
				.contains(pCurrencyName2.toLowerCase())
				|| pCurrency1
						.getShortName()
						.toLowerCase()
						.contains(
								pCurrencyName2
										.toLowerCase())
				|| pCurrency1
				.toString()
				.toLowerCase()
				.contains(pCurrencyName2.toLowerCase()));
	}

	/**
	 * Sets the selected CurrencyPair status to subscribed & add it to the
	 * Library (subscribed list)
	 */
	private CurrencyPair doSave() {
		if (validateInput()) {
			try {
				final CurrencyPair cp = CurrencyPairController.getInstance().getCurrencyPair(
								selectedBaseCurrency, selectedQuotedCurrency,
								selectedExchange);
				if (cp == null) {
					return null;
				}
				cp.setSubscribed(true);
				CurrencyPairController.getInstance()
						.addSuscribedCurrencyPair(cp);
				return cp;
			} catch (final Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/** Validates the selected Input */
	private boolean validateInput() {
		return (selectedExchange != null && selectedBaseCurrency != null && selectedQuotedCurrency != null);
	}

	/** Called if the selection changed in exchangeList */
	private void selectedExchangeChanged() {
		/*
		 * Sets the Text of the Exchange TextField to the selected
		 * Exchange-Name; The if-clause is need to ensure there are no
		 * Write-Conflicts;
		 */
		if (viewObj.getSelectedExchange() != null
				&& !viewObj
						.getTxtExchangeText()
						.toLowerCase()
						.contentEquals(
								viewObj.getSelectedExchange().getExchangeName()
										.toLowerCase())
				&& !viewObj
						.getTxtExchangeText()
						.toLowerCase()
						.contentEquals(
								viewObj.getSelectedExchange().toString()
										.toLowerCase())) {
			viewObj.setTxtExchangeText(viewObj.getSelectedExchange()
					.getExchangeName());
		}
		selectedExchange = viewObj.getSelectedExchange();
		viewObj.resetBaseCurrencyListSelection();
		viewObj.resetQuotedCurrencyListSelection();
		viewObj.setBaseCurrencyText("");
		viewObj.setQuotedCurrencyText("");
		updateBaseCurrencyList();
		updateQuotedCurrencyList();

	}

	/** Called if the selection changed in baseCurrencyList */
	private void selectedBaseCurrencyChanged() {
		/*
		 * Sets the Text of the BaseCurrency TextField to the selected
		 * Currency-Name; The if-clause is need to ensure there are no
		 * Write-Conflicts;
		 */
		if (viewObj.getSelectedBaseCurrency() != null
				&& !viewObj
						.getBaseCurrencyText()
						.toLowerCase()
						.contentEquals(
								viewObj.getSelectedBaseCurrency()
										.getCurrencyName().toLowerCase())
				&& !viewObj
						.getBaseCurrencyText()
						.toLowerCase()
						.contentEquals(
								viewObj.getSelectedBaseCurrency()
										.getShortName().toLowerCase())
				&& !viewObj
						.getBaseCurrencyText()
						.toLowerCase()
						.contentEquals(
								viewObj.getSelectedBaseCurrency().toString()
										.toLowerCase())) {
			viewObj.setBaseCurrencyText(viewObj.getSelectedBaseCurrency()
					.toString());
		}

		selectedBaseCurrency = viewObj.getSelectedBaseCurrency();
		updateEnabledComponents();
		viewObj.resetQuotedCurrencyListSelection();
		viewObj.setQuotedCurrencyText("");
		updateQuotedCurrencyList();
	}

	/** Called if the selection changed in quotedCurrencyList */
	private void selectedQuotedCurrencyChanged() {
		/*
		 * Sets the Text of the QuotedCurrency TextField to the selected
		 * Currency-Name; The if-clause is need to ensure there are no
		 * Write-Conflicts;
		 */
		if (viewObj.getSelectedQuotedCurrency() != null
				&& !viewObj
						.getQuotedCurrencyText()
						.toLowerCase()
						.contentEquals(
								viewObj.getSelectedQuotedCurrency()
										.getCurrencyName().toLowerCase())
				&& !viewObj
						.getQuotedCurrencyText()
						.toLowerCase()
						.contentEquals(
								viewObj.getSelectedQuotedCurrency()
										.getShortName().toLowerCase())
				&& !viewObj
						.getQuotedCurrencyText()
						.toLowerCase()
						.contentEquals(
								viewObj.getSelectedQuotedCurrency().toString()
										.toLowerCase())) {
			viewObj.setQuotedCurrencyText(viewObj.getSelectedQuotedCurrency()
					.toString());
		}

		selectedQuotedCurrency = viewObj.getSelectedQuotedCurrency();
		updateEnabledComponents();
	}

	/** Check if the given String (param) is the name of a valid, shown Exchange */
	public Exchange getExchange(final String pExchangeName) {
		for (final Exchange ex : exchangeList) {
			if (ex.getExchangeName().toLowerCase()
					.contentEquals(pExchangeName.toLowerCase())
					|| ex.toString().toLowerCase()
							.contentEquals(pExchangeName.toLowerCase())) {
				return ex;
			}
		}
		return null;
	}

	/**
	 * Check if the given String (param) is the name of a valid & shown
	 * BaseCurrency
	 */
	public CCC_Currency getBaseCurrency(final String pBaseCurrencyName) {
		for (final CCC_Currency c : baseCurrencyList) {
			if (c.getCurrencyName().toLowerCase()
					.contentEquals(pBaseCurrencyName.toLowerCase())
					|| c.getShortName().toLowerCase()
							.contentEquals(pBaseCurrencyName.toLowerCase())
					|| c.toString().toLowerCase()
							.contentEquals(pBaseCurrencyName.toLowerCase())) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Check if the given String (param) is the name of a valid & shown
	 * QuotedCurrency
	 */
	public CCC_Currency getQuotedCurrency(final String pQuotedCurrencyName) {
		for (final CCC_Currency c : quotedCurrencyList) {
			if (c.getCurrencyName().toLowerCase()
					.contentEquals(pQuotedCurrencyName.toLowerCase())
					|| c.getShortName().toLowerCase()
							.contentEquals(pQuotedCurrencyName.toLowerCase())
					|| c.toString().toLowerCase()
							.contentEquals(pQuotedCurrencyName.toLowerCase())) {
				return c;
			}
		}
		return null;
	}

	private void updateExchangeList() {
		filterExchangeList(viewObj.getTxtExchangeText());
		exchangeListModel.overrideList(exchangeList);
		viewObj.updateExchangeList();
	}

	private void updateBaseCurrencyList() {
		filterBaseCurrencyList(viewObj.getBaseCurrencyText());
		baseCurrencyListModel.overrideList(baseCurrencyList);
		viewObj.updateBaseCurrencyList();
	}

	private void updateQuotedCurrencyList() {
		filterQuotedCurrencyList(viewObj.getQuotedCurrencyText());
		quotedCurrencyListModel.overrideList(quotedCurrencyList);
		viewObj.updateQuotedCurrencyList();
	}

	/** Enables or Disables Components depending on selection-status */
	private void updateEnabledComponents() {
		// Check Quoted Currency Status
		if (selectedQuotedCurrency != null && selectedBaseCurrency != null
				&& selectedExchange != null) {
			viewObj.setBtnSaveEnabled(true);
		} else {
			viewObj.setBtnSaveEnabled(false);
		}

		// Check Base Currency Status
		if (selectedBaseCurrency != null && selectedExchange != null) {
			viewObj.setQuotedCurrencyEnabled(true);
		} else {
			viewObj.setQuotedCurrencyEnabled(false);
		}

		// Check Exchange Status
		if (selectedExchange != null) {
			viewObj.setBaseCurrencyEnabled(true);
		} else {
			viewObj.setBaseCurrencyEnabled(false);
		}
	}

	public CurrencyPairEditView getViewObject() {
		return viewObj;
	}
}
