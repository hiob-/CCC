package ui.currencyPairEdit;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import model.currency.CCC_Currency;


/**
 * Model for JList with CCC_Currency.
 * 
 * @author Luca T�nnler
 * @version 1.0
 * */
public class CurrencyListModel extends AbstractListModel<CCC_Currency> {
	private static final long serialVersionUID = 1L;
	private ArrayList<CCC_Currency> dataList;
	
	public CurrencyListModel(){
		this.dataList = new ArrayList<CCC_Currency>();
	}
	
	public CurrencyListModel(ArrayList<CCC_Currency> pCurrencyList){
		this.dataList = new ArrayList<CCC_Currency>(pCurrencyList);
	}
	
	public void overrideList(ArrayList<CCC_Currency> pCurrencyList){
		this.dataList = new ArrayList<CCC_Currency>(pCurrencyList);
		int index0 = dataList.size() - 1;
	    int index1 = index0;
		fireContentsChanged(this, index0, index1);
	}
	
	@Override
	public CCC_Currency getElementAt(int pIndex) {
		return dataList.get(pIndex);
	}

	@Override
	public int getSize() {
		return dataList.size();
	}
}
