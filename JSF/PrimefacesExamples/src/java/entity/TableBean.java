/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

/**
 *
 * @author salim
 */
  
import java.util.ArrayList;   
import java.util.List;
import javax.faces.bean.ManagedBean; 
import javax.faces.bean.SessionScoped;
  
@SessionScoped
@ManagedBean
public class TableBean {  
  
    private List<Car> cars;  
      
    private Car selectedCar;  
  
    public TableBean() {  
        cars = new ArrayList<Car>();  
          
        for(int i = 0 ; i < 9 ; i++)  
            cars.add(new Car("Model_" + i, 2010, "Brand_" + i, "Color_" + i));  
    }  
  
    public Car getSelectedCar() {  
        return selectedCar;  
    }  
    public void setSelectedCar(Car selectedCar) {  
        this.selectedCar = selectedCar;  
    }  
  
    public List<Car> getCars() {  
        return cars;  
    }  
}  