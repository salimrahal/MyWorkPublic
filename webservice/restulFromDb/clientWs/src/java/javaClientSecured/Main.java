/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaClientSecured;

import javaclient.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author salim
 * https://www.google.com.lb/?gws_rd=ssl#q=jersey+client+restful+main
 */
public class Main {

    public static void main(String[] args) {

//testRest();//ok
        testRest3();
    }

    public static void testRest3() {
        JerseyClientSecured client = new JerseyClientSecured();
        Object response = client.findAll_JSON_Auth(String.class);
        System.out.println("response:" + response);
        // do whatever with response
        client.close();
    }

    public static void testRes2() {
        try {

            URL url = new URL("http://localhost:8080/RESTfulExample/json/product/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    public static void testRest() {
        JerseyClient client = new JerseyClient();
        Object response = client.countREST();
        System.out.println("response:" + response);
        // do whatever with response
        client.close();
    }
}
