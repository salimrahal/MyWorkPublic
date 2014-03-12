package innerClass;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String args[]) throws Exception {
		Test2 o = new Test2();
		System.out.println(o.content());
	}

	public String content() throws Exception {
		throw new Exception("This is an exception on this.content ()");
	}
	//public static void testt(){}
	//It doesnt compile: 
//	private  class X {
//		public static String contentX() {
//			return "X";//
//		}
//	}
	
	private static class B {
		public String content() {
			return "B";
		}
	}

	private static class A extends B {
		public String content() {
			return "A";
		}
	}
}
