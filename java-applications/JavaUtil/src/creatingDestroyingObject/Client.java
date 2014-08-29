package creatingDestroyingObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import pack1.ListUtil;
import pack1.UtilMethods;

public class Client {

	public static void main(String[] args) {

		/*********** Effective Java test ***********/
		/*** Static Factory method test ***/
		/*
		 * Boolean boolObject = StaticFacMethod.valueOf(true);
		 * 
		 * //USING static factory method instead of constructor while creating
		 * Map //newIntance() is the static factory method. Map<String,
		 * List<String>> m = StaticFacMethod.newInstance(); Map<Integer,
		 * List<String>> m0 = StaticFacMethod.newInstance(); Map<String,
		 * List<String>> m1 = new HashMap<String, List<String>>(); /*
		 */

		/**************** Enforce Noninstantiability with Private constructor ************/
		// noninstantiable class test, throws assertion error, to ensure the
		// noninstantiability
		/*
		 * UtilityClass.invokeConstructByMistake(); /*
		 */

		/********** Avoid creating unnecessary objects ****************/
		/*
		 * Person pers = new Person(); Date sysdate = new Date();
		 * System.out.println(sysdate.getTime()); for(int i=0;i<=100;i++){
		 * System.out.println(pers.isBabyBoomer()); }
		 * System.out.println(sysdate.getTime()); /*
		 */
		/*
		 * PersonImproved persImp = new PersonImproved(); for(int
		 * i=0;i<=100;i++){ System.out.println(persImp.isBabyBoomer()); } /*
		 */
		/**/
		/**prefer primitives to boxed primitives, and watch out for unintentional autoboxing.*/
		// using Long: Hideously slow program! Can you spot the object creation?
		//Long sum = 0L;
		/*
		long sum = 0L;//its better to use primitive type
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println(sum);
        //sum = 2305843005992468481
		/**/
		
		/** Eliminate obsolete object reference **/
		/*
		Stack stk = new Stack();
		stk.push("elt1");
		stk.push("elt2");
		System.out.println(stk.getSize()+"/"+stk.getElements().length+"/"+stk.pop());
		System.out.println(stk.getSize());
		/**/
		
		/**Memory Leaks discussion
		 * Another common source of memory leaks is caches. Once you put an
object reference into a cache, its easy to forget that its there and leave it in the
cache long after it becomes irrelevant. There are several solutions to this problem.
If youre lucky enough to implement a cache for which an entry is relevant exactly
so long as there are references to its key outside of the cache, represent the cache
as a WeakHashMap; entries will be removed automatically after they become obsolete.
Remember that WeakHashMap is useful only if the desired lifetime of cache
entries is determined by external references to the key, not the value.
		 */
		WeakHashMap<Integer,String> weakMap = new WeakHashMap<Integer,String>() ;
		weakMap.put(1,"firstly");
		weakMap.put(2, "secondly");
		System.out.println(weakMap);
		/**item 7 :avoid Finalizers**/
	}
	
	
 
}
