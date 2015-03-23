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
public class Car {
    
    private int id;
    
    private int year;

  

    private String brand;

       private String color;

    public Car(int id, int year, String brand, String color) {
        this.id = id;
        this.year = year;
        this.brand = brand;
        this.color = color;
    }
    
    
    /**
     * Get the value of brand
     *
     * @return the value of brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set the value of brand
     *
     * @param brand new value of brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }



    /**
     * Get the value of color
     *
     * @return the value of color
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the value of color
     *
     * @param color new value of color
     */
    public void setColor(String color) {
        this.color = color;
    }

  

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of year
     *
     * @return the value of year
     */
    public int getYear() {
        return year;
    }

    /**
     * Set the value of year
     *
     * @param year new value of year
     */
    public void setYear(int year) {
        this.year = year;
    }
    
}
