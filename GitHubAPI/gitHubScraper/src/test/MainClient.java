/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author salim
 */
public class MainClient {

    public static void main(String[] args) {
        httpGetUser();
        // httpGetRepoRelease();
    }

    public static void httpGetRepoRelease() {
        try {

            URL url = new URL("https://api.github.com/repos/salimrahal/MyWorkPublic/releases");
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


    /*
     it call a ressource and post Json
     Result: 
     {
     "login":"salimrahal","id":3010848,"avatar_url":"https://avatars.githubusercontent.com/u/3010848?v=3",
     "gravatar_id":"",
     "url":"https://api.github.com/users/salimrahal",
     "html_url":"https://github.com/salimrahal","followers_url":"https://api.github.com/users/salimrahal/followers",
     "following_url":"https://api.github.com/users/salimrahal/following{/other_user}",
     "gists_url":"https://api.github.com/users/salimrahal/gists{/gist_id}",
     "starred_url":"https://api.github.com/users/salimrahal/starred{/owner}{/repo}",
     "subscriptions_url":"https://api.github.com/users/salimrahal/subscriptions",
     "organizations_url":"https://api.github.com/users/salimrahal/orgs",
     "repos_url":"https://api.github.com/users/salimrahal/repos",
     "events_url":"https://api.github.com/users/salimrahal/events{/privacy}",
     "received_events_url":"https://api.github.com/users/salimrahal/received_events",
     "type":"User","site_admin":false,"name":"salim","company":"ISAE-Cnam","blog":"http://www.safirasoft.com",
     "location":"Beirut","email":"salim.rahal@isae.edu.lb","hireable":true,"bio":null,"public_repos":4,"public_gists":0,
     "followers":1,"following":2,"created_at":"2012-12-10T20:45:23Z","updated_at":"2015-04-09T10:49:25Z"
     }
     */
    public static void httpGetUser() {
        HttpURLConnection conn = null;
        try {

            URL url = new URL("https://api.github.com/users/salimrahal");
            conn = (HttpURLConnection) url.openConnection();
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
        System.out.println("getting headers fields...");
        Map responseMap = conn.getHeaderFields();
        for (Iterator iterator = responseMap.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            System.out.println(key + " = ");

            List values = (List) responseMap.get(key);
            for (int i = 0; i < values.size(); i++) {
                Object o = values.get(i);
                System.out.println(o + ", ");
            }
        }
    }

}
