package innerClass;

public class Test {

	/** For a class defined inside a method, 
	 * what rule governs access to the variables of the enclosing method?
	 * Answer:: The class can only access final variables
	 * @param args
	 */
	int m = 1000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	//inner Class
	public void meth(final int p, int pp){
	    final String x ="methVal";
	    String y ="methVal";

	    class Homme{
			public void pr()
			{
				System.out.println("final var"+x);
				System.out.println(" var m"+m);
	//			System.out.println(" var y"+y); //illegal
				System.out.println("p"+p);
		//		System.out.println("pp"+pp);//illegal
				
			}
		}
	}

}
