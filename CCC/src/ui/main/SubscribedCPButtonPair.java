package ui.main;

import javax.swing.JButton;

import model.currency.CurrencyPair;

public class SubscribedCPButtonPair {
	private CurrencyPair cp;
	private JButton button;
	
	public SubscribedCPButtonPair(CurrencyPair cp, JButton button){
		this.cp=cp;
		this.button=button;
	}
	
	public CurrencyPair getCp() {
		return cp;
	}

	public void setCp(CurrencyPair cp) {
		this.cp = cp;
	}
	
	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

}
