package logic.persistence;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import model.currency.CurrencyPair;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;

/**
 * 
 * This class contains static methods to open and close the connection to the DB
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */

public class DbHandler {

	private static String DATABASE_URL = "jdbc:sqlite:data.db";
	private static JdbcConnectionSource connectionSource;

	public static void openDB() throws SQLException, IOException {
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "WARNING");
		//System.setProperty(LocalLog.LOCAL_LOG_FILE_PROPERTY,);
		
		File dbFile = new File(DATABASE_URL.substring(DATABASE_URL.lastIndexOf(':') + 1));
		if(!dbFile.exists()) {
			Logger.write("[dbHandler] DB File doesn't exist!", LogLevel.info);
			throw new IOException();
		}
		
	
		connectionSource = new JdbcConnectionSource(DATABASE_URL);
		// Test this to check if corrupted
		Dao<CurrencyPair, ?> CurrencyPair_Dao = DaoManager.createDao(connectionSource,
				CurrencyPair.class);
		List<CurrencyPair> all = CurrencyPair_Dao.queryForAll();
		
		
		Logger.write("[dbHandler] Opening DB", LogLevel.info);
	}
	
	public static void setDBPath(String path){
		DATABASE_URL = "jdbc:sqlite:"+path+"data.db";
		Logger.write("[dbHandler] Setting DBPath:"+DATABASE_URL, LogLevel.info);
	}

	public static void closeDB() throws SQLException {
		connectionSource.close();
		Logger.write("[dbHandler] Closing DB", LogLevel.info);
	}

	public static JdbcConnectionSource getInstance() {
		return connectionSource;
	}
}
