/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Point implements Cloneable {
    float x,y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    @Override
	public Object clone() throws CloneNotSupportedException {
	return super.clone();
	}

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
}
