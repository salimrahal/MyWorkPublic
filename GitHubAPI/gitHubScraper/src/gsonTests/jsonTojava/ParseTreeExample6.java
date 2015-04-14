/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsonTests.jsonTojava;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
 
import org.apache.commons.io.IOUtils;
 
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
 
public class ParseTreeExample6 {
    public static void main(String[] args) throws MalformedURLException, IOException {
        String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
        String json = IOUtils.toString(new URL(url));
        JsonParser parser = new JsonParser();
        // The JsonElement is the root node. It can be an object, array, null or
        // java primitive.
        JsonElement element = parser.parse(json);
        // use the isxxx methods to find out the type of jsonelement. In our
        // example we know that the root object is the Albums object and
        // contains an array of dataset objects
        if (element.isJsonObject()) {
            JsonObject albums = element.getAsJsonObject();
            System.out.println(albums.get("title").getAsString());
            JsonArray datasets = albums.getAsJsonArray("dataset");
            for (int i = 0; i < datasets.size(); i++) {
                JsonObject dataset = datasets.get(i).getAsJsonObject();
                System.out.println(dataset.get("album_title").getAsString());
            }
        }
 
    }
}
