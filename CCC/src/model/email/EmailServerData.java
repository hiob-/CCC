package model.email;


import java.util.Observable;

import model.ObserverObjectWrapper;
import model.ObserverObjectWrapper.ActionType;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "EMailServerData")
public class EmailServerData extends Observable{


	/**
	 *
	 * Saves all settings needed to connect to a smtp server
	 * 
	 * @author Diego Etter
	 * @version 1.0
	 * */
	
	
	@DatabaseField(columnName = "id", generatedId = true)
	int id;
	@DatabaseField(columnName = "username")
	String username;
	@DatabaseField(columnName = "password")
	String password;
	@DatabaseField(columnName = "hostaddress")
	String host;
	@DatabaseField(columnName = "port")
	Integer port;
	@DatabaseField(columnName = "encryption")
	String encryption;
	
	private static final byte[] key = "@(FD83;so?`Uw2pö^ik3(&1x(N3izbquii=IU+%3hjROM2ki9>>jgDUgGZ2b2i()Tt&+^aBa&19M.-_A<<".getBytes();

	// EMPTY CTOR FOR OR MAPPER
	public EmailServerData(){
		
	}
	
	public EmailServerData(String username, String password, String host,
			Integer port, String encryption) {
		this.username = username;
		this.password = encDecPW(password);
		this.host = host;
		this.port = port;
		this.encryption = encryption;
	}
	
	
	/**
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(final int id) {
		this.id = id;
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	/**
	 * 
	 * @return the username
	 */

	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	/**
	 * 
	 * @return the current password
	 */
	
	public String getPassword() {
		return encDecPW(password);
	}
	
	public static byte[] xorBArr(byte[] toXOR){
		byte[] result = new byte[toXOR.length];
		for (int i=0; i< toXOR.length ; i++){
			result[i] = (byte) (toXOR[i] ^ key[i]);			
		}
		return result;
	}
	
	public static String encDecPW(String password){
		return (new String(xorBArr(password.getBytes())));
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = encDecPW(password);
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}

	/**
	 * @return encryptiontype
	 */
	public String getEncyption() {
		return encryption;
	}

	/**
	 * @param encryptiontype SSL, TLS, None
	 */
	public void setEncryption(String encryption) {
		this.encryption = encryption;
		setChanged();
		notifyObservers(new ObserverObjectWrapper(this, ActionType.MODIFICATION));
	}
	
	@Override
	public String toString(){
	//	return username + ", " + host + ", " + encryptionType.toString();
		return username;
	}


}
