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

public class CHashtable implements Cloneable{
    Hashtable<Object, Object> hashtable;
    int sizeC;
    
    
    public CHashtable(int i) {
      sizeC = i;
      hashtable = new Hashtable<>();
    }

    public int getSizeC() {
        return sizeC;
    }

    public void setSizeC(int sizeC) {
        this.sizeC = sizeC;
    }  

    public Hashtable<Object, Object> getHashtable() {
        return hashtable;
    }

    public void setHashtable(Hashtable<Object, Object> hashtable) {
        this.hashtable = hashtable;
    }
    
    
    public boolean add(Object key, Object value) {
        boolean res = false;    
        if(key!=null && value !=null){
        //Always add the element to the hashtable
        hashtable.put(key, value);
        res = true;
        }
        return res;
    }
     public void clear() {
        this.hashtable.clear();
    }
     
    @Override
      public Object clone() throws CloneNotSupportedException  {
        Object obj = null;
        obj = super.clone();    
        return obj;
    }
      
      //deep clone: return a new CHashtabe instance contains a new Hashtable instance
    public Object dclone() {
        CHashtable cHash = new CHashtable(getSizeC());
        Hashtable<Object, Object> deepcloneHash = cHash.getHashtable();
        Object valueCloned = null;

        Iterator iter = this.iterate();
        while (iter.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
            //check every entry if cloneable, String is not cloenable
            String keyOriginal = (String) entry.getKey();
            String keyCopy = keyOriginal;

            //get the value, then clone it
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
                }
                else if (value instanceof String) {
                    //not clonable, it doesn't implement cloneable
                    valueCloned = value;
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(EHashtable.class.getName()).log(Level.SEVERE, null, ex);
            }
            //put every entry in the new HASH
            deepcloneHash.put(keyCopy, valueCloned);
            //then add the entry to the deepclone Hashtable
        }
        //set the new hash to the CgashTable instance
        cHash.setHashtable(deepcloneHash);
        return cHash;
    }
     public boolean isNotEmpty() {
        return (!this.hashtable.isEmpty());
    }

    public Iterator iterate() {
        Iterator<Map.Entry<Object, Object>> it;
        it = this.hashtable.entrySet().iterator();
        return it;
    }    
}
