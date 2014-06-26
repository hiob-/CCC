package ui.preferences;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.email.EmailAddress;
import ui.internationalization.Strings;

public class EmailAddressTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<EmailAddress> addressList;
	
	private String[] columnNames =	{Strings.getString("EMailPreferencesView.addressTableCol1"),
			Strings.getString("EMailPreferencesView.addressTableCol2")};
	
	private int isActive = 1;
	
	
	public EmailAddressTableModel(List<EmailAddress> addressList){
		this.addressList = addressList;
	}
	
	
			

	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return addressList.size();
	}
	
	@Override
	public final String getColumnName(final int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int column, int row) {
		if(row == 0){
			return addressList.get(column).getAddress();
		}else{
			return addressList.get(column).isActive();
		}
	}
	
	// Rule_Active toggle
	@Override
	public void setValueAt(final Object value, final int rowIndex,
			final int columnIndex) {
		final EmailAddress address = addressList.get(rowIndex);
		if (value instanceof Boolean) {
			if (columnIndex == isActive) {
				address.setActive(((Boolean) value).booleanValue());
			}
		}
	}
	
	@Override
	public boolean isCellEditable(final int row, final int col) {
		if (col == isActive) {
			return true;
		} else {
			return false;
		}
	}
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	public EmailAddress getEmailAddressAt(int row){
		return addressList.get(row);
	}
	
	public void addEmailAddress(EmailAddress address){
		addressList.add(address);
		fireTableDataChanged();
	}
	
	public void removeEmailAddress(EmailAddress address){
		addressList.remove(address);
		fireTableDataChanged();
	}
	
	public int indexOfEmailAddress(EmailAddress address){
		return addressList.indexOf(address);
	}

}
