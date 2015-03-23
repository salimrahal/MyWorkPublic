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
import beans.*;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "dtEditView")
@ViewScoped /* also it works if Sessionscoped */
public class EditView implements Serializable {

    private List<CarD> cars1;
    private List<CarD> cars2;
    private List<Tank> tankL;

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

    public void onRowEdit(RowEditEvent event) {
        System.out.println("onRowEdit");
        FacesMessage msg = new FacesMessage("Car Edited", ((CarD) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((CarD) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancelTank(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Tank Cancelled", ((Tank) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEditTank(RowEditEvent event) {
        System.out.println("onRowEdit2");
        FacesMessage msg = new FacesMessage("Tank Edited", ((Tank) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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

}
