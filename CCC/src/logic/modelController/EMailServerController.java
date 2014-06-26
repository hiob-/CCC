package logic.modelController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.persistence.DbHandler;
import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;
import model.email.EmailAddress;
import model.email.EmailServerData;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;


/**
 * 
 * @author Diego Etter
 * @version 1.0
 *
 */

public class EMailServerController extends Observable implements Observer {
	private List<EmailServerData> emailServerList;
	private Dao<EmailServerData, Integer> emailServerDao;
	private List<EmailAddress> emailAddressList;
	private Dao<EmailAddress, Integer> emailAddressDao;
	
	private static EMailServerController instance=null;
	
	private EMailServerController(){
		initServerCollection();
		initAddressCollection();
	}
	
	public static EMailServerController getInstance() {
		if (instance == null) {
			instance = new EMailServerController();
		}
		return instance;
	}



	private void initServerCollection() {
		try {
			emailServerDao = DaoManager.createDao(DbHandler.getInstance(), EmailServerData.class);
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		// Get Objects and add them to the library
		try {
			emailServerList = emailServerDao.queryForAll();
			Logger.write("[EMailServerCollection] Loaded "+emailServerList.size()+" EmailServer Settings from DB ", LogLevel.info);
		} catch (final SQLException e) {
			e.printStackTrace();
			emailServerList = new ArrayList<EmailServerData>();
		}	
		for(EmailServerData email: emailServerList){
			email.addObserver(this);
		}
	}
	
	private void initAddressCollection(){
		try{
			emailAddressDao = DaoManager.createDao(DbHandler.getInstance(), EmailAddress.class);
		} catch(final SQLException e) {
			e.printStackTrace();
		}
		
		try {
			emailAddressList = emailAddressDao.queryForAll();
			Logger.write("[EMailServerCollection] Loaded "+emailAddressList.size()+" Email Addresses from DB ", LogLevel.info);
		} catch (SQLException e) {
			e.printStackTrace();
			emailAddressList = new ArrayList<EmailAddress>();
		}
		for(EmailAddress address: emailAddressList){
			address.addObserver(this);
		}
	}
	
	
	/**
	 * 
	 * @param EmailServerData to add
	 */
	public void addEMailServer(EmailServerData emailData){
		emailServerList.add(emailData);
		emailData.addObserver(this);
		setChanged();
		notifyObservers(new ObserverObjectWrapper(emailData, ActionType.ADD));
		try {
			emailServerDao.create(emailData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param EmailServerData to remove
	 */
	public void removeEMailServer(EmailServerData emailData){
		if(emailServerList.remove(emailData)){

			try {
				emailServerDao.delete(emailData);
				setChanged();
				notifyObservers(new ObserverObjectWrapper(emailData, ActionType.REMOVE));
				emailData.deleteObservers();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Couldn't remove the EmailServer");
				emailServerList.add(emailData); // reset remove
			}
		}
	}
	
	/**
	 * 
	 * @return the EMailServerData's as a List
	 */
	public List<EmailServerData> getEMailServerList(){
		return emailServerList;
	}
	
	
	/**
	 * 
	 * @param EmailAddress to add
	 */
	public void addEmailAddress(EmailAddress address){
		emailAddressList.add(address);
		address.addObserver(this);
		setChanged();
		notifyObservers(new ObserverObjectWrapper(address, ActionType.ADD));
		try {
			emailAddressDao.create(address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param EmailAddress to remove
	 */
	public void removeEmailAddress(EmailAddress address){
		if(emailAddressList.remove(address)){
			try {
				emailAddressDao.delete(address);
				setChanged();
				notifyObservers(new ObserverObjectWrapper(address, ActionType.REMOVE));
				address.deleteObservers();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Couldn't remove the EmailAddress");
				emailAddressList.add(address);
			}
		}
	}

	/**
	 * 
	 * @return the EmailAddressList
	 */
	
	public List<EmailAddress> getEmailAddressList(){
		return emailAddressList;
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ObserverObjectWrapper){
			ObserverObjectWrapper wrapper = (ObserverObjectWrapper)arg;
			switch(wrapper.getActionType()){
			case MODIFICATION: try {
					if(wrapper.getObject()instanceof EmailServerData){
						emailServerDao.update((EmailServerData)wrapper.getObject());
					}else{
						emailAddressDao.update((EmailAddress)wrapper.getObject());
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			default:
				break; 
			}
		}
		
	}
}
