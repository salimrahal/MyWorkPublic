package hashmap;

import java.util.Hashtable;
import java.util.Iterator;

public class CloneHashTable {    
    public static void main(String a[]) {    
    	Hashtable hashtable = new Hashtable();    
    	Hashtable hashtable1 = new Hashtable();    
    	hashtable.put(1, "One");
    	hashtable.put(2, "Two");
    	hashtable.put(3, "Three");
       System.out.println("Original HashMap : " + hashtable.hashCode());
       hashtable1 = (Hashtable) hashtable.clone();
       System.out.println("Copied HashMap : " + hashtable1.hashCode()); 
       
      
   } 
}
