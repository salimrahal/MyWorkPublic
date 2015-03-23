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
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
 
@ManagedBean(name = "dfCarsView")
@ViewScoped
public class DFCarsView implements Serializable {
     
    public List<Car> cars;
 
    @ManagedProperty("#{carService}")
    public CarService service;

    public DFCarsView() {    
            //service = new CarService();
    }
     
    
    @PostConstruct
    public void init() {    
        System.out.println("DFCarsView init..");
        cars = service.createCars(9);
    }
     
    public List<Car> getCars() {
        return cars;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }

    public CarService getService() {
        return service;
    }
    
}
