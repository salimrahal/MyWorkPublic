
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CHashtable;
import model.Circle;
import model.EHashtable;
import model.PlaneCircle;
import model.Point;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
public class A1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //TODO code application logic here
        //A. Create an EHashtable instance and CHashtable instance.
        EHashtable et = new EHashtable(20);
        CHashtable ct = new CHashtable(20);

        //B. create 4 instance circle / 4 planecircle / 4 point
        Circle cr1 = new Circle(1f);
        Circle cr2 = new Circle(2f);
        Circle cr3 = new Circle(3f);
        Circle cr4 = new Circle(4f);

        Point po1 = new Point(10, 10);
        Point po2 = new Point(11, 11);
        Point po3 = new Point(12, 12);
        Point po4 = new Point(13, 13);

        PlaneCircle pcr1 = new PlaneCircle(po1, 11f);
        PlaneCircle pcr2 = new PlaneCircle(po2, 22f);
        PlaneCircle pcr3 = new PlaneCircle(po3, 33f);
        PlaneCircle pcr4 = new PlaneCircle(po4, 44f);

        // c) Write code to add these 4 Circles and 4 PlaneCiarcles to your two hashtables 
        //using keys "Cl", "C2", "C3", "C4", and "PC", "PC2", "PC3", "PC4". 
        et.add(new String("C1"), cr1);
        et.add(new String("C2"), cr2);
        et.add(new String("C3"), cr3);
        et.add(new String("C4"), cr4);

        et.add(new String("PC1"), pcr1);
        et.add(new String("PC2"), pcr2);
        et.add(new String("PC3"), pcr3);
        et.add(new String("PC4"), pcr4);

        ct.add(new String("C1"), cr1);
        ct.add(new String("C2"), cr2);
        ct.add(new String("C3"), cr3);
        ct.add(new String("C4"), cr4);

        ct.add(new String("PC1"), pcr1);
        ct.add(new String("PC2"), pcr2);
        ct.add(new String("PC3"), pcr3);
        ct.add(new String("PC4"), pcr4);

        //D) Add() the string "Hi" to your two hashtables using key "S1".   
        et.add(new String("S1"), new String("Hi"));
        ct.add(new String("S1"), new String("Hi"));

        // E) Then clone() and dclone() both hashtables.
        try {

            EHashtable clonedEtable = (EHashtable) et.clone();
            CHashtable clonedCtable = (CHashtable) ct.clone();
            EHashtable deepclonedEtable = (EHashtable) et.dclone();
            CHashtable deepclonedCtable = (CHashtable) ct.dclone();

            //F) Then iterate through all 4 cloned hashtables printing their contents 
            System.out.println();
            System.out.println("****Contents of the shallow cloned EHashtable clonedEtable.****");
            System.out.println();
            Iterator iter = null;
            iter = clonedEtable.iterate();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                System.out.println("Key=" + entry.getKey() + ",value=" + entry.getValue().toString());
            }

            System.out.println();
            System.out.println("****Contents of the deep cloned EHashtable deepclonedEtable.****");
            System.out.println();
            iter = null;
            iter = deepclonedEtable.iterate();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                System.out.println("Key=" + entry.getKey() + ",value=" + entry.getValue().toString());
            }
            System.out.println();
            System.out.println("****Contents of the shallow cloned CHashtable clonedCtable.****");
            System.out.println();
            iter = null;
            iter = clonedCtable.iterate();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                System.out.println("Key=" + entry.getKey() + ",value=" + entry.getValue().toString());
            }
            System.out.println();
            System.out.println("****Contents of the deep cloned CHashtable deepclonedCtable.****");
            System.out.println();
            iter = null;
            iter = deepclonedCtable.iterate();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                System.out.println("Key=" + entry.getKey() + ",value=" + entry.getValue().toString());
            }
            //G) Add() two new ("C1",value ) ("PC I",value2) pairs to the original hashtables where the values differ from their previous values.
            /* Assignement A1
             * Circle cr5 = new Circle(99f);
            Point po5 = new Point(99, 99);
            PlaneCircle pcr5 = new PlaneCircle(po5, 9999f);

            ct.add(new String("C1"), cr5);
            ct.add(new String("PC1"), pcr5);

            et.add(new String("C1"), cr5);
            et.add(new String("PC1"), pcr5);
           */
            /*G Assignement 1BB: Adding two new Keys C1 and PC1 with different values AND 
              and change the circle instance cr2 and point instance po2
            */
            Circle cr5 = new Circle(99f);
            Point po5 = new Point(99, 99);
            PlaneCircle pcr5 = new PlaneCircle(po5, 9999f);

            ct.add(new String("C1"), cr5);
            ct.add(new String("PC1"), pcr5);

            et.add(new String("C1"), cr5);
            et.add(new String("PC1"), pcr5);
            
            cr2.setRadius(123f);
            po2.setX(456f);
            
            System.out.println();
            // h) Then iterate through all 4 cloned hashtables again
            System.out.println("****After Adding two new pairs (C1,Value1) and (PC1,Value2) to Original hashtables where the values differ from the origina hashtables.****");           
            System.out.println("****After editing cr2 and po2.****");           
            System.out.println();
            System.out.println();
            System.out.println("****Contents of the shallow cloned EHashtable clonedEtable.****");
            System.out.println();
            iter = null;
            iter = clonedEtable.iterate();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                System.out.println("Key=" + entry.getKey() + ",value=" + entry.getValue().toString());
            }

            System.out.println();
            System.out.println("****Contents of the deep cloned EHashtable deepclonedEtable.****");
            System.out.println();
            iter = null;
            iter = deepclonedEtable.iterate();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                System.out.println("Key=" + entry.getKey() + ",value=" + entry.getValue().toString());
            }
            System.out.println();
            System.out.println("****Contents of the shallow cloned CHashtable clonedCtable.****");
            System.out.println();
            iter = null;
            iter = clonedCtable.iterate();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                System.out.println("Key=" + entry.getKey() + ",value=" + entry.getValue().toString());
            }
            System.out.println();
            System.out.println("****Contents of the deep cloned CHashtable deepclonedCtable.****");
            System.out.println();
            iter = null;
            iter = deepclonedCtable.iterate();
            while (iter.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iter.next();
                System.out.println("Key=" + entry.getKey() + ",value=" + entry.getValue().toString());
            }

            //I. clrearing both Hashtable
            System.out.println("****clrearing both original tables****");
            System.out.println();
            et.clear();
            System.out.println();
            ct.clear();

            //J. Then check if they are empty using isNotEmpty() and printing results.
            System.out.println();
            System.out.println("*EHashtable isNotEmpty method returns :" + et.isNotEmpty());
            System.out.println();
            System.out.println("*CHashtable isNotEmpty method returns :" + ct.isNotEmpty());
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        } catch (ClassCastException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println("\n Error Message:" + e.toString());
        }
    }
}
