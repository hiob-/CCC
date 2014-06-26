package ui.main;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;
import logic.preferences.PreferencesHandler;

/**
 * 
 * The sole purpose of this class is to create a new maincontroller, it will
 * handle all the rest
 * 
 * @author Oussama Zgheb
 * 
 */
public class CCC_Main {
	public static MainController mc;

	public static void main(final String[] args) {
		
		Loader.initLogging();
		PreferencesHandler.init();
		
		try {
			Loader.initAll(false);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
				    "Database couldn't be loaded! SQLException. Is the file corrupt?",
				    "CCC",
				    JOptionPane.WARNING_MESSAGE);
			Logger.write("DBLoader threw a SQLException! Corrupt file? -> "+e.getSQLState()+" | "+e.getMessage(), LogLevel.error);
			Loader.deinitAll();	
			System.exit(-1);

		} catch (IOException e){
			JOptionPane.showMessageDialog(null,
				    "Database couldn't be loaded! The file doesn't exist. Check the preferences file DB path.",
				    "CCC",
				    JOptionPane.WARNING_MESSAGE);
			Logger.write("DBLoader coudln't find the DB!"+e.getMessage(), LogLevel.error);
			Loader.deinitAll();
			System.exit(-1);

		} 		
		mc = new MainController();
	
	}
}
