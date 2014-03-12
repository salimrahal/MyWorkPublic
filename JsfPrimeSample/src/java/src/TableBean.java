/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author salim
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TableBean implements Serializable{

    private final static String[] colors;
    private final static String[] manufacturers;
    private List<Car> carsSmall;
    private Car selectedCar;

    static {
        colors = new String[10];
        colors[0] = "Black";
        colors[1] = "White";
        colors[2] = "Green";
        colors[3] = "Red";
        colors[4] = "Blue";
        colors[5] = "Orange";
        colors[6] = "Silver";
        colors[7] = "Yellow";
        colors[8] = "Brown";
        colors[9] = "Maroon";

        manufacturers = new String[10];
        manufacturers[0] = "Mercedes";
        manufacturers[1] = "BMW";
        manufacturers[2] = "Volvo";
        manufacturers[3] = "Audi";
        manufacturers[4] = "Renault";
        manufacturers[5] = "Opel";
        manufacturers[6] = "Volkswagen";
        manufacturers[7] = "Chrysler";
        manufacturers[8] = "Ferrari";
        manufacturers[9] = "Ford";
    }

    public TableBean() {
        carsSmall = new ArrayList<Car>();

        populateRandomCars(carsSmall, 1);
    }

    private void populateRandomCars(List<Car> list, int size) {
        for (int i = 0; i < size; i++) //            list.add(new Car(getRandomModel(), getRandomYear(), getRandomManufacturer(), getRandomColor()));  
        {
            list.add(new Car("CAR1", "2010", "RENAULT", "RED"));
             list.add(new Car("CAR2", "2012", "BMW", "RED"));
        }
       
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public List<Car> getCarsSmall() {
        return this.carsSmall;
    }
}
