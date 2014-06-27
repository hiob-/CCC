package ui.internationalization;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Strings {
	private static final String BUNDLE_PATH = "ui.internationalization.strings";
	private static String BUNDLE_NAME = BUNDLE_PATH+"_en_EN"; // Default

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Strings() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "[MISSING]" + key + '!';
		}
	}

	/**
	 * 
	 * @param PBUNDLE_NAME
	 *            , example: _de_DE or _en_EN 
	 *            Don't forget do loadResourceBundle() to change language
	 */
	public static void setLanguageName(String PBUNDLE_NAME) {
		BUNDLE_NAME = BUNDLE_PATH + PBUNDLE_NAME;
	}

	/**
	 * Load the currently setLanguageName in the GUI 
	 * 
	 * @return True if Language was set, False if language wasn't found
	 */
	public static boolean loadLanguage() {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
			return true;
		} catch (MissingResourceException e) {
			return false;
		}
	}


}
