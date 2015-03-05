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
    private List<CarD> cars2;
    private List<Tank> tankL;
    private CarD selectedCar;
    @ManagedProperty("#{carServiceD}")
    private CarServiceD service;

    @ManagedProperty("#{tankServiceD}")
    private TankServiceD serviceTank;

    @PostConstruct
    public void init() {
        cars1 = service.createCars(10);
        cars2 = service.createCars(10);
        tankL = serviceTank.createTanks(10);
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

    public List<CarD> getCars2() {
        return cars2;
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
    /*
     Tank
     */

    public List<Tank> getTankL() {
        return tankL;
    }

    public void setTankL(List<Tank> tankL) {
        this.tankL = tankL;
    }

    public List<String> getCapacities() {
        return serviceTank.getCapacities();
    }

    public void setServiceTank(TankServiceD serviceTank) {
        this.serviceTank = serviceTank;
    }

    public CarD getSelectedCar() {
        if (selectedCar != null) {
           // System.out.println("get selected car:" + selectedCar.getId());
        } else {
           // System.out.println(" selected car:null");
        }

        return selectedCar;
    }

    public void setSelectedCar(CarD selectedCar) {
        this.selectedCar = selectedCar;
        if (selectedCar != null) {
           // System.out.println("setSelectedCar:" + selectedCar.getId());
        } else {
           // System.out.println(" setSelectedCar car:null");
        }
    }

}
