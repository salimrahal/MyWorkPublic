package pack1;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<Integer, String> hash = new HashMap<Integer, String>();
		System.out.println(hash.put(null,null));
		System.out.println(hash.size());
		ArrayList<String> arr = new ArrayList<String>();
		arr.add(null);
		System.out.println(arr.size());
	}

}
