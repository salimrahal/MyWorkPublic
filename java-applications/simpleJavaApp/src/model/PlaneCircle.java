/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class PlaneCircle implements Cloneable {
    Point p;
    float radius;

    public PlaneCircle(Point p, float radius) {
        this.p = p;
        this.radius = radius;
    }
    
     @Override
	public Object clone() throws CloneNotSupportedException {
	return super.clone();
	}

    @Override
    public String toString() {
        return "PlaneCircle{" + "point x=" + p.x+";y="+p.y+ ", radius=" + radius + '}';
    }
     
}
