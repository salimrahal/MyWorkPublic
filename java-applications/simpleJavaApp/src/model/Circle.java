/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class Circle implements Cloneable{
 float radius;

    public Circle(float radius) {
        this.radius = radius;
    }
     @Override
	public Object clone() throws CloneNotSupportedException {
	return super.clone();
	}

    @Override
    public String toString() {
        return "Circle{" + "radius=" + radius + '}';
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
    
 
}
