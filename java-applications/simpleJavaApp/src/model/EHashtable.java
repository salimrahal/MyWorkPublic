/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EHashtable extends Hashtable<Object, Object> {

    int sizeE;

    public EHashtable() {
    }

    public EHashtable(int i) {
        this.sizeE = i;
    }

    public boolean add(Object key, Object value) {
        boolean res = false;
        if (key != null && value != null) {
            //Always add the element to the hashtable
            this.put(key, value);
            res = true;
        }
        return res;
    }

    @Override
    public void clear() {
        super.clear();
    }

    /*
     *hashtable.clone()
     * Creates a shallow copy of this hashtable.
     * All the structure of the hashtable itself is copied, 
     * but the keys and values are not cloned. This is a relatively expensive operation.
     */
    @Override
    public Object clone() {
        return super.clone();
        //myobjectListB = new HashMap<Integer,myObject>(myobjectListA);
    }

//deep clone
    public Object dclone() {
        Hashtable deepclone = new EHashtable();
        Object valueCloned = null;

        Iterator iter = this.iterate();
        while (iter.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
            //check every entry if cloneable
            String keyOriginal = (String) entry.getKey();
            String keyCopy = keyOriginal;

            //get the value
            Object value = entry.getValue();
            try {
                if (value instanceof Point) {
                    Point p = null;
                    p = (Point) value;
                    valueCloned = p.clone();
                }
                if (value instanceof Circle) {
                    Circle c = null;
                    c = (Circle) value;
                    valueCloned = c.clone();
                }
                if (value instanceof PlaneCircle) {
                    PlaneCircle pc = null;
                    pc = (PlaneCircle) value;
                    valueCloned = pc.clone();
                } else if (value instanceof String) {
                    //not clonable, it doesn't implement cloneable
                    valueCloned = value;
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(EHashtable.class.getName()).log(Level.SEVERE, null, ex);
            }
            deepclone.put(keyCopy, valueCloned);
            //then add the entry to the deepclone Hashtable
        }
        return deepclone;
    }

    public boolean isNotEmpty() {
        return (!this.isEmpty());
    }

    public Iterator iterate() {
        Iterator<Map.Entry<Object, Object>> it;
        it = this.entrySet().iterator();
        return it;
    }
}
