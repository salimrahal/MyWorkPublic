/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsonTests.dataset;

import com.google.gson.annotations.SerializedName;
import gsonTests.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salim
 */
public class Dataset {
    public String album_id;
    public String album_title;
    @SerializedName("album_images")
    List<AlbumImages> images = new ArrayList<AlbumImages>();
}
