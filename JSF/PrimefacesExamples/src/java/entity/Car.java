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
public class Car {

    private int year;

    private String manufacturer;
    private String color;
    private String brand;
    private String model;
    
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    } 

    Car(String model, int year, String brand, String color) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.model = model;
        this.year = year;
        this.brand = brand;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    /**
     * Get the value of model
     *
     * @return the value of model
     */
    public String getModel() {
        return model;
    }

    /**
     * Set the value of model
     *
     * @param model new value of model
     */
    public void setModel(String model) {
        this.model = model;
    }

}
