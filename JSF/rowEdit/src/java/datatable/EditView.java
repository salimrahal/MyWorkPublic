/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatable;

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

@ManagedBean(name = "dtEditView")
@ViewScoped /* also it works if Sessionscoped */

public class EditView implements Serializable {

    private List<CarD> cars1;
    private CarD selectedCar;
    @ManagedProperty("#{carServiceD}")
    private CarServiceD service;

    @PostConstruct
    public void init() {
        cars1 = service.createCars(10);
    }

    public void editerAction() {
        //System.out.println("editerAction:edited:" + selectedCar.getId());
    }

    public void prepareEdit() {
        // System.out.println("prepareEdit..");
    }

    public List<CarD> getCars1() {
        return cars1;
    }

    public List<String> getBrands() {
        return service.getBrands();
    }

    public List<String> getColors() {
        return service.getColors();
    }

    public void setService(CarServiceD service) {
        this.service = service;
    }

    public CarD getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(CarD selectedCar) {
        this.selectedCar = selectedCar;
    }

}
