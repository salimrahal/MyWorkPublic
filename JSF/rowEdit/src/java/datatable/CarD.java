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
public class CarD {

    private String id;

    private int year;

    private boolean soldstate;
    private String brand;
    private int price;
    private String color;

    /**
     * Get the value of soldstate
     *
     * @return the value of soldstate
     */
    public boolean isSoldstate() {
        return soldstate;
    }

    /**
     * Set the value of soldstate
     *
     * @param soldstate new value of soldstate
     */
    public void setSoldstate(boolean soldstate) {
        this.soldstate = soldstate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    
    // Car(getRandomId(), getRandomBrand(), getRandomYear(), getRandomColor(), getRandomPrice(), getRandomSoldState()));
    public CarD(String id, String brand, int year, String color, int price, boolean soldstate) {
        this.id = id;
        this.year = year;
        this.brand = brand;
        this.color = color;
        this.price = price;
        this.soldstate = soldstate;
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
    public String getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
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
