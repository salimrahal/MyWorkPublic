package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;

public class Test {

	protected Test(){}
	/**
	 * @param args
	 * @throws CloneNotSupportedException 
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
				
		//treeSetTest();
		Hashtable<Object, Object> hash = new Hashtable<Object, Object>();
		hash.put(new String("S1"), new refClass());
		hash.put(new String("S2"), new refClass());
		hash.put(new String("S1"), new refClass());
		hash.put(new String("S3"), new refClass());
		System.out.println(hash);
		refClass ref1 = new refClass();
		//refClass ref2 = new refClass();
		refClass ref2 = (refClass) ref1.clone();
		System.out.println(ref1+"/"+ref2);
	  
	}
	
	/*
	 * TreeSet by default sort the elment asc: [1, 2, 3, 5, 8, 11]
	 */
	public static void treeSetTest(){
		TreeSet<Number> set = new TreeSet<Number>();
		set.add(5);
		set.add(1);
		set.add(3);
		set.add(2);
		set.add(11);
		set.add(8);
//      short sh=4;
//		set.add(sh);
//		float ff = 12.6f;
//		set.add(ff);		
		System.out.println(set.toString());	
	}
	//duplication wont works
	public void duplicateObject(){
		class Obj{
			String s;
			public Obj(String s)
			{
				this.s=s;
			}
		}
		Obj o1=new Obj("hello");
		Obj o2=new Obj("behhh");
		Collection<Obj> list = new ArrayList<Obj>();
		//Collection<Obj> additionalList = new ArrayList<Obj>();

		System.out.println(11^2);
		System.out.println(list.add(o2));
		
		//dup obj should returns false
		System.out.println(list.add(o1));
	
		System.out.println(list.size());

	}
	
}
