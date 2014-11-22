/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author salim
 */
public class Networking {
     public static String getmyPIP() throws IOException {
        Iav validator = new Iav();
        String remoteip = null;
        String[] urlArr = new String[]{"http://checkip.amazonaws.com/", "http://www.trackip.net/ip", "http://curlmyip.com/", "http://icanhazip.com/"};
        boolean needPIP = true;
        int i = 0;
        while (needPIP && i < urlArr.length) {
            try {
                URL connection = new URL(urlArr[i]);
                i++;
                URLConnection con = connection.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                remoteip = reader.readLine();
            } catch (IOException iOException) {
            } finally {
                if (remoteip != null) {
                    if (validator.validate(remoteip)) {
                        needPIP = false;
                    }
                }
            }
        }//end while
        return remoteip;
    }
}
