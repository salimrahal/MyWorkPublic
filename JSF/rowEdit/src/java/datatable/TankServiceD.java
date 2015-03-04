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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "tankServiceD")
@ApplicationScoped
public class TankServiceD {
    /* todo: Salim: to be completed*/

    private final static String[] capacities;

    static {
        capacities = new String[10];
        capacities[0] = "5";
        capacities[1] = "6";
        capacities[2] = "9";
        capacities[3] = "10";
        capacities[4] = "12";
        capacities[5] = "5";
        capacities[6] = "6";
        capacities[7] = "7";
        capacities[8] = "8";
        capacities[9] = "20";
    }

    public List<Tank> createTanks(int size) {
        List<Tank> list = new ArrayList<Tank>();
        for (int i = 0; i < size; i++) {
            list.add(new Tank(getRandomId(), getRandomYear(), getRandomCapacity()));
        }
        return list;
    }

    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }

    private String getRandomCapacity() {
        return capacities[(int) (Math.random() * 10)];
    }

    public List<String> getCapacities() {
        return Arrays.asList(capacities);
    }
}
