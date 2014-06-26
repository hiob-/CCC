package logic.preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;

import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.persistence.DbHandler;
import logic.updater.CurrencyPairUpdateManager;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

/**
 * 
 * This class handles the persisting of user defined settings and their initialization.
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */
public class PreferencesHandler extends Observable {
	public static final int scientificRepresentation = 1;
	public static final int decimalRepresentation = 2;
	private static String language; // _de_DE
	private static String dbPath; // D:\\data.db
	private static int updateInterval;

	private static Section preferencesIni;
	private static Ini ini;
	private static String emailSender;
	private static int representation;


	/**
	 * Opens the ini file and loads all the values
	 * Also the db path is checked for a valid file
	 */
	public static void init() {

		loadINI();

		loadValues();

		if (!checkDBPathValid()) {
			Logger.write(
					"[PreferencesHandler] No DB exists at dbPath! Resetting to default",
					LogLevel.error);
			dbPath = "";
			preferencesIni.put("dbPath", dbPath);

		}
	}
	

	public static void writeToFile() {
		try {
			ini.store(new FileOutputStream("Preferences.ini"));
			Logger.write("[PreferencesHandler] Written Preferences to INI File",LogLevel.info);
		} catch (IOException e) {
			Logger.write("[PreferencesHandler] Couldn't save settings, io exception",LogLevel.error);
		}
	}

	private static void loadINI() {
		ini = new Ini();
		try {
			ini.load(new FileReader("Preferences.ini"));
		} catch (Exception e) {
			Logger.write("[PreferencesHandler] Ini File not found / io error - exiting",LogLevel.error);
			Logger.close();
			System.exit(0);
		}		
	}



	private static void loadValues() {
		try{
			preferencesIni = ini.get("Preferences");
			language = preferencesIni.get("language");
			dbPath = preferencesIni.get("dbPath");
			updateInterval = preferencesIni.get("updateInterval", Integer.class);
			emailSender = preferencesIni.get("emailSender", String.class);	
			representation = preferencesIni.get("representation", Integer.class);			
		}catch(Exception e){
			setDefaults();
			Logger.write("[PreferencesHandler] One (or more) field in INI missing, loading defaults", LogLevel.error);
		}
		Logger.write("Loaded settings: Lang:" + language + ";  dbPath:" + dbPath
				+ ";  Update Interval:" + updateInterval + ";  emailSender:"
				+ emailSender + ";"+" Representation:"+representation+";", LogLevel.info);
	}

	private static void setDefaults() {
		preferencesIni = ini.add("Preferences");
		setLanguage("");
		setDbPath("");
		setUpdateInterval(5);
		setEmailSender("CCC");	
		setRepresentation(scientificRepresentation);
	}

	/**
	 * Checks if the currently set DB Path points to valid location. This means
	 * there is a data.db file in said directory. If the path is empty there is
	 * no need to check, because then, the local workingpath is used and we
	 * assume there is a file there, else the user fiddled around with the jar.
	 * 
	 * @return True if exists, false if not
	 */
	public static boolean checkDBPathValid() {
		if ((dbPath.equals("")) || (dbPath.length() > 2 && new File(dbPath).exists())) {
			return true;
		}
		return false;
	}

	public static String getLanguage() {
		return language;
	}

	public static void setLanguage(String language) {	
		PreferencesHandler.language = language;
		preferencesIni.put("language", language);
		Logger.write("[PreferencesHandler] Set Language to: " + language,
				LogLevel.info);
	}



	public static String getDbPath() {
		return dbPath;
	}

	public static void setDbPath(String dbPath) {
		dbPath.replace("\\", "\\\\"); // yay java
		PreferencesHandler.dbPath = dbPath;
		preferencesIni.put("dbPath", dbPath);
		DbHandler.setDBPath(dbPath);
		Logger.write("[PreferencesHandler] Set DB Path to: " + dbPath,
				LogLevel.info);
	}

	public static int getUpdateInterval() {
		return updateInterval;
	}

	public static void setUpdateInterval(int updateInterval) {
		PreferencesHandler.updateInterval = updateInterval;
		preferencesIni.put("updateInterval", updateInterval);
		CurrencyPairUpdateManager.setUpdateInterval(updateInterval);
		Logger.write("[PreferencesHandler] Set UpdateInterval to: "
				+ updateInterval + " s", LogLevel.info);
	}

	public static String getEmailSender() {
		return emailSender;
	}
	
	public static void setRepresentation(int state){
		representation = state;
		preferencesIni.put("representation",representation);
		Logger.write("[PreferencesHandler] Set Representation to: "
				+ representation, LogLevel.info);
	}
	
	public static int getRepresentation(){
		return representation;
	}

	public static void setEmailSender(String pemailSender) {
		emailSender = pemailSender;
		preferencesIni.put("emailSender", emailSender);
		Logger.write("[PreferencesHandler] Set emailSender to: " + emailSender,
				LogLevel.info);

	}
}
