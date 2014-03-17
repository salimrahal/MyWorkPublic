package network.urls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Reverse {

	/**Writing to URL connection::
	 * 

	 * example program that runs the ReverseServlet over the network through a URLConnection
	 * @param args
	 */
	    public static void main(String[] args) throws Exception {
	    	//args[0]="http://example.com/servlet/ReverseServlet ";
	    	//args[1]="Reverse Me";
//	        if (args.length != 2) {
//	            System.err.println("Usage:  java Reverse "
//	                + "http://<location of your servlet/script>"
//	                + " string_to_reverse");
//	            System.exit(1);
//	        }

	        String stringToReverse = URLEncoder.encode("Reverse Me", "UTF-8");

	        /**A- Steps to Writing to the URL:
    1- Create a URL.
    2- Retrieve the URLConnection object.
    3- Set output capability on the URLConnection.
    4- Open a connection to the resource.
    5- Get an output stream from the connection.
    6- Write to the output stream.
    7- Close the output stream. */
	        URL url = new URL("http://example.com/servlet/ReverseServlet");
	        URLConnection connection = url.openConnection();
	        connection.setDoOutput(true);

	        OutputStreamWriter out = new OutputStreamWriter(
	                                         connection.getOutputStream());
	        out.write("string=" + stringToReverse);
	        out.close();
/** B- Reading the result from the URL*/
	        BufferedReader in = new BufferedReader(
	                                    new InputStreamReader(
	                                    connection.getInputStream()));
	        String decodedString;
	        while ((decodedString = in.readLine()) != null) {
	            System.out.println(decodedString);
	        }
	        in.close();
	    }
	}
