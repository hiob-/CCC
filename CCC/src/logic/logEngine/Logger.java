package logic.logEngine;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

/**
 * Writes a simple HTML log file with log-levels setting the font color
 *  
 * @author Oussama Zgheb
 * @version 1.1
 */

public class Logger {
	private static Writer writer = null;

	public static void intialize() throws IOException {

		writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("log.html"), "utf-8"));
		writer.write("<html> <body bgcolor=\"#000000\">");
	}

	public static void flush(){
		try {
			writer.flush();
		} catch (IOException e) {
		}
	}
	
	public static void close() {
		try {
			writer.write(printDebugMemoryUsage());
			writer.write("</body></html>");
			writer.close();
			System.out.println("Log written to file.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void write(String text, LogLevel level) {
		Date curDat = new Date();
		try {
			writer.write("<p><font color=\"" + level.getColorCode() + "\">"
					+ curDat.toString() + ":" + text + "</p>");
		} catch (IOException e) {
			// there is nothing we can do . . . 
		}
	}
	
	private static String printDebugMemoryUsage() {
		int mb = 1024*1024;
		String memoryUsage;
		//Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();
		memoryUsage=("##### Heap utilization statistics [MB] #####"+"<br>");
		//Print used memory
		memoryUsage+=("Used Memory:" 
			+ (runtime.totalMemory() - runtime.freeMemory()) / mb+"<br>");

		//Print free memory
		memoryUsage+=("Free Memory:" 
			+ runtime.freeMemory() / mb+"<br>");
		
		//Print total available memory
		memoryUsage+=("Total Memory:" + runtime.totalMemory() / mb+"<br>");

		//Print Maximum available memory
		memoryUsage+=("Max Memory:" + runtime.maxMemory() / mb+"<br>");
		return(memoryUsage);
	}


	public enum LogLevel {
		warning("#FFF300"), error("#FF0000"), info("#1A00FF");

		private final String colorCode;

		private LogLevel(String colorCode) {
			this.colorCode = colorCode;
		}

		public String getColorCode() {
			return colorCode;
		}
	}

}
