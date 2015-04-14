/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsonTests;

import gsonTests.dataset.Dataset;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salim
 * http://www.studytrails.com/java/json/java-google-json-parse-json-to-java.jsp
 */
public class Albums {
   public String title;
    public String message;
    public List<String> errors = new ArrayList<String>();
    public String total;
    public int total_pages;
    public int page;
    public String limit;
    public List<Dataset> dataset = new ArrayList<Dataset>();
}
