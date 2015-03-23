/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dialog;

/**
 *
 * @author salim
 */
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;
 
@ManagedBean(name = "dfView")
public class DFView {
     
    public void viewCars() {
        System.out.println("DFView viewCars() begins..");
        RequestContext.getCurrentInstance().openDialog("viewCars");
    }
     
    public void viewCarsCustomized() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 320);
         
        RequestContext.getCurrentInstance().openDialog("viewCars", options, null);
    }
}
