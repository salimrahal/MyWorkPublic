package creatingDestroyingObject;

import java.util.HashMap;
import java.util.Map;

public class StaticFacMethod {

	   // static factory method, this technique is similar to flyweight pattern
	  //Such static factories are effectively type-conversion methods.
	   public static Boolean valueOf(boolean b) {
		   return b ? Boolean.TRUE : Boolean.FALSE;
		   }
	   
	   //Static factory where he hide the new keyword. also knows as 
	   //"type reference"
	//   Map<String, List<String>> m = newInstance();
	   public static  <K, V> Map<K, V> newInstance() {
		   return new HashMap<K, V>();
	   } 
}
