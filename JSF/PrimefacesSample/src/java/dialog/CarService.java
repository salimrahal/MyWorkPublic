/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dialog;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author salim
 */
@ManagedBean
@SessionScoped
public class CarService implements Serializable{

    List<Car> createCars(int i) {
        List<Car> cars = new LinkedList<>();
       for(int j=0;j<=i;j++){
           cars.add(new Car(j, 200+j, "b"+j, "blue"));
       }
        return cars;
    }
    
}
