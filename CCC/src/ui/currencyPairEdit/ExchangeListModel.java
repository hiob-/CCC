package ui.currencyPairEdit;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import model.Exchange;


/**
 * Model for JList containing Exchanges.
 * 
 * @author Luca T�nnler
 * @version 1.0
 * */
public class ExchangeListModel extends AbstractListModel<Exchange> {
	private static final long serialVersionUID = 1L;
	private ArrayList<Exchange> dataList;
	
	public ExchangeListModel(){
		this.dataList = new ArrayList<Exchange>();
	}
	
	public ExchangeListModel(ArrayList<Exchange> pExchangeList){
		this.dataList = new ArrayList<Exchange>(pExchangeList);
	}
	
	public void overrideList(ArrayList<Exchange> pExchangeList){
		this.dataList = new ArrayList<Exchange>(pExchangeList);
		int index0 = dataList.size() - 1;
	    int index1 = index0;
		fireContentsChanged(this, index0, index1);
	}
	
	@Override
	public Exchange getElementAt(int pIndex) {
		return dataList.get(pIndex);
	}

	@Override
	public int getSize() {
		return dataList.size();
	}

}
