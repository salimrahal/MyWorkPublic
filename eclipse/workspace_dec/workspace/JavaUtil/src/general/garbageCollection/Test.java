package general.garbageCollection;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double x = 1000000000;
		System.out.println(x);
		/**
		 * uns the garbage collector. Calling the gc method suggests that the
		 * Java Virtual Machine expend effort toward recycling unused objects in
		 * order to make the memory they currently occupy available for quick
		 * reuse. When control returns from the method call, the Java Virtual
		 * Machine has made a best effort to reclaim space from all discarded
		 * objects. The call System.gc() is effectively equivalent to the call:
		 * Runtime.getRuntime().gc()
		 */
		System.gc(); // equiva to: Runtime.getRuntime().gc();

	}

}
