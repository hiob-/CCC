package logic.exchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 
 * Makes a valid HTTP Get request and returns the result in a string.
 * 
 * @author Oussama Zgheb
 * @version 1.1
 *
 */
public class HttpGet {
	private int returnCode = -1;


	/**
	 * 
	 * @param A FQDN URL 
	 * @return The response as one string using crossplattform linebreaks
	 * @throws Exception
	 */
	public String getHTML(String urlToRead) throws Exception {

		// Init Variables
		HttpURLConnection conn = null;
		BufferedReader rd;
		String line;
		String result = "";
		String newLine = System.getProperty("line.separator");
		boolean isFirst = true;
		
		// Open Connection
		URL url = new URL(urlToRead);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));			
		// Read the stream
		while ((line = rd.readLine()) != null) {
			if (!isFirst)
				result += newLine;
			isFirst = false;
			result += line;
		}
		rd.close();

		returnCode = conn.getResponseCode();

		return result;
	}

	public int getHTTPResponseCode() {
		return returnCode;
	}

}
