package ui.main;
import javax.swing.JOptionPane;

import logic.logEngine.Logger;
import logic.logEngine.Logger.LogLevel;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	public void uncaughtException(Thread t, Throwable e) {
		Logger.write("[ExceptionHandler] Uncaught Exception - "+e.getMessage(), LogLevel.error);
		JOptionPane.showMessageDialog(null, e.getMessage());
		Logger.flush();
	}
}
