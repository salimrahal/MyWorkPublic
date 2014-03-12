package exceptions;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		throwTest();
	}
	
	public static void throwTest()
	{
		throw new AssertionError() ;
		//compilation error
		//System.out.println("complete the code exec");
	}
}
