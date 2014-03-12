/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dvdrental;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author salim
 */
@ManagedBean
@SessionScoped
public class FilmController {

    int startId;
    int endId;
    DataModel filmTitles;
    FilmHelper helper;
    private int recordCount = 1000;
    private int pageSize = 10;
    private Film current;
    private int selectedItemIndex;

    /**
     * Creates a new instance of FilmController
     */
    //Add the following code (in bold) to create the FilmController instance and retrieve the films. 
    public FilmController() {
         helper = new FilmHelper();
        startId = 1;
        endId = 10;
        
    }
    public FilmController(int startId, int endId) {
        helper = new FilmHelper();
        this.startId = startId;
        this.endId = endId;
    }

    public Film getSelected() {
        if (current == null) {
            current = new Film();
            selectedItemIndex = -1;
        }
        return current;
    }
     public DataModel getFilmTitles() {
        if (filmTitles == null) {
            filmTitles = new ListDataModel(helper.getFilmTitles(startId, endId));
        }
        return filmTitles;
    }

    void recreateModel() {
        filmTitles = null;
    }
    //Add the following methods that are used to display the table and navigate the pages. 
    public boolean isHasNextPage() {
        if (endId + pageSize <= recordCount) {
            return true;
        }
        return false;
    }

    public boolean isHasPreviousPage() {
        if (startId-pageSize > 0) {
            return true;
        }
        return false;
    }

    public String next() {
        startId = endId+1;
        endId = endId + pageSize;
        recreateModel();
        return "index";
    }

    public String previous() {
        startId = startId - pageSize;
        endId = endId - pageSize;
        recreateModel();
        return "index";
    }

    public int getPageSize() {
        return pageSize;
    }

    public String prepareView(){
        current = (Film) getFilmTitles().getRowData();
        return "browse";
    }
    public String prepareList(){
        recreateModel();
        return "index";
    }
    
    //Add the following methods that access the helper class to retrieve additional film details. 
    public String getLanguage() {
        int langID = current.getLanguageByLanguageId().getLanguageId().intValue();
        String language = helper.getLangByID(langID);
        return language;
    }

    public String getActors() {
        List actors = helper.getActorsByID(current.getFilmId());
        StringBuffer totalCast = new StringBuffer();
        for (int i = 0; i < actors.size(); i++) {
            Actor actor = (Actor) actors.get(i);
            totalCast.append(actor.getFirstName());
            totalCast.append(" ");
            totalCast.append(actor.getLastName());
            totalCast.append("  ");
        }
        return totalCast.toString();
    }

    public String getCategory() {
        Category category = helper.getCategoryByID(current.getFilmId());
        return  category.getName();
    }

}
