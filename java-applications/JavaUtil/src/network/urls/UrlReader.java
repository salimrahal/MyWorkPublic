package network.urls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlReader {

	/**
	 * 1- This program read directly from URL object, then print out the HTML content of the URL:
	 * 2- the second method read from the URLconnection object
	 * http://www.oracle.com/
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		readingFromURLConnection();
	}

	public static void readingDirectlyFromURL() throws Exception {
		URL oracle = new URL("http://www.oracle.com/");
		// operation like InputStreamrteader, will implicity obtain connection
		// if necessary.
		// The connection is opened implicitly by calling getInputStream.
		BufferedReader in = new BufferedReader(new InputStreamReader(
				oracle.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}
/**
 * 
 * @throws Exception
 */
	public static void readingFromURLConnection() throws Exception {
		URL oracle = new URL("http://www.oracle.com/");
		URLConnection yc = oracle.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}
}
