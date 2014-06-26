package model.email;

import java.util.Observable;

import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



/**
 * 
 * Saves a email adress.
 * 
 * @author Diego Etter
 * @version 1.0
 *
 */

@DatabaseTable(tableName = "EmailAddress")
public class EmailAddress extends Observable {
	
	public EmailAddress(){
		
	}
	
	
	@DatabaseField(columnName = "id", generatedId = true)
	private int id;
	@DatabaseField(columnName = "address")
	private String Address;
	@DatabaseField(columnName = "active")
	private boolean active;
	
	

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}
	
	/**
	 * 
	 * @return the EmailAddress
	 */
	public String getAddress() {
		return Address;
	}
	
	/**
	 * 
	 * @param String new Address
	 */
	public void setAddress(String address) {
		Address = address;
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}
	
	/**
	 * 
	 * @return boolean isActive
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * 
	 * @param set boolean isActive
	 */
	public void setActive(boolean active) {
		this.active = active;
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}
	
	@Override
	public String toString(){
		return Address;
	}
	
	
}
