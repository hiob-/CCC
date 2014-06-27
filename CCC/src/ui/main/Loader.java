package ui.main;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ui.internationalization.Strings;

import extensions.exchangeImplementations.BTCE_Exchange_Implementation;
import extensions.exchangeImplementations.CryptsyCurrencyPairUpdater;
import extensions.exchangeImplementations.Cryptsy_Exchange_Implementation;
import extensions.exchangeImplementations.FAKE_Exchange_Implementation;
import extensions.exchangeImplementations.BTCE_Exchange_Implementation.BTCE_EXCHANGE;
import extensions.exchangeImplementations.Cryptsy_Exchange_Implementation.CRYPTSY_EXCHANGE;
import extensions.exchangeImplementations.FAKE_Exchange_Implementation.FAKE_EXCHANGE;

import logic.exchange.DisableCertCheck;
import logic.exchange.ExchangeController;
import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.persistence.DbHandler;
import logic.preferences.PreferencesHandler;
import logic.updater.CurrencyPairUpdateManager;

/**
 * 
 * This class contains static methods to init db & logging and shutting it down
 * again
 * 
 * @author Oussama Zgheb
 * @version 1.0
 * 
 */

public class Loader {

	public static void initLogging() {
		// Start Logging Engine
		try {
			logic.logEngine.Logger.intialize();
		} catch (final IOException e1) {
			e1.printStackTrace();
		}

		Logger.write("CCC Starting", LogLevel.info);
	}

	public static void initAll(boolean testMode) throws SQLException, IOException {


		
		//final ExceptionHandler handler = new ExceptionHandler();
		//Thread.setDefaultUncaughtExceptionHandler(handler);
		
		// Look & Feel
		initNimbus();
		
		initExchanges();
		
		// Open DB connection
		DbHandler.openDB();
		
		try {
			DisableCertCheck.doIt();
		} catch (final Exception e) {
			Logger.write("Disable Cert check failed", LogLevel.warning);
			e.printStackTrace();
		}
		
		Loader.loadValuesIntoCCC(PreferencesHandler.getDbPath(),PreferencesHandler.getUpdateInterval(),PreferencesHandler.getLanguage());
		System.out.println(PreferencesHandler.getLanguage());
		
		// Start CurrencyPair Price Update Thread
		if(!testMode){
			runCryptsyUpdater();
			CurrencyPairUpdateManager.getInstance().runAllThreads();
		}
		
		
	}

	private static void initExchanges() {
		
		// We have to initialize Exchanges manually, as we cannot save abstract
		// classes nor methods in the DB
		final CRYPTSY_EXCHANGE CRYPTSY = new Cryptsy_Exchange_Implementation.CRYPTSY_EXCHANGE(
				2, "Cryptsy", "http://cryptsy.com");
		ExchangeController.getInstance().addToList(CRYPTSY);

		final BTCE_EXCHANGE BTCE = new BTCE_Exchange_Implementation.BTCE_EXCHANGE(1,
				"BTC-E", "http://btc-e.com");
		ExchangeController.getInstance().addToList(BTCE);
		
		final FAKE_EXCHANGE FAKE = new FAKE_Exchange_Implementation.FAKE_EXCHANGE(3,
				"FAKE-EX", "http://hodor.com");
		ExchangeController.getInstance().addToList(FAKE);
		
	}

	private static void runCryptsyUpdater() {
		// Start a new Thread which updates Cryptsy CurrencyPairs
		final Thread cryptsyCPUpdaterThread = new Thread() {
			@Override
			public void run() {
				final CryptsyCurrencyPairUpdater ccpUpdater = new CryptsyCurrencyPairUpdater();
				ccpUpdater.update();
			}
		};
		cryptsyCPUpdaterThread.start();
	}



	private static void initNimbus() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            Logger.write("[init] Loaded Nimbus", LogLevel.info);
		            return;
		        }
		    }
		    throw new Exception();
		} catch (Exception e) {
		    // If Nimbus is not available, fall back to cross-platform
			Logger.write("[init] Nimbus not found / available. Fallback to cross-platform", LogLevel.warning);
		    try {
		        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch (Exception ex) {
		    	// not worth my time
		    	Logger.write("[init] No Look & Feel could have been loaded. Your GUI must look quite weird now.", LogLevel.error);
		    }
		}		
	}
	public static void loadValuesIntoCCC(String dbPath, int updateInterval, String language) {
		// DB
		DbHandler.setDBPath(dbPath);
		// Thread
		CurrencyPairUpdateManager.setUpdateInterval(updateInterval);
		//RuleExecuteManager.setUpdateInterval(updateInterval);
		// Language
		Strings.setLanguageName(language);
		if(Strings.loadLanguage()){
			Logger.write("Loading new Resource Bundle (language): " + language,LogLevel.info);
		}else{
			Logger.write("Couldn't find Resource Bundle (language): "+ language, LogLevel.error);
		}		
	}
	
	public static void deinitAll() {

		Logger.flush();
		PreferencesHandler.writeToFile();

		// Kill Update Thread
		//CurrencyPairUpdateManager.getInstance().killThread();
		while(CurrencyPairUpdateManager.getInstance().hasActiveThread()){
			CurrencyPairUpdateManager.getInstance().killAllThreads();
		}
		// Close the DB properly
		try {
			DbHandler.closeDB();
		} catch (final SQLException e) {
			Logger.write("DB couldn't be closed properly", LogLevel.error);
		}
		// Close Log Handle properly
		Logger.write("CCC Closing", LogLevel.info);
		logic.logEngine.Logger.close();

	}
}
