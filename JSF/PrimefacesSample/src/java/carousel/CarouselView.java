/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carousel;

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
 
@ManagedBean
@ViewScoped
public class CarouselView implements Serializable {
     
    private List<CarC> cars;
     
    private CarC selectedCar;
     
    @ManagedProperty("#{carServiceC}")
    private CarServiceC service;
     
    @PostConstruct
    public void init() {
        cars = service.createCars(9);
    }
 
    public List<CarC> getCars() {
        return cars;
    }
 
    public void setService(CarServiceC service) {
        this.service = service;
    }
 
    public CarC getSelectedCar() {
        return selectedCar;
    }
 
    public void setSelectedCar(CarC selectedCar) {
        this.selectedCar = selectedCar;
    }
}
